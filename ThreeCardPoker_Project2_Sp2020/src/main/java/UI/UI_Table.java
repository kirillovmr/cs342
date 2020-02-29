package UI;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class UI_Table {

    public static HBox createWagerBox(String value, String text, boolean left, ArrayList<TextField> uiInputs) {
        TextField wagerInput = new TextField(value);
        wagerInput.getStyleClass().addAll("wagerInput", "shadow");
        wagerInput.setAlignment(left ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);

        uiInputs.add(wagerInput);

        ImageView wagerChip = UI.loadImage("chip.png", 0.07);
        wagerChip.getStyleClass().addAll("wagerChip", "shadow");

        Text wagerRowText = new Text(text);
        wagerRowText.getStyleClass().addAll("wagerRowText", "shadow");

        HBox wagerBox = left ? new HBox(wagerRowText, wagerChip, wagerInput) : new HBox(wagerInput, wagerChip, wagerRowText);
        wagerBox.getStyleClass().add("wagerBox");
        return wagerBox;
    }

    public static HBox createWagerRow(String value, int maxWidth, String text, ArrayList<TextField> uiInputs) {
        HBox wagerRow = new HBox(
            createWagerBox(value, text, true, uiInputs),
            createWagerBox(value, text, false, uiInputs)
        );
        wagerRow.getStyleClass().add("wagerRow");
        return  wagerRow;
    }

    public static HBox createPlayersCardsRow(ArrayList<ArrayList<ImageView>> uiCards) {
        HBox playersCardsRow = new HBox(
            UI.createCardBox(uiCards.get(1), "player1Card"),
            UI.createCardBox(uiCards.get(2), "player2Card")
        );
        playersCardsRow.getStyleClass().add("playersCardsRow");
        return playersCardsRow;
    }

    public static StackPane createTable(ArrayList<ArrayList<ImageView>> uiCards, ArrayList<TextField> uiInputs) {
        final int tableWidth = (int) (GameConstants.globalWidth * 0.7);
        final int tableHeight = (int) (GameConstants.globalHeight * 0.5);

        // Back side of the table
        Rectangle tableBack = new Rectangle(0, 0, tableWidth, tableHeight);
        tableBack.setId("table-back");
        tableBack.setArcWidth(tableWidth * 0.5);
        tableBack.setArcHeight(tableHeight);

        // Front side of the table
        Rectangle tableFront = new Rectangle(0, 0, tableWidth * 0.9, tableHeight * 0.9);
        tableFront.setId("table-front");
        tableFront.getStyleClass().addAll("shadow");
        tableFront.setArcWidth(tableWidth * 0.9 * 0.5);
        tableFront.setArcHeight(tableHeight * 0.9);

        // Dealers Cards
        HBox dealerCardsBox = UI.createCardBox(uiCards.get(0), "dealerCard");
        dealerCardsBox.getStyleClass().add("dealerCardsBox");

        // Pair Plus Row
        HBox pairPlusRow = createWagerRow("", 110, "PAIR PLUS", uiInputs);
        pairPlusRow.getStyleClass().add("pairPlusRow");

        // Ante Row
        HBox anteRow = createWagerRow(Integer.toString(GameConstants.minBet), 110, "ANTE WAGER", uiInputs);
        anteRow.getStyleClass().add("anteRow");

        // Players Cards Row
        HBox playersCardsRow = createPlayersCardsRow(uiCards);
        playersCardsRow.getStyleClass().add("playersCardsRow");

        // Bottom Box
        VBox bottomBox = new VBox(pairPlusRow, anteRow, playersCardsRow);
        bottomBox.getStyleClass().add("bottomBox");

        // BorderPane for everything
        BorderPane pane = new BorderPane();
        pane.setTop(dealerCardsBox);
        pane.setBottom(bottomBox);

        return new StackPane(tableBack, tableFront, pane);
    }
}