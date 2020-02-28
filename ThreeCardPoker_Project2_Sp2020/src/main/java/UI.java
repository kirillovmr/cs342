import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

    static ToolBar createToolbar() {
        ToolBar toolBar = new ToolBar(
                new Button("New"),
                new Button("Options"),
                new Button("Exit")
        );

        return toolBar;
    }


    static ImageView loadImage(String name, double scale) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/res/" + name));

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(image.getHeight() * scale);
        imageView.setFitWidth(image.getWidth() * scale);

        return imageView;
    }

    static ImageView createDealerImage() throws FileNotFoundException {
        double scale = (double)(ThreeCardPokerGame.globalWidth) / 2500;

        ImageView dealerImage = loadImage("dealer.png", scale);
        dealerImage.getStyleClass().addAll("dealerImage");
        return dealerImage;
    }

    static ImageView createCardImage() throws FileNotFoundException {
        double scale = (double)(ThreeCardPokerGame.globalWidth) / 8000;

        ImageView card = loadImage("card_back.png", scale);
        card.getStyleClass().addAll("card", "shadow");
        return card;
    }

    static HBox createCardBox(ArrayList<ImageView> uiPlayerCards) throws Exception {
        HBox cardBox = new HBox(
//                createCardImage(),
//                createCardImage(),
//                createCardImage()
        );

        for(int i=0; i<3; i++) {
            uiPlayerCards.add(createCardImage());
            cardBox.getChildren().add( uiPlayerCards.get(i) );
        }

        cardBox.getStyleClass().add("cardBox");
        return cardBox;
    }

    static Button createButton(String text, String className) {
        Button button = new Button(text);
        button.getStyleClass().addAll("gameBtn", "shadow", className);
        return button;
    }

    static HBox createGameButtons() {
        HBox leftButtonsBox = new HBox(
                createButton("Play", "playButton"),
                createButton("Fold", "foldButton")
        );
        leftButtonsBox.getStyleClass().add("buttonsBox");

        Button dealButton = createButton("Deal", "dealButton");

        HBox rightButtonsBox = new HBox(
                createButton("Play", "playButton"),
                createButton("Fold", "foldButton")
        );
        rightButtonsBox.getStyleClass().add("buttonsBox");

        HBox buttonsRow = new HBox(leftButtonsBox, dealButton, rightButtonsBox);
        buttonsRow.getStyleClass().add("buttonsRow");
        return buttonsRow;
    }
}
