package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MyController implements Initializable {

    public ImageView coffeeImage;
    public ImageView extraShotImage, creamImage, milkImage, sugarImage, honeyImage, iceImage;
    public CheckBox extraShotCheck, creamCheck, milkCheck, sugarCheck, honeyCheck, iceCheck;
    public VBox orderBox;
    public Text totalText;

    Coffee order = new BasicCoffee();
    double cost = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		coffeeImage.setImage(createImage("coffee.jpg"));
        extraShotImage.setImage(createImage("shot.png"));
        creamImage.setImage(createImage("cream.png"));
        milkImage.setImage(createImage("milk.png"));
        sugarImage.setImage(createImage("sugar.png"));
        honeyImage.setImage(createImage("honey.png"));
        iceImage.setImage(createImage("ice.png"));
	}



    public Image createImage(String filename) {
        try {
            return new Image(new FileInputStream("src/main/resources/images/" + filename));
        }
        catch (FileNotFoundException e) {
            System.err.println(">< createImage: File " + filename + " was not found");
            return null;
        }
    }


    public void placeOrder() {
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
    }

    public void clearOrder() {
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
    }
}
