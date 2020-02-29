import UI.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

	EventHandler<ActionEvent> startBtnHandler, dealBtnHandler;
	MyHandler onBetChange;

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

		// Creating UI.UI
//		ToolBar toolbar = UI.createToolbar();
		ImageView dealer = UI.createDealerImage();
		StackPane table = UI_Table.createTable(uiCards, uiInputs);
		HBox playButtons = UI.createGameButtons(uiButtons);
//		HBox moneyBox = UI.createMoneyBox(uiText);

		this.createEventHandlers();
		this.setEventHandlers();

		// Setting up UI.UI elements
		this.gameToInitialState();

		VBox root = new VBox(UI.spacer(30), dealer, table, playButtons);
		root.setId("sceneVBox");

		Scene scene = new Scene(root, GameConstants.globalWidth, GameConstants.globalHeight);
		scene.getStylesheets().add("ThreeCardPokerGame.css");

		stage.setScene(scene);
		stage.setTitle("Three Card Poker");
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

			// Showing cards to players
			uiCards.get(1).get(0).flip(e1 -> uiCards.get(1).get(1).flip(e2 -> uiCards.get(1).get(2).flip(e3 -> {
				uiCards.get(2).get(0).flip(e4 -> uiCards.get(2).get(1).flip(e5 -> uiCards.get(2).get(2).flip(onFinish -> {
					// Enabling Start and Fold buttons
					uiButtons.get(0).setDisable(false);
					uiButtons.get(1).setDisable(false);
					uiButtons.get(3).setDisable(false);
					uiButtons.get(4).setDisable(false);
				})));
			})));
		};

		// Handler to run whenever players bet value is changed
		onBetChange = dummy -> {
			if (!state.gameStarted) {
				boolean disable = ((playerOne.getAnteBet() >= GameConstants.minBet && playerOne.getAnteBet() <= GameConstants.maxBet)
						&& (playerTwo.getAnteBet() >= GameConstants.minBet && playerTwo.getAnteBet() <= GameConstants.maxBet));
				uiButtons.get(2).setDisable(!disable);
			}
		};
	}


	void setEventHandlers() {
		// Cheat: Flip dealers card on click
		for(UICard card : uiCards.get(0)) {
			card.setOnMouseClicked(e -> card.flip(onFinish -> card.flip(null) ));
		}

		// Handlers for input fields
		uiInputs.get(0).setOnSuccessChange( newValue -> playerOne.setPairPlusBet(newValue) );
		uiInputs.get(1).setOnSuccessChange( newValue -> playerTwo.setPairPlusBet(newValue) );
		uiInputs.get(2).setOnSuccessChange( newValue -> playerOne.setAnteBet(newValue) );
		uiInputs.get(3).setOnSuccessChange( newValue -> playerTwo.setAnteBet(newValue) );

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
			// Scaling cards if Fold
			if (!state.player1ChosenPlay)
				for(UICard card : uiCards.get(1))
					card.makeSmaller();

			// Disabling buttons
			uiButtons.get(0).setDisable(true);
			uiButtons.get(1).setDisable(true);
			state.player1UIUpdated = true;
		}

		// If player2 chose something
		if(state.player2Chosen && !state.player2UIUpdated) {
			// Scaling cards if fold
			if (!state.player2ChosenPlay)
				for(UICard card : uiCards.get(2))
					card.makeSmaller();

			// Disabling buttons
			uiButtons.get(3).setDisable(true);
			uiButtons.get(4).setDisable(true);
			state.player2UIUpdated = true;
		}

		// If both players made a selection
		if (state.player1Chosen && state.player2Chosen) {
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
		}
	}


	// Initializes UICards with appropriate cards form hand
	void assignHandToUICards(ArrayList<Card> hand, ArrayList<UICard> cards) {
		for(byte i=0; i<hand.size(); i++) {
			cards.get(i).setFrontImage(hand.get(i).toResourceName());
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
	}

}
