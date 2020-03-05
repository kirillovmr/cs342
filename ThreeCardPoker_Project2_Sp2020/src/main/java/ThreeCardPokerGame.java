import UI.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ThreeCardPokerGame extends Application {

	State state;
	StackPane rootStack;

	// Logic instances
	Dealer theDealer;
	Player playerOne, playerTwo;

	// UI instances
	UIDealer uiDealer;
	UITable uiTable;
	UIGameButtons uiGameButtons;
	UISettings uiSettings;

	// For simulating actual chips movement
	StackPane fakeChips;
	ArrayList<StackPane> chipsCopy;

	// Couple of handlers
	EventHandler<ActionEvent> startBtnHandler, dealBtnHandler;
	MyHandler onBetChange, onWrongBetInput;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		state = new State();
		playerOne = new Player();
		playerTwo = new Player();
		theDealer = new Dealer();

		// TODO: When player gets FLUSH but dealer pair, some why player loses

		// Creating elements
		uiSettings = new UISettings(stage);
		uiDealer = new UIDealer();
		uiTable = new UITable();
		uiGameButtons = new UIGameButtons();

		// Main Vertical Box
		VBox root = new VBox( uiDealer, uiTable, UIMisc.spacer(30), uiGameButtons );
		root.setId("sceneVBox");

		// Creating and applying handlers
		this.createEventHandlers();
		this.setEventHandlers();

		// Preparing game to start
		this.gameFreshStart();
		this.gameToInitialState();

		rootStack = new StackPane(root, uiSettings, new UIPlayersImages());
		uiSettings.setPickOnBounds(false);

		Scene scene = new Scene(rootStack, GameConstants.globalWidth, GameConstants.globalHeight);
		scene.getStylesheets().add("ThreeCardPokerGame.css");

		stage.setScene(scene);
		stage.setTitle("Three Card Poker");
		stage.setResizable(false);
		stage.show();
	}

	void createEventHandlers() {
		// Handler for start button
		startBtnHandler = event -> {
			makeDealBtnFromStart();
			gameToInitialState();
		};

		// Handler for deal button
		dealBtnHandler = event -> {
			state.gameStarted = true;

			// Disabling input fields
			for(TextField field : uiTable.uiInputs)
				field.setDisable(true);

			// Disable Deal button
			uiGameButtons.dealPlayBtn.setDisable(true);

			// Updating players balance
			playerOne.setBalance( playerOne.getBalance() - playerOne.getAnteBet() - playerOne.getPairPlusBet() );
			playerTwo.setBalance( playerTwo.getBalance() - playerTwo.getAnteBet() - playerTwo.getPairPlusBet());

			// Dealing hand
			playerOne.setHand(theDealer.dealHand());
			playerTwo.setHand(theDealer.dealHand());
			theDealer.setHand(theDealer.dealHand());

			// Matching hand with UI Cards
			assignHandToUICards(theDealer.getHand(), uiTable.dealersCards);
			assignHandToUICards(playerOne.getHand(), uiTable.p1Cards);
			assignHandToUICards(playerTwo.getHand(), uiTable.p2Cards);

			// Animating chips
			animateTranslationOfChips(onFinish -> {
				// Showing cards to players
				uiTable.p1Cards.get(0).open(e1 -> uiTable.p1Cards.get(1).open(e2 -> uiTable.p1Cards.get(2).open(e3 ->
					uiTable.p2Cards.get(0).open(e4 -> uiTable.p2Cards.get(1).open(e5 -> uiTable.p2Cards.get(2).open(onFinish2 -> {
						// Enabling Start and Fold buttons
						uiGameButtons.p1PlayBtn.setDisable(false);
						uiGameButtons.p1FoldBtn.setDisable(false);
						uiGameButtons.p2PlayBtn.setDisable(false);
						uiGameButtons.p2FoldBtn.setDisable(false);

						// Evaluating Pair Plus bet
						evaluatePairPlus(e -> disablePlayButtonsIfNotEnoughMoney());
				}))))));
			});
		};

		// Handler to run whenever players bet value is changed
		onBetChange = dummy -> {
			if (!state.gameStarted) {
				// Disabling deal button if the ante bet is not in range
				boolean disableByAnte = (
					playerOne.getAnteBet() < GameConstants.minBet || playerOne.getAnteBet() > GameConstants.maxBet ||
					playerTwo.getAnteBet() < GameConstants.minBet || playerTwo.getAnteBet() > GameConstants.maxBet
				);

				// Disabling deal button if the ante bet is not in range
				boolean disableByPairPlus = (
					((playerOne.getPairPlusBet() < GameConstants.minBet || playerOne.getPairPlusBet() > GameConstants.maxBet) && playerOne.getPairPlusBet() != 0) ||
					((playerTwo.getPairPlusBet() < GameConstants.minBet || playerTwo.getPairPlusBet() > GameConstants.maxBet) && playerTwo.getPairPlusBet() != 0)
				);

				// Disabling deal button if total bet > balance
				boolean disableByBalance = (
					(playerOne.getBalance() - playerOne.getAnteBet() - playerOne.getPairPlusBet()) < 0 ||
					(playerTwo.getBalance() - playerTwo.getAnteBet() - playerTwo.getPairPlusBet()) < 0
				);
				uiGameButtons.dealPlayBtn.setDisable(disableByAnte || disableByPairPlus || disableByBalance);
			}
		};

		// Handler to run whenever user enters wrong bet
		onWrongBetInput = dummy -> {
			if (uiTable.warningText.isVisible()) {
				uiTable.showWarningText.stop();
				uiTable.showWarningText = new Timeline(new KeyFrame(Duration.millis(GameConstants.showWarningTime), e -> uiTable.warningText.setVisible(false)));
				uiTable.showWarningText.play();
				return;
			}

			uiTable.warningText.setVisible(true);
			uiTable.showWarningText = new Timeline(new KeyFrame(Duration.millis(GameConstants.showWarningTime), e -> uiTable.warningText.setVisible(false)));
			uiTable.showWarningText.play();
		};
	}


	void setEventHandlers() {
		// Cheat: Flip dealers card on click
		for(UICard card : uiTable.dealersCards) {
			card.setOnMouseClicked(e -> {
				if (!card.showingFace && state.gameStarted) {
					card.open(onFinish -> card.close(null));
				}
			});
		}

		// Handlers for settings
		uiSettings.freshStartBtn.setOnAction(e -> {
			uiSettings.hidePopup();
			gameToInitialState();
			gameFreshStart();
		});
		uiSettings.newLookBtn.setOnAction(e -> {
			uiSettings.hidePopup();
			uiTable.flipView();
		});
		uiSettings.exitBtn.setOnAction(e -> {
			uiSettings.hidePopup();
			System.exit(0);
		});

		// Handlers for input fields
		uiTable.uiInputs.get(0).setOnSuccessChange( newValue -> {
			playerOne.setPairPlusBet(newValue);
			for(byte i=0; i<5; i++)
				uiTable.uiChips.get(0).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);
		uiTable.uiInputs.get(1).setOnSuccessChange( newValue -> {
			playerTwo.setPairPlusBet(newValue);
			for(byte i=0; i<5; i++)
				uiTable.uiChips.get(1).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);
		uiTable.uiInputs.get(2).setOnSuccessChange( newValue -> {
			playerOne.setAnteBet(newValue);
			for(byte i=0; i<5; i++)
				uiTable.uiChips.get(2).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);
		uiTable.uiInputs.get(3).setOnSuccessChange( newValue -> {
			playerTwo.setAnteBet(newValue);
			for(byte i=0; i<5; i++)
				uiTable.uiChips.get(3).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);

		// Firing new events to properly display chips
		for (byte i=0; i<4; i++)
			uiTable.uiInputs.get(i).textProperty().setValue("?");

		// Handlers for player balance change
		playerOne.setOnBalanceChange( newBalance -> uiGameButtons.setPlayerOneMoney(newBalance) );
		playerTwo.setOnBalanceChange( newBalance -> uiGameButtons.setPlayerTwoMoney(newBalance) );

		// Handlers for players bet change
		playerOne.setOnBetChange( onBetChange );
		playerTwo.setOnBetChange( onBetChange );

		// Handler for Deal Button
		uiGameButtons.dealPlayBtn.setOnAction(dealBtnHandler);

		// Handlers for play and fold buttons
		uiGameButtons.p1PlayBtn.setOnAction(e -> {
			state.player1Chosen = true;
			state.player1ChosenPlay = true;
			updateUIAfterSelection();
		});
		uiGameButtons.p1FoldBtn.setOnAction(e -> {
			state.player1Chosen = true;
			state.player1ChosenPlay = false;
			updateUIAfterSelection();
		});
		uiGameButtons.p2PlayBtn.setOnAction(e -> {
			state.player2Chosen = true;
			state.player2ChosenPlay = true;
			updateUIAfterSelection();
		});
		uiGameButtons.p2FoldBtn.setOnAction(e -> {
			state.player2Chosen = true;
			state.player2ChosenPlay = false;
			updateUIAfterSelection();
		});
	}

	// Runs when the player chooses to Play or Fold
	void updateUIAfterSelection() {
		// If player1 chose something
		if(state.player1Chosen && !state.player1UIUpdated) {
			if (state.player1ChosenPlay) {
				// Displaying 2x more chips in bet
				UIMisc.duplicateChips(chipsCopy.get(2), 1, "chip_red.png");

				// Updating players balance
				playerOne.setBalance( playerOne.getBalance() - playerOne.getAnteBet() );
			}
			else {
				// Scaling cards if Fold
				for (UICard card : uiTable.p1Cards)
					card.makeSmaller();
			}

			// Disabling buttons
			uiGameButtons.p1PlayBtn.setDisable(true);
			uiGameButtons.p1FoldBtn.setDisable(true);
			state.player1UIUpdated = true;
		}

		// If player2 chose something
		if(state.player2Chosen && !state.player2UIUpdated) {
			if (state.player2ChosenPlay) {
				// Displaying 2x more chips in bet
				UIMisc.duplicateChips(chipsCopy.get(3), 1, "chip_red.png");

				// Updating players balance
				playerTwo.setBalance( playerTwo.getBalance() - playerTwo.getAnteBet() );
			}
			else {
				// Scaling cards if fold
				for (UICard card : uiTable.p2Cards)
					card.makeSmaller();
			}

			// Disabling buttons
			uiGameButtons.p2PlayBtn.setDisable(true);
			uiGameButtons.p2FoldBtn.setDisable(true);
			state.player2UIUpdated = true;
		}

		// If both players made a selection
		if (state.player1Chosen && state.player2Chosen) {

			// Clearing info text
			uiTable.infoText.clearText();

			Timeline smallDelay = new Timeline(new KeyFrame(Duration.millis(1000)));
			smallDelay.setOnFinished(afterDelay -> {
				// Open dealer cards
				uiTable.dealersCards.get(0).open(e -> uiTable.dealersCards.get(1).open(e2 -> uiTable.dealersCards.get(2).open(onFinish -> {
					// Calculate winner
					evaluateHands();

					state.gameStarted = false;

					// Making deal btn as start btn
					makeStartBtnFromDeal();
				})));
			});
			smallDelay.play();
		}
	}


	// Initializes UICards with appropriate cards form hand
	void assignHandToUICards(ArrayList<Card> hand, ArrayList<UICard> cards) {
		for(byte i=0; i<hand.size(); i++) {
			cards.get(i).setFrontImage(hand.get(i).toResourceName());
		}
	}


	// Evaluating players hands for Pair Plus wagers
	void evaluatePairPlus(EventHandler<ActionEvent> onFinish) {
		int betToStackX = 245, betToPlayerX = 80;
		int betToStackY = 92, betToPlayerY = 20;
		int time = 100;

		int p1Res = ThreeCardLogic.evalHand(playerOne.getHand());
		int p1Win = ThreeCardLogic.evalPPWinnings(playerOne.getHand(), playerOne.pairPlusBet);
		int p2Res = ThreeCardLogic.evalHand(playerTwo.getHand());
		int p2Win = ThreeCardLogic.evalPPWinnings(playerTwo.getHand(), playerTwo.pairPlusBet);

		// Setting card text value
		uiTable.playersCardText.setPlayerOneText( ThreeCardLogic.evalHandToPairPlusName(p1Res) );
		uiTable.playersCardText.setPlayerTwoText( ThreeCardLogic.evalHandToPairPlusName(p2Res) );

		AtomicReference<String> p1InfoText = new AtomicReference<>();
		AtomicReference<String> p2InfoText = new AtomicReference<>();

		// Evaluating player 1
		if (p1Res == 0) {
			p1InfoText.set("Pair Plus wager lost");
			UIMisc.shiftChips(chipsCopy.get(0), -betToStackX+7, -betToStackY, time, null);
		}
		else {
			p1InfoText.set("Pair Plus wins $" + playerOne.pairPlusBet + "x" + ThreeCardLogic.evalHandToPairPlusMultiplier(p1Res) + " = $" + p1Win);
			playerOne.setBalance( playerOne.getBalance() + p1Win );
			UIMisc.duplicateChips(chipsCopy.get(0), 5-p1Res, "chip_black.png");
			UIMisc.shiftChips(chipsCopy.get(0), -betToPlayerX, betToPlayerY, time, null);
		}

		// Evaluating player 2
		Timeline smallDelay = new Timeline(new KeyFrame(Duration.millis(time*4)));
		smallDelay.setOnFinished(event -> {
			if (p2Res == 0) {
				p2InfoText.set("Pair Plus wager lost");
				UIMisc.shiftChips(chipsCopy.get(1), -betToStackX, -betToStackY, time, onFinish);
			}
			else {
				p2InfoText.set("Pair Plus wins $" + playerTwo.pairPlusBet + "x" + ThreeCardLogic.evalHandToPairPlusMultiplier(p2Res) + " = $" + p2Win);
				playerTwo.setBalance( playerTwo.getBalance() + p2Win );
				UIMisc.duplicateChips(chipsCopy.get(1), 5-p2Res, "chip_black.png");
				UIMisc.shiftChips(chipsCopy.get(1), betToPlayerX, betToPlayerY, time, onFinish);
			}

			if (playerOne.getPairPlusBet() > 0)
				uiTable.infoText.setPlayerOneText(p1InfoText.get());
			if (playerTwo.getPairPlusBet() > 0)
				uiTable.infoText.setPlayerTwoText(p2InfoText.get());
		});
		smallDelay.play();
	}


	// Evaluates players against the dealer and runs animation
	void evaluateHands() {
		String p1InfoText, p2InfoText;
		int anteToPlayerX = 50, anteToDealerX = 0;
		int anteToPlayerY = 10, anteToDealerY = 100;
		int time = 100;

		if (state.player1ChosenPlay) {
			int res1 = ThreeCardLogic.compareHands(theDealer.getHand(), playerOne.getHand());
			if (res1 == 1) {
				p1InfoText = "You lost ante wager";
				UIMisc.shiftChips(chipsCopy.get(2), anteToDealerX, -anteToDealerY, time, null);
			} else if (res1 == 2) {
				p1InfoText = "You won $" + 4 * playerOne.getAnteBet();
				playerOne.setBalance( playerOne.getBalance() + 4 * playerOne.getAnteBet() );
				UIMisc.duplicateChips(chipsCopy.get(2), 1, "chip_red.png");
				UIMisc.shiftChips(chipsCopy.get(2), -anteToPlayerX, anteToPlayerY, time, null);
			} else {
				p1InfoText = "Neither wins";
				playerOne.setBalance( playerOne.getBalance() + 2 * playerOne.getAnteBet() );
				UIMisc.shiftChips(chipsCopy.get(2), -anteToPlayerX, anteToPlayerY, time, null);
			}
		} else {
			p1InfoText = "You lose because of the Fold";
			UIMisc.shiftChips(chipsCopy.get(2), anteToDealerX, -anteToDealerY, time, null);
		}
		uiTable.infoText.setPlayerOneText(p1InfoText);

		if (state.player2ChosenPlay) {
			int res2 = ThreeCardLogic.compareHands(theDealer.getHand(), playerTwo.getHand());
			if (res2 == 1) {
				p2InfoText = "You lost ante wager";
				UIMisc.shiftChips(chipsCopy.get(3), anteToDealerX, -anteToDealerY, time, null);
			} else if (res2 == 2) {
				p2InfoText = "You won $" + 4 * playerTwo.getAnteBet();
				playerTwo.setBalance( playerTwo.getBalance() + 4 * playerTwo.getAnteBet() );
				UIMisc.duplicateChips(chipsCopy.get(3), 1, "chip_red.png");
				UIMisc.shiftChips(chipsCopy.get(3), anteToPlayerX, anteToPlayerY, time, null);
			} else {
				p2InfoText = "Neither wins";
				playerTwo.setBalance( playerTwo.getBalance() + 2 * playerTwo.getAnteBet() );
				UIMisc.shiftChips(chipsCopy.get(3), anteToPlayerX, anteToPlayerY, time, null);
			}
		} else {
			p2InfoText = "You lose because of the Fold";
			UIMisc.shiftChips(chipsCopy.get(3), anteToDealerX, -anteToDealerY, time, null);
		}
		uiTable.infoText.setPlayerTwoText(p2InfoText);
	}

	// Imitates translation of the chips to the center of the screen
	void animateTranslationOfChips(EventHandler<ActionEvent> onFinish) {
		chipsCopy = new ArrayList<>();
		fakeChips = new StackPane();

		// Duplicating existing chips
		for(byte i=0; i<4; i++) {
			StackPane chipStack = new StackPane();
			for(byte j=0; j<10; j++) {
				if (uiTable.uiInputs.get(i).getCurrentValue() > j * 5) {
					ImageView wagerChip = UIMisc.createImageView(i < 2 ? "chip_black.png" : "chip_red.png", 0.07);
					wagerChip.getStyleClass().addAll("wagerChip", "shadow");

					StackPane chipWrap = new StackPane(wagerChip);
					chipWrap.setPadding(new Insets(0, 0, j * 5, 0));
					chipStack.getChildren().add(chipWrap);
				}
			}
			fakeChips.getChildren().add(chipStack);
			chipsCopy.add(chipStack);
		}

		// Making copy chips invisible
		for(byte i=0; i<4; i++)
			fakeChips.getChildren().get(i).setVisible(false);

		// Calculating difference between actual chips position and center of the screen
		int x1 = (int)((uiTable.uiChips.get(0).get(0).localToScreen(uiTable.uiChips.get(0).get(0).getBoundsInLocal()).getMinX() -
				uiTable.uiChips.get(1).get(0).localToScreen(uiTable.uiChips.get(1).get(0).getBoundsInLocal()).getMinX()) / 2);
		int x2 = (int)((uiTable.uiChips.get(2).get(0).localToScreen(uiTable.uiChips.get(2).get(0).getBoundsInLocal()).getMinX() -
				uiTable.uiChips.get(3).get(0).localToScreen(uiTable.uiChips.get(3).get(0).getBoundsInLocal()).getMinX()) / 2);
		int y1 = 13;
		int y2 = 85;

		// Placing copyChips above actual chips
		int timeToPlaceFakeChips = 100;
		Timeline placeCopyChips = new Timeline(new KeyFrame(Duration.millis(timeToPlaceFakeChips * 2), event -> {
			for(byte i=0; i<4; i++) {
				TranslateTransition translateToActual = new TranslateTransition();
				translateToActual.setDuration(Duration.millis(timeToPlaceFakeChips));
				translateToActual.setNode(chipsCopy.get(i));
				switch (i) {
					case 0:
						translateToActual.setByX(x1);
						translateToActual.setByY(y1);
						break;
					case 1:
						translateToActual.setByX(-x1);
						translateToActual.setByY(y1);
						break;
					case 2:
						translateToActual.setByX(x2);
						translateToActual.setByY(y2);
						break;
					case 3:
						translateToActual.setByX(-x2);
						translateToActual.setByY(y2);
						break;
				}

				// If moving the last chip
				if (i == 3) {
					translateToActual.setOnFinished(afterPlacingChips -> {
						Timeline smallDelay = new Timeline(new KeyFrame(Duration.millis(timeToPlaceFakeChips + 500)));

						smallDelay.setOnFinished(afterSmallDelay -> {
							int timeToTranslateOneChip = 100;
							TranslateTransition translateToActual1 = new TranslateTransition();
							translateToActual1.setDuration(Duration.millis(timeToTranslateOneChip));
							Timeline moveToCenter = new Timeline(new KeyFrame(Duration.millis(timeToTranslateOneChip * 2), new EventHandler<ActionEvent>() {
								int i = 0;
								public void handle(ActionEvent event) {
									// Making actual chips invisible
									for (byte j=0; j<5; j++)
										uiTable.uiChips.get(i).get(j).setVisible(false);

									StackPane chipsToWorkWith = chipsCopy.get(i);

									// Caching the node
									chipsToWorkWith.setCache(true);
									chipsToWorkWith.setCacheShape(true);
									chipsToWorkWith.setCacheHint(CacheHint.SPEED);

									// Moving chips to the center
									translateToActual1.setNode(chipsToWorkWith);
									switch (i) {
										case 0:
											translateToActual1.setByX(-x1 -20);
											translateToActual1.setByY(-y1 -20);
											break;
										case 1:
											translateToActual1.setByX(x1 +20);
											translateToActual1.setByY(-y1 -20);
											break;
										case 2:
											translateToActual1.setByX(-x2 -20);
											translateToActual1.setByY(-y2 +20);
											break;
										case 3:
											translateToActual1.setByX(x2 +20);
											translateToActual1.setByY(-y2 +20);
											break;
									}

									// If moving the last chip
									if (i == 3) {
										translateToActual1.setOnFinished(e -> {
											Timeline smallDelay2 = new Timeline(new KeyFrame(Duration.millis(timeToTranslateOneChip + 500)));
											smallDelay2.setOnFinished(onFinish);
											smallDelay2.play();
										});
									}
									translateToActual1.play();

									// Making copy chips visible
									fakeChips.getChildren().get(i).setVisible(true);
									i += 1;
								}
							}));
							moveToCenter.setCycleCount(4);
							moveToCenter.play();
						});
						smallDelay.play();
					});
				}
				translateToActual.play();
			}
		}));
		placeCopyChips.play();

		fakeChips.setDisable(true);
		rootStack.getChildren().add(fakeChips);
	}


	// Restoring game to initial state after playing
	void gameToInitialState() {

		// Clearing text values
		uiTable.infoText.clearText();
		uiTable.playersCardText.clearText();

		// Restoring cards size
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GameConstants.cardScaleAnimationTime), ev -> {
			for(UICard card: uiTable.p1Cards)
				card.restoreSize();
			for(UICard card: uiTable.p2Cards)
				card.restoreSize();
		}));
		timeline.setOnFinished(ev -> {
			// Delay is needed since restoring card size uses animation
			new Timeline(new KeyFrame(Duration.millis(GameConstants.cardScaleAnimationTime * 2), ev2 -> {
				// Closing cards
				for (UICard card : uiTable.dealersCards)
					card.close(null);
				for (UICard card : uiTable.p1Cards)
					card.close(null);
				for (UICard card : uiTable.p2Cards)
					card.close(null);

				// Delay to make sure cards are closed
				Timeline smallDelay = new Timeline(new KeyFrame(Duration.millis(GameConstants.cardFlipAnimationTime * 2)));
				smallDelay.setOnFinished(onFinish -> {

					// Restoring state
					state.init();

					// Restoring input fields
					for(TextField field : uiTable.uiInputs)
						field.setDisable(false);

					// Restoring buttons
					uiGameButtons.p1PlayBtn.setDisable(true);
					uiGameButtons.p1FoldBtn.setDisable(true);
					uiGameButtons.dealPlayBtn.setDisable(false);
					uiGameButtons.p2PlayBtn.setDisable(true);
					uiGameButtons.p2FoldBtn.setDisable(true);

					// Run onInputChange handlers to properly update Deal Button
					uiTable.uiInputs.get(0).textProperty().setValue("lol");
				});
				smallDelay.play();
			})).play();
		});
		timeline.play();

		try {
			// Making actual chips visible
			for(byte i=0; i<4; i++) {
				for (byte j=0; j<5; j++)
					uiTable.uiChips.get(i).get(j).setVisible(false);
				uiTable.uiInputs.get(i).textProperty().setValue("?");
			}

			// Deleting fake chips node
			rootStack.getChildren().remove(fakeChips);
		} catch (Exception ignored) {}
	}

	// Restarts the balance and wagers
	void gameFreshStart() {
		playerOne.setBalance(GameConstants.initialMoneyValue);
		playerTwo.setBalance(GameConstants.initialMoneyValue);

		playerOne.setPairPlusBet(0);
		playerTwo.setPairPlusBet(0);

		playerOne.setAnteBet(GameConstants.minBet);
		playerTwo.setAnteBet(GameConstants.minBet);

		if (!state.showingDealButton) {
			makeDealBtnFromStart();
		}
	}

	void disablePlayButtonsIfNotEnoughMoney(){
		if (playerOne.getBalance() - playerOne.getAnteBet() < 0)
			uiGameButtons.p1PlayBtn.setDisable(true);

		if (playerTwo.getBalance() - playerTwo.getAnteBet() < 0)
			uiGameButtons.p2PlayBtn.setDisable(true);
	}

	void makeDealBtnFromStart() {
		uiGameButtons.dealPlayBtn.setDisable(true);
		uiGameButtons.dealPlayBtn.setText(GameConstants.dealBtnText);
		uiGameButtons.dealPlayBtn.setOnAction(dealBtnHandler);
		state.showingDealButton = true;
	}
	void makeStartBtnFromDeal() {
		uiGameButtons.dealPlayBtn.setOnAction(startBtnHandler);
		uiGameButtons.dealPlayBtn.setText(GameConstants.startBtnText);
		uiGameButtons.dealPlayBtn.setDisable(false);
		state.showingDealButton = false;
	}
}
