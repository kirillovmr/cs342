import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreeCardPokerGame extends Application {

	final static int globalWidth = 1440;
	final static int globalHeight = 1080;
//
//	final static int globalWidth = 800;
//	final static int globalHeight = 600;

//	final static int globalWidth = 1024;
//	final static int globalHeight = 768;

	Player playerOne, playerTwo;
	Dealer theDealer;

	ArrayList<TextField> uiInputs; // [0], [1] - Pair plus, [2], [3] - Ante
	ArrayList< ArrayList<ImageView> > uiCards; // [0] is a dealer, [1], [2] are cards of players 1 and 2 respectively

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		playerOne = new Player();
		playerTwo = new Player();
		theDealer = new Dealer();

		// Initializing UI Lists
		uiInputs = new ArrayList<>();
		uiCards = new ArrayList<>();
		for (byte i=0; i<3; i++)
			uiCards.add(new ArrayList<>());

		// Creating UI
		ToolBar toolbar = UI.createToolbar();
		ImageView dealer = UI.createDealerImage();
		StackPane table = UI_Table.createTable(globalWidth, globalHeight, uiCards, uiInputs);
		HBox playButtons = UI.createGameButtons();

		// Setting UI elements
		setupUIElements();

		Random r = new Random();
		AtomicBoolean back = new AtomicBoolean(true);
		uiCards.get(0).get(0).setOnMouseClicked(e -> {
			back.set(!back.get());

			if (back.get()) {
				try {
					closeCard(uiCards.get(0).get(0));
				} catch (FileNotFoundException ex) {}
			}
			else {
				int num = r.nextInt(13) + 2;
				try {
					openCard(uiCards.get(0).get(0), "H" + num + ".png");
				} catch (FileNotFoundException ex) {}
			}
		});

		HBox spacer = new HBox();
		spacer.setPadding(new Insets(30, 0, 0, 0));

		VBox vbox = new VBox();
		vbox.setId("sceneVBox");
		vbox.getChildren().addAll(spacer, dealer, table, playButtons);

		Scene scene = new Scene(vbox, globalWidth, globalHeight);
		scene.getStylesheets().add("ThreeCardPokerGame.css");

		stage.setScene(scene);
		stage.setTitle("Three Card Poker");
		stage.show();
	}

	void openCard(ImageView card, String filename) throws FileNotFoundException {
		card.setImage(new Image(new FileInputStream("src/res/cards/" + filename)));
	}

	void closeCard(ImageView card) throws FileNotFoundException {
		card.setImage(new Image(new FileInputStream("src/res/card_back.png")));
	}

	void setupUIElements() {

		ArrayList<Method> setters = new ArrayList(4);
		try {
			setters.add(playerOne.getClass().getMethod("setPairPlusBet"));
			setters.add(playerTwo.getClass().getMethod("setPairPlusBet"));
			setters.add(playerOne.getClass().getMethod("setAnteBet"));
			setters.add(playerTwo.getClass().getMethod("setAnteBet"));
		}
		catch(Exception e) {}

		for (byte i=0; i<4; i++) {
			byte finalI = i;
			uiInputs.get(finalI).textProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.length() > 0) {
					try {
						int val = Integer.parseInt(newValue);

						Player p = finalI % 2 == 0 ? playerOne : playerTwo;
						if(finalI < 2)
							p.setPairPlusBet(val);
						else
							p.setAnteBet(val);

						System.out.println("" + Integer.toString(finalI) + "New value: " + newValue);
					} catch (NumberFormatException nfe) {
						uiInputs.get(finalI).setText(oldValue);
					}
				}
			});
		}
	}

}
