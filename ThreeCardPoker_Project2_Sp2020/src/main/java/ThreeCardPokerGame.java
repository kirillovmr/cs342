import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.Math.random;

public class ThreeCardPokerGame extends Application {

	final int globalWidth = 1024;
	final int globalHeight = 768;

	Player playerOne;
	Player playerTwo;
	Dealer theDealer;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(10);

		ToolBar toolbar = createToolbar();
		ImageView dealer = createDealerImage();
		StackPane table = createTable();

		vbox.getChildren().addAll(toolbar, dealer, table);


		Scene scene = new Scene(vbox, globalWidth, globalHeight);
		scene.getStylesheets().add("ThreeCardPokerGame.css");

		stage.setScene(scene);
		stage.setTitle("Improved Hello JavaFX Application");
		stage.show();
	}

	ToolBar createToolbar() {
		ToolBar toolBar = new ToolBar(
				new Button("New"),
				new Button("Options"),
				new Button("Exit")
		);

		return toolBar;
	}

	ImageView createDealerImage() throws FileNotFoundException {
		//Creating an image
		Image image = new Image(new FileInputStream("src/res/dealer.png"));

		//Setting the image view
		ImageView imageView = new ImageView(image);

		imageView.setFitHeight(image.getHeight() * 0.3);
		imageView.setFitWidth(image.getWidth() * 0.3);

		return imageView;
	}

	ImageView createCardImage() throws FileNotFoundException {
		//Creating an image
		Image image = new Image(new FileInputStream("src/res/card_back.png"));

		//Setting the image view
		ImageView imageView = new ImageView(image);

		imageView.setFitHeight(image.getHeight() * 0.1);
		imageView.setFitWidth(image.getWidth() * 0.1);

		return imageView;
	}

	ImageView loadImage(String name, double ratio) throws Exception {
		//Creating an image
		Image image = new Image(new FileInputStream("src/res/" + name));

		//Setting the image view
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(image.getHeight() * ratio);
		imageView.setFitWidth(image.getWidth() * ratio);

		return imageView;
	}

	HBox createCardBox() throws Exception {
		HBox cardBox = new HBox();
		cardBox.setSpacing(10);
		for (int i=0; i<3; i++)
			cardBox.getChildren().add(createCardImage());

		return cardBox;
	}

	StackPane createTable() throws Exception {
		final int tableWidth = (int) (globalWidth * 0.7);
		final int tableHeight = (int) (globalHeight * 0.5);

		StackPane stack = new StackPane();

		// Back side of the table
		Rectangle tableBack = new Rectangle(0, 0, tableWidth, tableHeight);
		tableBack.setId("table-back");
		tableBack.setArcWidth(tableWidth * 0.5);
		tableBack.setArcHeight(tableHeight);

		// Front side of the table
		Rectangle tableFront = new Rectangle(0, 0, tableWidth * 0.9, tableHeight * 0.9);
		tableFront.setId("table-front");
		tableFront.setArcWidth(tableWidth * 0.9 * 0.5);
		tableFront.setArcHeight(tableHeight * 0.9);

		// Dealers Cards
		HBox dealerCardsBox = createCardBox();
		dealerCardsBox.setAlignment(Pos.TOP_CENTER);
		dealerCardsBox.setPadding(new Insets(30,0,0,0));


		VBox leftPlayer = createPlayerBox(true);
		leftPlayer.setAlignment(Pos.BASELINE_LEFT);
		VBox rightPlayer = createPlayerBox(false);


		// Combining players
		HBox playersBox = new HBox();
		playersBox.setAlignment(Pos.BOTTOM_CENTER);
		playersBox.setPadding(new Insets(0, 0, 30, 0));
		playersBox.getChildren().addAll(leftPlayer, rightPlayer);

		// BorderPane for everything
		BorderPane pane = new BorderPane();
		pane.setTop(dealerCardsBox);
		pane.setBottom(playersBox);

		stack.getChildren().addAll(tableBack, tableFront, pane);

		return stack;
	}

	VBox createPlayerBox(boolean left) throws Exception {
		// Player cards
		HBox playerCardsBox = createCardBox();
		playerCardsBox.setAlignment(left ? Pos.BASELINE_LEFT : Pos.BASELINE_RIGHT);
		playerCardsBox.setPadding(new Insets(0, left ? 40 : 0, 0, left ? 0 : 40));

		// Pair plus wager
		TextField pairPlusField = new TextField("5");
		pairPlusField.setAlignment(left ? Pos.BASELINE_LEFT : Pos.BASELINE_RIGHT);
		pairPlusField.setMaxWidth(60);
		ImageView pairPlusChip = loadImage("chip.png", 0.07);

		// Ante wager
		TextField anteField = new TextField("5");
		anteField.setAlignment(left ? Pos.BASELINE_LEFT : Pos.BASELINE_RIGHT);
		anteField.setMaxWidth(60);
		ImageView anteChip = loadImage("chip.png", 0.07);

		HBox pairPlusBox = new HBox();
		pairPlusBox.setSpacing(5);
		BorderPane pairPlusPane = new BorderPane();

		HBox anteBox = new HBox();
		anteBox.setSpacing(5);
		BorderPane antePane = new BorderPane();

		if (left) {
			pairPlusBox.getChildren().addAll(pairPlusChip, pairPlusField);
			anteBox.getChildren().addAll(anteChip, anteField);
			pairPlusPane.setLeft(pairPlusBox);
			antePane.setLeft(anteBox);
		} else {
			pairPlusBox.getChildren().addAll(pairPlusField, pairPlusChip);
			anteBox.getChildren().addAll(anteField, anteChip);
			pairPlusPane.setRight(pairPlusBox);
			antePane.setRight(anteBox);
		}

		// Combining bet and cards
		VBox playerBox = new VBox();
		playerBox.setSpacing(10);
		playerBox.getChildren().addAll(pairPlusPane, antePane, playerCardsBox);

		return playerBox;
	}

}
