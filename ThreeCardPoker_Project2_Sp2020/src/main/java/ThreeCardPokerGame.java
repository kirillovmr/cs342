import UI.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	ArrayList<TextField> uiInputs; // [0],[1] - Pair plus, [2],[3] - Ante
	ArrayList<Button> uiButtons; // [0],[1] - Play, Fold P1, [2] - Deal, [3],[4] - Play, Fold P2
	ArrayList< ArrayList<ImageView> > uiCards; // [0] is a dealer, [1],[2] are cards of players 1 and 2 respectively

	EventHandler<ActionEvent> startBtnHandler, dealBtnHandler;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
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

		this.createEventHandlers();

		// Creating UI.UI
		ToolBar toolbar = UI.createToolbar();
		ImageView dealer = UI.createDealerImage();
		StackPane table = UI_Table.createTable(uiCards, uiInputs);
		HBox playButtons = UI.createGameButtons(uiButtons);
		HBox moneyBox = UI.createMoneyBox(uiText);

		// Setting up UI.UI elements
		this.setupUIElements();

		UICard card = new UICard();
		card.setFrontImage("C4.png");

		VBox root = new VBox(UI.spacer(30), dealer, table, playButtons, card);
		root.setId("sceneVBox");

		Scene scene = new Scene(root, GameConstants.globalWidth, GameConstants.globalHeight);
		scene.getStylesheets().add("ThreeCardPokerGame.css");

		stage.setScene(scene);
		stage.setTitle("Three Card Poker");
		stage.show();
	}


	void createEventHandlers() {
		startBtnHandler = event -> {
			uiButtons.get(2).setDisable(true);
			uiButtons.get(2).setText(GameConstants.dealBtnText);
			uiButtons.get(2).setOnAction(dealBtnHandler);
			restoreGame();
		};

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

			// Showing cards to players
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
				private int i = 0;
				public void handle(ActionEvent event) {
					UIMisc.openCard(uiCards.get(1).get(i), playerOne.getHand().get(i).toResourceName(), null);
					UIMisc.openCard(uiCards.get(2).get(i), playerTwo.getHand().get(i).toResourceName(), null);
					i++;
				}
			}));

			// When cards are shown
			timeline.setOnFinished(e -> {

				// Enabling Start and Fold buttons
				for(Button b : uiButtons) {
					if (b.getText() == "Play" || b.getText() == "Fold") {
						b.setDisable(false);
					}
				}
			});

			timeline.setCycleCount(3);
			timeline.play();
		};
	}

	void setupUIElements() {

		// Cheat: Flip dealers card on click
		for(ImageView card : uiCards.get(0)) {
			card.setOnMouseClicked(new EventHandler<MouseEvent>() {
				boolean animating = false;
				public void handle(MouseEvent mouseEvent) {
					if (state.gameStarted && !animating) {
						String cardId = card.getId();
						int cardIdx = cardId.charAt(cardId.length() - 1) - 48;

						animating = true;
						UIMisc.openCard(card, theDealer.getHand().get(cardIdx).toResourceName(), onFinishOpenEvent -> {
							UIMisc.closeCard(card, onFinishCloseEvent -> {
								animating = false;
							});
						});
					}
				}
			});
		}

		// Event handlers for Wager Inputs
		int fieldNum = 0;
		for(TextField field : uiInputs) {
			int finalI = fieldNum;
			field.textProperty().addListener((observable, oldValue, newValue) -> {
				int parsedInt = -1;
				try {
					parsedInt = Integer.parseInt(newValue);

					// Restricting bet size
					if (parsedInt < 0 || parsedInt > GameConstants.maxBet)
						throw new Exception();
				}
				// Input is not an integer
				catch (NumberFormatException nfe) {
					if (newValue.length() == 0)
						parsedInt = 0;
					else
						uiInputs.get(finalI).setText(oldValue);
				}
				// Input is > threshold
				catch (Exception e) {
					System.out.println("Bet size is restricted to " + GameConstants.maxBet);
					uiInputs.get(finalI).setText(oldValue);
				}
				finally {
					if (parsedInt >= 0 && parsedInt <= GameConstants.maxBet) {
						Player p = finalI % 2 == 0 ? playerOne : playerTwo;
						if (finalI < 2) {
							p.setPairPlusBet(parsedInt);
						} else {
							p.setAnteBet(parsedInt);
						}

						System.out.println("" + Integer.toString(finalI) + "New value: " + parsedInt);
					}
				}

				// Checking for disabling Deal Button
				if (!state.gameStarted) {
					Button dealBtn = uiButtons.get(2);
					if ((playerOne.getAnteBet() >= GameConstants.minBet && playerOne.getAnteBet() <= GameConstants.maxBet)
							&& (playerTwo.getAnteBet() >= GameConstants.minBet && playerTwo.getAnteBet() <= GameConstants.maxBet))
					{
						dealBtn.setDisable(false);
					}
					else {
						dealBtn.setDisable(true);
					}

				}
			});
			fieldNum += 1;
		}


		// Disabling Play and Fold buttons
		for(Button b : uiButtons) {
			if (b.getText() == "Play" || b.getText() == "Fold") {
				b.setDisable(true);
			}
		}

		// Event Listener for Deal Button
		uiButtons.get(2).setOnAction(dealBtnHandler);

		// Event handlers for Deal and play buttons
		for(Button b : uiButtons) {
			if (b.getText() == "Play" || b.getText() == "Fold") {
				b.setOnAction(event -> {
					String btnId = b.getId();

					switch (btnId) {
						case "btnPlay1":
							state.player1Chosen = true;
							state.player1ChosenPlay = true;
							break;
						case "btnFold1":
							state.player1Chosen = true;
							state.player1ChosenPlay = false;
							break;
						case "btnPlay2":
							state.player2Chosen = true;
							state.player2ChosenPlay = true;
							break;
						case "btnFold2":
							state.player2Chosen = true;
							state.player2ChosenPlay = false;
							break;
						default:
							System.err.println("Play or Fold button was not able to change state");
							break;
					}

					updateUIAfterSelection();
				});
			}
		}
	}

	void updateUIAfterSelection() {
		if(state.player1Chosen && !state.player1UIUpdated) {
			if (state.player1ChosenPlay) {
				// Translate them up
			}
			else {
				for(ImageView card : uiCards.get(1)) {
					UIMisc.makeCardSmaller(card);
				}
			}

			// Disabling buttons
			uiButtons.get(0).setDisable(true);
			uiButtons.get(1).setDisable(true);

			state.player1UIUpdated = true;
		}
		if(state.player2Chosen && !state.player2UIUpdated) {
			if (state.player2ChosenPlay) {
				// Translate them up
			}
			else {
				for(ImageView card : uiCards.get(2)) {
					UIMisc.makeCardSmaller(card);
				}
			}

			// Disabling buttons
			uiButtons.get(3).setDisable(true);
			uiButtons.get(4).setDisable(true);

			state.player2UIUpdated = true;
		}

		// If both players made a selection
		if (state.player1Chosen && state.player2Chosen) {

			// Open dealer cards
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
				private int i = 0;
				public void handle(ActionEvent event) {
					UIMisc.openCard(uiCards.get(0).get(i), theDealer.getHand().get(i).toResourceName(), null);
					i++;
				}
			}));

			// When cards are shown
			timeline.setOnFinished(event -> {

				// Calculate winner
				evaluateHands();

				state.gameStarted = false;

				// Making deal btn as start btn
				uiButtons.get(2).setOnAction(startBtnHandler);
				uiButtons.get(2).setText(GameConstants.startBtnText);
				uiButtons.get(2).setDisable(false);

			});
			timeline.setCycleCount(3);
			timeline.play();
		}


	}


	void evaluateHands() {
		if (state.player1ChosenPlay) {
			int res1 = ThreeCardLogic.compareHands(theDealer.getHand(), playerOne.getHand());
			if (res1 == 1) {
				System.out.println("P1: dealer wins");
			} else if (res1 == 2) {
				System.out.println("P1: player wins");
			} else {
				System.out.println("P1: NO ONE wins");
			}
		} else {
			System.out.println("P1 loses because of the fold");
		}

		if (state.player2ChosenPlay) {
			int res2 = ThreeCardLogic.compareHands(theDealer.getHand(), playerTwo.getHand());
			if (res2 == 1) {
				System.out.println("P2: dealer wins");
			} else if (res2 == 2) {
				System.out.println("P2: player wins");
			} else {
				System.out.println("P2: NO ONE wins");
			}
		} else {
			System.out.println("P2 loses because of the fold");
		}
	}


	// Restoring game to initial state after playing
	void restoreGame() {

		// Restoring cards size
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GameConstants.cardScaleAnimationTime), ev -> {
			if (state.player1Chosen) {
				if (state.player1ChosenPlay) {

				}
				else {
					for(ImageView card: uiCards.get(1)) {
						UIMisc.restoreCardSize(card);
					}
				}
			}

			if (state.player2Chosen) {
				if (state.player2ChosenPlay) {

				}
				else {
					for(ImageView card: uiCards.get(2)) {
						UIMisc.restoreCardSize(card);
					}
				}
			}
		}));

		// When cards would be restores
		timeline.setOnFinished(ev -> {

			// Delay is needed since restoring card size uses animation
			Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(GameConstants.cardScaleAnimationTime), ev2 -> {

				// Closing cards
				for(ArrayList<ImageView> arr : uiCards) {
					for (ImageView card : arr) {
						UIMisc.closeCard(card, null);
					}
				}

				// Restoring state
				state.init();

				// Restoring input fields
				for(TextField field : uiInputs)
					field.setDisable(false);

				// Restoring buttons
				uiButtons.get(2).setDisable(false);

			}));
			timeline2.play();
		});
		timeline.play();
	}

}
