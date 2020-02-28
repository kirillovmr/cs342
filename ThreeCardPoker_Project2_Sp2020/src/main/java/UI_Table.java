import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class UI_Table {

    static HBox createWagerBox(String value, String text, boolean left, ArrayList<TextField> uiInputs) throws FileNotFoundException {
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

    static HBox createWagerRow(String value, int maxWidth, String text, ArrayList<TextField> uiInputs) throws FileNotFoundException {
        HBox wagerRow = new HBox(
            createWagerBox(value, text, true, uiInputs),
            createWagerBox(value, text, false, uiInputs)
        );
        wagerRow.getStyleClass().add("wagerRow");
        return  wagerRow;
    }

    static HBox createPlayersCardsRow(ArrayList<ArrayList<ImageView>> uiCards) throws Exception {
        HBox playersCardsRow = new HBox(
            UI.createCardBox(uiCards.get(1)),
            UI.createCardBox(uiCards.get(2))
        );
        playersCardsRow.getStyleClass().add("playersCardsRow");
        return playersCardsRow;
    }

    static StackPane createTable(
            int globalWidth, int globalHeight, ArrayList<ArrayList<ImageView>> uiCards, ArrayList<TextField> uiInputs
    ) throws Exception {
        final int tableWidth = (int) (globalWidth * 0.7);
        final int tableHeight = (int) (globalHeight * 0.5);

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
        HBox dealerCardsBox = UI.createCardBox(uiCards.get(0));
        dealerCardsBox.getStyleClass().add("dealerCardsBox");

        // Pair Plus Row
        HBox pairPlusRow = createWagerRow("5", 110, "PAIR PLUS", uiInputs);
        pairPlusRow.getStyleClass().add("pairPlusRow");

        // Ante Row
        HBox anteRow = createWagerRow("5", 110, "ANTE WAGER", uiInputs);
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
