package UI;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class UIGameButtons extends HBox {

    ArrayList<Button> uiButtons;

    public UIGameButtons(ArrayList<Button> uiButtons) {
        super();
        this.uiButtons = uiButtons;

        createGameButtons();
        this.getStyleClass().addAll("buttonsRow");
    }

    public void createGameButtons() {
        // Left side
        HBox leftButtonsBox = new HBox(
                createMoneyButton(),
                UIMisc.leftSpacer(10),
                createButton("Play", "playButton", "btnPlay1"),
                createButton("Fold", "foldButton", "btnFold1")
        );
        leftButtonsBox.getStyleClass().add("buttonsBox");

        // Middle button
        Button dealButton = createButton("Deal", "dealButton", "btnDeal");

        // Right side
        HBox rightButtonsBox = new HBox(
                createButton("Play", "playButton", "btnPlay2"),
                createButton("Fold", "foldButton", "btnFold2"),
                UIMisc.leftSpacer(10),
                createMoneyButton()
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
        Button b = new Button("MONEY: $100");
        b.getStyleClass().addAll("gameBtn", "shadow", "moneyBtn");

        return b;
    }
}
