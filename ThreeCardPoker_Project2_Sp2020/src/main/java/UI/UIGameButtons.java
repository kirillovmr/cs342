package UI;

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
        HBox leftButtonsBox = new HBox(
                createButton("Play", "playButton", "btnPlay1"),
                createButton("Fold", "foldButton", "btnFold1")
        );
        leftButtonsBox.getStyleClass().add("buttonsBox");

        Button dealButton = createButton("Deal", "dealButton", "btnDeal");

        HBox rightButtonsBox = new HBox(
                createButton("Play", "playButton", "btnPlay2"),
                createButton("Fold", "foldButton", "btnFold2")
        );
        rightButtonsBox.getStyleClass().add("buttonsBox");

        this.getChildren().addAll(leftButtonsBox, dealButton, rightButtonsBox);
    }

    private Button createButton(String text, String className, String id) {
        Button button = new Button(text);
        button.getStyleClass().addAll("gameBtn", "shadow", className);
        button.setId(id);

        uiButtons.add(button);
        return button;
    }
}
