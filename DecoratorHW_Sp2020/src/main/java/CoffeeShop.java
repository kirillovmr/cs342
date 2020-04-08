import UI.Misc;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CoffeeShop extends Application {

	Coffee order = new BasicCoffee();
	double cost = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Who want's coffee!!!");

		/*
			TODO: structure code

			Below you'll find a lot of unstructured code.
			I had no time for a good structure I followed in my previous projects and homework.
			So I have to say sorry for the code you will see in advance.
			Thank you!

		 */

		// Top coffee image
		ImageView coffeeImage = Misc.createImageView("coffee.jpg", 1);
		coffeeImage.setFitWidth(400);
		coffeeImage.setFitHeight(150);
		HBox coffeeImageBox = new HBox(coffeeImage);
		coffeeImageBox.setAlignment(Pos.CENTER);

		// Coffee text
		Text coffeeText = new Text("Basic Coffee");
		coffeeText.setTextAlignment(TextAlignment.CENTER);

		// Coffee description
		Text descriptionText = new Text("Coffee is a brewed drink prepared from roasted coffee beans,");
		descriptionText.setTextAlignment(TextAlignment.CENTER);
		Text descriptionText2 = new Text("the seeds of berries from certain Coffea species.");
		descriptionText2.setTextAlignment(TextAlignment.CENTER);
		VBox descriptionTextBox = new VBox(descriptionText, descriptionText2);
		descriptionTextBox.setSpacing(5);
		descriptionTextBox.setAlignment(Pos.CENTER);

		// Text + description box
		VBox textBox = new VBox(coffeeText, descriptionTextBox);
		textBox.setAlignment(Pos.CENTER);
		textBox.setSpacing(20);
		textBox.setPadding(new Insets(20, 0, 20, 0));

		// Toppings text
		Text toppingsText = new Text("Select extras:");
		HBox toppingsTextBox = new HBox(toppingsText);
		toppingsTextBox.setPadding(new Insets(0,0,0,10));

		// Toppings
		ImageView extraShotImage = Misc.createImageView("shot.png", 0.05);
		CheckBox extraShotCheck = new CheckBox("Extra Shot");
		HBox extraShotBox = new HBox(extraShotImage, extraShotCheck);
		extraShotBox.setAlignment(Pos.CENTER_LEFT);
		extraShotBox.setSpacing(5);

		ImageView creamImage = Misc.createImageView("cream.png", 0.05);
		CheckBox creamCheck = new CheckBox("Cream");
		HBox creamBox = new HBox(creamImage, creamCheck);
		creamBox.setAlignment(Pos.CENTER_LEFT);
		creamBox.setSpacing(5);

		ImageView sugarImage = Misc.createImageView("sugar.png", 0.05);
		CheckBox sugarCheck = new CheckBox("Sugar");
		HBox sugarBox = new HBox(sugarImage, sugarCheck);
		sugarBox.setAlignment(Pos.CENTER_LEFT);
		sugarBox.setSpacing(5);

		ImageView honeyImage = Misc.createImageView("honey.png", 0.05);
		CheckBox honeyCheck = new CheckBox("Honey");
		HBox honeyBox = new HBox(honeyImage, honeyCheck);
		honeyBox.setAlignment(Pos.CENTER_LEFT);
		honeyBox.setSpacing(5);

		ImageView milkImage = Misc.createImageView("milk.png", 0.05);
		CheckBox milkCheck = new CheckBox("Milk");
		HBox milkBox = new HBox(milkImage, milkCheck);
		milkBox.setAlignment(Pos.CENTER_LEFT);
		milkBox.setSpacing(5);

		ImageView iceImage = Misc.createImageView("ice.png", 0.05);
		CheckBox iceCheck = new CheckBox("Ice");
		HBox iceBox = new HBox(iceImage, iceCheck);
		iceBox.setAlignment(Pos.CENTER_LEFT);
		iceBox.setSpacing(5);

		// Toppings box
		VBox toppingsCol1 = new VBox(extraShotBox, creamBox, milkBox);
		toppingsCol1.setPadding(new Insets(0,0,0,10));
		toppingsCol1.setSpacing(10);
		toppingsCol1.setPrefWidth(200);
		VBox toppingsCol2 = new VBox(sugarBox, honeyBox, iceBox);
		toppingsCol2.setSpacing(10);
		HBox toppingsBox = new HBox(toppingsCol1, toppingsCol2);
		toppingsBox.setPadding(new Insets(10,0,0,0));

		// Order text
		Text orderText = new Text("Order summary:");
		HBox orderTextBox = new HBox(orderText);
		orderTextBox.setPadding(new Insets(20,0,0,10));

		// Order summary
		VBox orderBox = new VBox(new Text(" * will be available after placing an order"));
		orderBox.setSpacing(5);
		orderBox.setPadding(new Insets(10, 0, 0, 10));

		// Total text
		Text totalText = new Text("Total: $0.00");
		totalText.setVisible(false);
		HBox totalTextBox = new HBox(totalText);
		totalTextBox.setAlignment(Pos.CENTER);
		totalTextBox.setPadding(new Insets(10,0,10,0));

		// Buttons
		Button createOrderButton = new Button("Place order !");
		createOrderButton.setPrefWidth(380);
		createOrderButton.setBackground(new Background(new BackgroundFill(Color.web("#B79F8F"), CornerRadii.EMPTY, Insets.EMPTY)));
		Button clearOrderButton = new Button("Clear");
		clearOrderButton.setPrefWidth(380);
		clearOrderButton.setBackground(new Background(new BackgroundFill(Color.web("#A66059"), CornerRadii.EMPTY, Insets.EMPTY)));
		VBox buttonsBox = new VBox(totalTextBox, createOrderButton, clearOrderButton);
		buttonsBox.setSpacing(5);
		buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonsBox.setPadding(new Insets(0,0,10,0));

		createOrderButton.setOnAction(e -> {
			orderBox.getChildren().remove(0, orderBox.getChildren().size());
			orderBox.getChildren().add(new Text(BasicCoffee.getText()));

			if (extraShotCheck.isSelected()) {
				order = new ExtraShot(order);
				orderBox.getChildren().add(new Text(ExtraShot.getText()));
			}
			if (sugarCheck.isSelected()) {
				order = new Sugar(order);
				orderBox.getChildren().add(new Text(Sugar.getText()));
			}
			if (creamCheck.isSelected()) {
				order = new Cream(order);
				orderBox.getChildren().add(new Text(Cream.getText()));
			}
			if (honeyCheck.isSelected()) {
				order = new Honey(order);
				orderBox.getChildren().add(new Text(Honey.getText()));
			}
			if (milkCheck.isSelected()) {
				order = new Milk(order);
				orderBox.getChildren().add(new Text(Milk.getText()));
			}
			if (iceCheck.isSelected()) {
				order = new Ice(order);
				orderBox.getChildren().add(new Text(Ice.getText()));
			}
			cost = order.makeCoffee();

			totalText.setText("Total: $" + Math.round(cost * 100.0) / 100.0);
			totalText.setVisible(true);
			order = new BasicCoffee();
		});

		clearOrderButton.setOnAction(e -> {
			order = new BasicCoffee();
			extraShotCheck.setSelected(false);
			sugarCheck.setSelected(false);
			creamCheck.setSelected(false);
			honeyCheck.setSelected(false);
			milkCheck.setSelected(false);
			iceCheck.setSelected(false);
			totalText.setVisible(false);
			orderBox.getChildren().remove(0, orderBox.getChildren().size());
			orderBox.getChildren().add(new Text(" * will be available after placing an order"));
		});

		// Main Vertical Box
		VBox mainVBox = new VBox(
				coffeeImage,
				textBox,
				toppingsTextBox,
				toppingsBox,
				orderTextBox,
				orderBox
		);

		// Border pane for alignment
		BorderPane pane = new BorderPane();
		pane.setTop(mainVBox);
		pane.setBottom(buttonsBox);
		pane.setBackground(new Background(new BackgroundFill(Color.web("#F6F5F1"), CornerRadii.EMPTY, Insets.EMPTY)));

		Scene scene = new Scene(pane,400,670);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
