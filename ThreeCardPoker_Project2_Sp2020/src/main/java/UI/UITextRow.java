package UI;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class UITextRow extends HBox {

    private Text playerOneText;
    private Text playerTwoText;

    public UITextRow(String customClassName, int spacing) {
        super();

        playerTwoText = new Text("Default text");
        playerOneText = new Text("Default text");
        playerOneText.setTextAlignment(TextAlignment.RIGHT);
        playerTwoText.setTextAlignment(TextAlignment.LEFT);

        playerOneText.getStyleClass().addAll("uiText", "shadow", customClassName);
        playerTwoText.getStyleClass().addAll("uiText", "shadow", customClassName);

        this.setSpacing(spacing);
        this.getStyleClass().add("textRow");
        this.getChildren().addAll(new HBox(playerOneText), new HBox(playerTwoText));
    }

    public void setPlayerOneText(String text) {
        if (text.length() == 0)
            this.playerOneText.setVisible(false);
        else {
            this.playerOneText.setText(text);
            this.playerOneText.setVisible(true);
        }
    }

    public void setPlayerTwoText(String text) {
        if (text.length() == 0)
            this.playerTwoText.setVisible(false);
        else {
            this.playerTwoText.setText(text);
            this.playerTwoText.setVisible(true);
        }
    }

    public void clearText() {
        setPlayerOneText("");
        setPlayerTwoText("");
    }
}
