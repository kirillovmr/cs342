package UI;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class UIGameButtons extends HBox {

    ArrayList<Button> uiButtons;
    Button playerOneMoneyBtn = new Button();
    Button playerTwoMoneyBtn = new Button();

    private String moneyPrefix = "$";

    public UIGameButtons(ArrayList<Button> uiButtons) {
        super();
        this.uiButtons = uiButtons;

        createGameButtons();
        this.getStyleClass().addAll("buttonsRow");
    }

    public void createGameButtons() {
        // Left side
        playerOneMoneyBtn = createMoneyButton();
        HBox leftButtonsBox = new HBox(
                playerOneMoneyBtn,
                UIMisc.leftSpacer(10),
                createButton("Play", "playButton", "btnPlay1"),
                createButton("Fold", "foldButton", "btnFold1")
        );
        leftButtonsBox.getStyleClass().add("buttonsBox");

        // Middle button
        Button dealButton = createButton("Deal", "dealButton", "btnDeal");

        // Right side
        playerTwoMoneyBtn = createMoneyButton();
        playerTwoMoneyBtn.setContentDisplay(ContentDisplay.RIGHT);
        HBox rightButtonsBox = new HBox(
                createButton("Play", "playButton", "btnPlay2"),
                createButton("Fold", "foldButton", "btnFold2"),
                UIMisc.leftSpacer(10),
                playerTwoMoneyBtn
        );
        rightButtonsBox.getStyleClass().add("buttonsBox");



        this.getChildren().addAll(leftButtonsBox, dealButton, rightButtonsBox);
    }

    private Button createButton(String text, String className, String id) {
        Button b = new Button(text);
        b.getStyleClass().addAll("gameBtn", "shadow", className);
        b.setId(id);

        uiButtons.add(b);
        return b;
    }

    private Button createMoneyButton() {
        Button b = new Button(moneyPrefix);
        StackPane img = new StackPane(UIMisc.createImageView("money.png", 0.1));
        img.getStyleClass().addAll("moneyImg", "shadow");
        b.setGraphic(img);
        b.getStyleClass().addAll("gameBtn", "shadow", "moneyBtn");

        return b;
    }

    public void setPlayerOneMoney(int val) {
        this.playerOneMoneyBtn.setText(moneyPrefix + val);
    }

    public void setPlayerTwoMoney(int val) {
        this.playerTwoMoneyBtn.setText(moneyPrefix + val);
    }
}
