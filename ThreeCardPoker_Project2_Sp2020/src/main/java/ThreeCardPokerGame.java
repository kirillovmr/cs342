import UI.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class ThreeCardPokerGame extends Application {

	State state;

	Player playerOne, playerTwo;
	Dealer theDealer;

	ArrayList<Text> uiText; // [0] - money of P1, [1] - money of P2
	ArrayList<UITextField> uiInputs; // [0],[1] - Pair plus, [2],[3] - Ante
	ArrayList<Button> uiButtons; // [0],[1] - Play, Fold P1, [2] - Deal, [3],[4] - Play, Fold P2
	ArrayList< ArrayList<UICard> > uiCards; // [0] is a dealer, [1],[2] are cards of players 1 and 2 respectively
	ArrayList<ArrayList<ImageView>> uiChips;

	Timeline showWarningText;
	Text warningText;

	StackPane rootStack;
	StackPane fakeChips;
	ArrayList<StackPane> chipsCopy;

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

		playerOne.setAnteBet(GameConstants.minBet);
		playerTwo.setAnteBet(GameConstants.minBet);

		uiText = new ArrayList<>();
		uiInputs = new ArrayList<>();
		uiButtons = new ArrayList<>();
		uiCards = new ArrayList<>();
		for (byte i=0; i<3; i++)
			uiCards.add(new ArrayList<>());

		uiChips = new ArrayList<>();
		for (byte i=0; i<4; i++)
			uiChips.add(new ArrayList<>());

		warningText = new Text();

		// Preparing game to start
		this.gameToInitialState();

		// Main Vertical Box
		VBox root = new VBox(
				new UIDealer(),
				new UITable(uiCards, uiInputs, uiChips, warningText),
				UIMisc.spacer(30),
				new UIGameButtons(uiButtons)
		);
		root.setId("sceneVBox");

		// Creating and applying handlers
		this.createEventHandlers();
		this.setEventHandlers();

		rootStack = new StackPane(root);

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
			uiButtons.get(2).setDisable(true);
			uiButtons.get(2).setText(GameConstants.dealBtnText);
			uiButtons.get(2).setOnAction(dealBtnHandler);
			gameToInitialState();
		};

		// Handler for deal button
		dealBtnHandler = event -> {
			state.gameStarted = true;

			// Disabling input fields
			for(TextField field : uiInputs)
				field.setDisable(true);

			// Disable Deal button
			uiButtons.get(2).setDisable(true);

			// Dealing hand
			playerOne.setHand(theDealer.dealHand());
			playerTwo.setHand(theDealer.dealHand());
			theDealer.setHand(theDealer.dealHand());

			// Matching hand with UI Cards
			assignHandToUICards(playerOne.getHand(), uiCards.get(1));
			assignHandToUICards(playerTwo.getHand(), uiCards.get(2));
			assignHandToUICards(theDealer.getHand(), uiCards.get(0));

			// Animating chips
			animateTranslationOfChips(onFinish -> {
				// Showing cards to players
				uiCards.get(1).get(0).flip(e1 -> uiCards.get(1).get(1).flip(e2 -> uiCards.get(1).get(2).flip(e3 -> {
					uiCards.get(2).get(0).flip(e4 -> uiCards.get(2).get(1).flip(e5 -> uiCards.get(2).get(2).flip(onFinish2 -> {
						// Enabling Start and Fold buttons
						uiButtons.get(0).setDisable(false);
						uiButtons.get(1).setDisable(false);
						uiButtons.get(3).setDisable(false);
						uiButtons.get(4).setDisable(false);

						// Evaluating Pair Plus bet
						evaluatePairPlus();
					})));
				})));
			});
		};

		// Handler to run whenever players bet value is changed
		onBetChange = dummy -> {
			if (!state.gameStarted) {
				boolean disable = ((playerOne.getAnteBet() >= GameConstants.minBet && playerOne.getAnteBet() <= GameConstants.maxBet)
						&& (playerTwo.getAnteBet() >= GameConstants.minBet && playerTwo.getAnteBet() <= GameConstants.maxBet));
				uiButtons.get(2).setDisable(!disable);
			}
		};

		// Handler to run whenever user enters wrong bet
		onWrongBetInput = dummy -> {
			if (warningText.isVisible()) {
				showWarningText.stop();
				showWarningText = new Timeline(new KeyFrame(Duration.millis(GameConstants.showWarningTime), e -> warningText.setVisible(false)));
				showWarningText.play();
				return;
			}

			warningText.setVisible(true);
			showWarningText = new Timeline(new KeyFrame(Duration.millis(GameConstants.showWarningTime), e -> warningText.setVisible(false)));
			showWarningText.play();
		};
	}


	void setEventHandlers() {
		// Cheat: Flip dealers card on click
		for(UICard card : uiCards.get(0)) {
			card.setOnMouseClicked(e -> card.flip(onFinish -> card.flip(null) ));
		}

		// Handlers for input fields
		uiInputs.get(0).setOnSuccessChange( newValue -> {
			playerOne.setPairPlusBet(newValue);
			for(byte i=0; i<5; i++)
				uiChips.get(0).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);
		uiInputs.get(1).setOnSuccessChange( newValue -> {
			playerTwo.setPairPlusBet(newValue);
			for(byte i=0; i<5; i++)
				uiChips.get(1).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);
		uiInputs.get(2).setOnSuccessChange( newValue -> {
			playerOne.setAnteBet(newValue);
			for(byte i=0; i<5; i++)
				uiChips.get(2).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);
		uiInputs.get(3).setOnSuccessChange( newValue -> {
			playerTwo.setAnteBet(newValue);
			for(byte i=0; i<5; i++)
				uiChips.get(3).get(i).setVisible(newValue > i * GameConstants.maxBet / 5);
		}, onWrongBetInput);
		// Firing new events to properly display chips
		for (byte i=0; i<4; i++)
			uiInputs.get(i).textProperty().setValue("?");


		// Handlers for players bet change
		playerOne.setOnBetChange( onBetChange );
		playerTwo.setOnBetChange( onBetChange );

		// Handler for Deal Button
		uiButtons.get(2).setOnAction(dealBtnHandler);

		// Handlers for play and fold buttons
		uiButtons.get(0).setOnAction(e -> {
			state.player1Chosen = true;
			state.player1ChosenPlay = true;
			updateUIAfterSelection();
		});
		uiButtons.get(1).setOnAction(e -> {
			state.player1Chosen = true;
			state.player1ChosenPlay = false;
			updateUIAfterSelection();
		});
		uiButtons.get(3).setOnAction(e -> {
			state.player2Chosen = true;
			state.player2ChosenPlay = true;
			updateUIAfterSelection();
		});
		uiButtons.get(4).setOnAction(e -> {
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
			}
			else {
				// Scaling cards if Fold
				for (UICard card : uiCards.get(1))
					card.makeSmaller();
			}

			// Disabling buttons
			uiButtons.get(0).setDisable(true);
			uiButtons.get(1).setDisable(true);
			state.player1UIUpdated = true;
		}

		// If player2 chose something
		if(state.player2Chosen && !state.player2UIUpdated) {
			if (state.player2ChosenPlay) {
				// Displaying 2x more chips in bet
				UIMisc.duplicateChips(chipsCopy.get(3), 1, "chip_red.png");
			}
			else {
				// Scaling cards if fold
				for (UICard card : uiCards.get(2))
					card.makeSmaller();
			}

			// Disabling buttons
			uiButtons.get(3).setDisable(true);
			uiButtons.get(4).setDisable(true);
			state.player2UIUpdated = true;
		}

		// If both players made a selection
		if (state.player1Chosen && state.player2Chosen) {
			Timeline smallDelay = new Timeline(new KeyFrame(Duration.millis(1000)));
			smallDelay.setOnFinished(afterDelay -> {
				// Open dealer cards
				uiCards.get(0).get(0).flip(e -> uiCards.get(0).get(1).flip(e2 -> uiCards.get(0).get(2).flip(onFinish -> {
					// Calculate winner
					evaluateHands();

					state.gameStarted = false;

					// Making deal btn as start btn
					uiButtons.get(2).setOnAction(startBtnHandler);
					uiButtons.get(2).setText(GameConstants.startBtnText);
					uiButtons.get(2).setDisable(false);
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


	void evaluatePairPlus() {
		int betToStackX = 260, betToPlayerX = 80;
		int betToStackY = 130, betToPlayerY = 20;
		int time = 100;

		int p1Res = ThreeCardLogic.evalHand(playerOne.getHand());
		int p1Win = ThreeCardLogic.evalPPWinnings(playerOne.getHand(), playerOne.pairPlusBet);
		int p2Res = ThreeCardLogic.evalHand(playerTwo.getHand());
		int p2Win = ThreeCardLogic.evalPPWinnings(playerTwo.getHand(), playerTwo.pairPlusBet);

		// Evaluating player 1
		if (p1Res == 0) {
			System.out.println("Player 1 loses Pair Plus bet");
			UIMisc.shiftChips(chipsCopy.get(0), -betToStackX, -betToStackY, time, e -> chipsCopy.get(0).setVisible(false));
		}
		else {
			System.out.println("Player 1 Pair Plus wins " + p1Win + "$");
			UIMisc.duplicateChips(chipsCopy.get(0), 6-p1Res, "chip_black.png");
			UIMisc.shiftChips(chipsCopy.get(0), -betToPlayerX, betToPlayerY, time, null);
		}

		// Evaluating player 2
		Timeline smallDelay = new Timeline(new KeyFrame(Duration.millis(time*4)));
		smallDelay.setOnFinished(onFinish -> {
			if (p2Res == 0) {
				System.out.println("Player 2 loses Pair Plus bet");
				UIMisc.shiftChips(chipsCopy.get(1), -betToStackX, -betToStackY, time, e -> chipsCopy.get(1).setVisible(false));
			}
			else {
				System.out.println("Player 2 Pair Plus wins " + p2Win + "$");
				UIMisc.duplicateChips(chipsCopy.get(1), 6-p2Res, "chip_black.png");
				UIMisc.shiftChips(chipsCopy.get(1), betToPlayerX, betToPlayerY, time, null);
			}
		});
		smallDelay.play();
	}


	void evaluateHands() {
		int anteToPlayerX = 50, anteToDealerX = 0;
		int anteToPlayerY = 20, anteToDealerY = 100;
		int time = 100;
		if (state.player1ChosenPlay) {
			int res1 = ThreeCardLogic.compareHands(theDealer.getHand(), playerOne.getHand());
			if (res1 == 1) {
				System.out.println("P1: dealer wins");
				UIMisc.shiftChips(chipsCopy.get(2), anteToDealerX, -anteToDealerY, time, null);
			} else if (res1 == 2) {
				System.out.println("P1: player wins");
				UIMisc.duplicateChips(chipsCopy.get(2), 1, "chip_red.png");
				UIMisc.shiftChips(chipsCopy.get(2), -anteToPlayerX, anteToPlayerY, time, null);
			} else {
				System.out.println("P1: NO ONE wins");
				UIMisc.shiftChips(chipsCopy.get(2), -anteToPlayerX, anteToPlayerY, time, null);
			}
		} else {
			System.out.println("P1 loses because of the fold");
			UIMisc.shiftChips(chipsCopy.get(2), anteToDealerX, -anteToDealerY, time, null);
		}

		if (state.player2ChosenPlay) {
			int res2 = ThreeCardLogic.compareHands(theDealer.getHand(), playerTwo.getHand());
			if (res2 == 1) {
				System.out.println("P2: dealer wins");
				UIMisc.shiftChips(chipsCopy.get(3), anteToDealerX, -anteToDealerY, time, null);
			} else if (res2 == 2) {
				System.out.println("P2: player wins");
				UIMisc.duplicateChips(chipsCopy.get(3), 1, "chip_red.png");
				UIMisc.shiftChips(chipsCopy.get(3), anteToPlayerX, anteToPlayerY, time, null);
			} else {
				System.out.println("P2: NO ONE wins");
				UIMisc.shiftChips(chipsCopy.get(3), anteToPlayerX, anteToPlayerY, time, null);
			}
		} else {
			System.out.println("P2 loses because of the fold");
			UIMisc.shiftChips(chipsCopy.get(3), anteToDealerX, -anteToDealerY, time, null);
		}
	}


	void animateTranslationOfChips(EventHandler<ActionEvent> onFinish) {
		chipsCopy = new ArrayList<>();
		fakeChips = new StackPane();

		// Duplicating existing chips
		for(byte i=0; i<4; i++) {
			StackPane chipStack = new StackPane();
			for(byte j=0; j<10; j++) {
				if (uiInputs.get(i).getCurrentValue() > j * 5) {
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
		int x1 = (int)((uiChips.get(0).get(0).localToScreen(uiChips.get(0).get(0).getBoundsInLocal()).getMinX() -
				uiChips.get(1).get(0).localToScreen(uiChips.get(1).get(0).getBoundsInLocal()).getMinX()) / 2);
		int x2 = (int)((uiChips.get(2).get(0).localToScreen(uiChips.get(2).get(0).getBoundsInLocal()).getMinX() -
				uiChips.get(3).get(0).localToScreen(uiChips.get(3).get(0).getBoundsInLocal()).getMinX()) / 2);
		int y1 = 13;
		int y2 = 85;

		// Placing copyChips above actual chips
		int timeToPlaceFakeChips = 100;
		Timeline placeCopyChips = new Timeline(new KeyFrame(Duration.millis(timeToPlaceFakeChips * 2), event -> {
			for(byte i=0; i<4; i++) {
				TranslateTransition translate = new TranslateTransition();
				translate.setDuration(Duration.millis(timeToPlaceFakeChips));
				translate.setNode(chipsCopy.get(i));
				switch (i) {
					case 0:
						translate.setByX(x1);
						translate.setByY(y1);
						break;
					case 1:
						translate.setByX(-x1);
						translate.setByY(y1);
						break;
					case 2:
						translate.setByX(x2);
						translate.setByY(y2);
						break;
					case 3:
						translate.setByX(-x2);
						translate.setByY(y2);
						break;
				}

				// If moving the last chip
//				if (i == 3) {
//					translate.setOnFinished();
//				}
				translate.play();
			}
		}));

		placeCopyChips.setOnFinished(afterPlacingChips -> {
			Timeline smallDelay = new Timeline(new KeyFrame(Duration.millis(timeToPlaceFakeChips + 500)));

			smallDelay.setOnFinished(afterSmallDelay -> {
				int timeToTranslateOneChip = 100;
				TranslateTransition translate1 = new TranslateTransition();
				translate1.setDuration(Duration.millis(timeToTranslateOneChip));
				Timeline moveToCenter = new Timeline(new KeyFrame(Duration.millis(timeToTranslateOneChip * 2), new EventHandler<ActionEvent>() {
					int i = 0;
					public void handle(ActionEvent event) {
						// Making actual chips invisible
						for (byte j=0; j<5; j++)
							uiChips.get(i).get(j).setVisible(false);

						// Caching the node
						chipsCopy.get(i).setCache(true);
						chipsCopy.get(i).setCacheShape(true);
						chipsCopy.get(i).setCacheHint(CacheHint.SPEED);

						// Moving chips to the center
						translate1.setNode(chipsCopy.get(i));
						switch (i) {
							case 0:
								translate1.setByX(-x1 -20);
								translate1.setByY(-y1 -20);
								break;
							case 1:
								translate1.setByX(x1 +20);
								translate1.setByY(-y1 -20);
								break;
							case 2:
								translate1.setByX(-x2 -20);
								translate1.setByY(-y2 +20);
								break;
							case 3:
								translate1.setByX(x2 +20);
								translate1.setByY(-y2 +20);
								break;
						}

						// If moving the last chip
						if (i == 3) {
							translate1.setOnFinished(e -> {
								Timeline smallDelay2 = new Timeline(new KeyFrame(Duration.millis(timeToTranslateOneChip + 500)));
								smallDelay2.setOnFinished(onFinish);
								smallDelay2.play();
							});
						}
						translate1.play();

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
		placeCopyChips.play();

		fakeChips.setDisable(true);
		rootStack.getChildren().add(fakeChips);
	}


	// Restoring game to initial state after playing
	void gameToInitialState() {
		// Restoring cards size
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GameConstants.cardScaleAnimationTime), ev -> {
			for(UICard card: uiCards.get(1))
				card.restoreSize();
			for(UICard card: uiCards.get(2))
				card.restoreSize();
		}));
		timeline.setOnFinished(ev -> {
			// Delay is needed since restoring card size uses animation
			new Timeline(new KeyFrame(Duration.millis(GameConstants.cardScaleAnimationTime * 2), ev2 -> {
				// Closing cards
				for(ArrayList<UICard> arr : uiCards)
					for (UICard card : arr)
						card.flip(null);

				// Restoring state
				state.init();

				// Restoring input fields
				for(TextField field : uiInputs)
					field.setDisable(false);

				// Restoring buttons
				uiButtons.get(0).setDisable(true);
				uiButtons.get(1).setDisable(true);
				uiButtons.get(2).setDisable(false);
				uiButtons.get(3).setDisable(true);
				uiButtons.get(4).setDisable(true);

			})).play();
		});
		timeline.play();

		try {
			// Making actual chips visible
			for(byte i=0; i<4; i++) {
				for (byte j=0; j<5; j++)
					uiChips.get(i).get(j).setVisible(false);
				uiInputs.get(i).textProperty().setValue("?");
			}

			// Deleting fake chips node
			rootStack.getChildren().remove(fakeChips);
		} catch (Exception e) {}
	}

}
