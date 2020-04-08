package UI;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class UIGameButtons extends HBox {

    private Button playerOneMoneyBtn;
    private Button playerTwoMoneyBtn;

    public Button p1PlayBtn, p1FoldBtn;
    public Button dealPlayBtn;
    public Button p2PlayBtn, p2FoldBtn;

    private String moneyPrefix = "$";

    public UIGameButtons() {
        super();

        playerOneMoneyBtn = createMoneyButton();
        p1PlayBtn = createButton("Play", "playButton", "btnPlay1");
        p1FoldBtn = createButton("Fold", "foldButton", "btnFold1");

        dealPlayBtn = createButton("Deal", "dealButton", "btnDeal");

        p2PlayBtn = createButton("Play", "playButton", "btnPlay2");
        p2FoldBtn = createButton("Fold", "foldButton", "btnFold2");
        playerTwoMoneyBtn = createMoneyButton();
        playerTwoMoneyBtn.setContentDisplay(ContentDisplay.RIGHT);

        this.getStyleClass().addAll("buttonsRow");
        createButtonBoxes();
    }

    private void createButtonBoxes() {
        // Left side
        HBox leftButtonsBox = new HBox(
                playerOneMoneyBtn,
                UIMisc.leftSpacer(10),
                p1FoldBtn,
                p1PlayBtn
        );
        leftButtonsBox.getStyleClass().add("buttonsBox");

        // Right side
        HBox rightButtonsBox = new HBox(
                p2PlayBtn,
                p2FoldBtn,
                UIMisc.leftSpacer(10),
                playerTwoMoneyBtn
        );
        rightButtonsBox.getStyleClass().add("buttonsBox");

        this.getChildren().addAll(leftButtonsBox, dealPlayBtn, rightButtonsBox);
    }

    private Button createButton(String text, String className, String id) {
        Button b = new Button(text);
        b.getStyleClass().addAll("gameBtn", "shadow", className);
        b.setId(id);

        // Setting tooltips
//        if (text.equals("Play")) {
//            Tooltip t = new Tooltip("Doubles the Ante Bet");
//            t.setShowDelay(Duration.millis(100));
//            b.setTooltip(t);
//        }

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
