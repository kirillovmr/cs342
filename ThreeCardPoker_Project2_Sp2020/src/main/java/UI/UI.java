package UI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class UI {

    public static ToolBar createToolbar() {
        ToolBar toolBar = new ToolBar(
                new Button("New"),
                new Button("Options"),
                new Button("Exit")
        );
        return toolBar;
    }

    public static HBox spacer(int padding) {
        HBox spacer = new HBox();
        spacer.setPadding(new Insets(padding, 0, 0, 0));
        return spacer;
    }

    public static ImageView loadImage(String filename, double scale) {
        try {
            Image image = new Image(new FileInputStream("src/res/" + filename));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(image.getHeight() * scale);
            imageView.setFitWidth(image.getWidth() * scale);
            return imageView;
        }
        catch (FileNotFoundException e) {
            System.err.println(">< loadImage: File " + filename + " was not found");
            return null;
        }
    }

    public static ImageView createDealerImage() {
        double scale = (double)(GameConstants.globalWidth) / 3000;

        ImageView dealerImage = loadImage("dealer.png", scale);
        dealerImage.getStyleClass().addAll("dealerImage");
        return dealerImage;
    }

    public static ImageView createCardImage(String id) {
        double scale = (double)(GameConstants.globalWidth) / 8000;

        ImageView card = loadImage("card_back.png", scale);
        card.getStyleClass().addAll("card", "shadow");
        card.setId(id);
        return card;
    }

    public static HBox createCardBox(ArrayList<ImageView> uiPlayerCards, String idPrefix) {
        HBox cardBox = new HBox();

        for(byte i=0; i<3; i++) {
            uiPlayerCards.add(createCardImage(idPrefix + i));
            cardBox.getChildren().add( uiPlayerCards.get(i) );
        }

        cardBox.getStyleClass().add("cardBox");
        return cardBox;
    }

    public static HBox createGameButtons(ArrayList<Button> uiButtons) {
        HBox leftButtonsBox = new HBox(
                createButton("Play", "playButton", "btnPlay1", uiButtons),
                createButton("Fold", "foldButton", "btnFold1", uiButtons)
        );
        leftButtonsBox.getStyleClass().add("buttonsBox");

        Button dealButton = createButton("Deal", "dealButton", "btnDeal", uiButtons);

        HBox rightButtonsBox = new HBox(
                createButton("Play", "playButton", "btnPlay2", uiButtons),
                createButton("Fold", "foldButton", "btnFold2", uiButtons)
        );
        rightButtonsBox.getStyleClass().add("buttonsBox");

        HBox buttonsRow = new HBox(leftButtonsBox, dealButton, rightButtonsBox);
        buttonsRow.getStyleClass().add("buttonsRow");
        return buttonsRow;
    }

    static Button createButton(String text, String className, String id, ArrayList<Button> uiButtons) {
        Button button = new Button(text);
        button.setId(id);
        button.getStyleClass().addAll("gameBtn", "shadow", className);

        uiButtons.add(button);
        return button;
    }

    public static HBox createMoneyBox(ArrayList<Text> uiText) {
        Text p1 = new Text(Integer.toString(GameConstants.initialMoneyValue));
        uiText.add(p1);

        Text p2 = new Text(Integer.toString(GameConstants.initialMoneyValue));
        uiText.add(p2);

        HBox moneyRow = new HBox(p1, p2);
        moneyRow.getStyleClass().add("moneyRow");
        return moneyRow;
    }
}
