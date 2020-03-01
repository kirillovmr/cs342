package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class UITable extends StackPane {

    final int tableWidth = GameConstants.tableWidth;
    final int tableHeight = GameConstants.tableHeight;

    final String initInputValue = Integer.toString(GameConstants.minBet);

    ArrayList<ArrayList<UICard>> uiCards;
    ArrayList<UITextField> uiInputs;
    ArrayList<ArrayList<ImageView>> uiChips;
    Text warningText;

    public UITable(ArrayList<ArrayList<UICard>> uiCards, ArrayList<UITextField> uiInputs, ArrayList<ArrayList<ImageView>> uiChips, Text warningText) {
        super();

        this.uiCards = uiCards;
        this.uiInputs = uiInputs;
        this.uiChips = uiChips;
        this.warningText = warningText;

        createTable();
    }

    public void createTable() {
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
        HBox dealerCardsBox = UICard.createCardBox(uiCards.get(0), "dealerCard");
        dealerCardsBox.getStyleClass().add("dealerCardsBox");

        // Chip stacks
        UIChipStack letfChips = new UIChipStack();
        letfChips.getStyleClass().add("mainChipStack");
        VBox leftChipsBox = new VBox(UIMisc.spacer(50), letfChips);
        UIChipStack rightChips = new UIChipStack();
        rightChips.setVisible(false);

        // Dealers Row
        HBox dealerRow = new HBox(leftChipsBox, dealerCardsBox, rightChips);
        dealerRow.getStyleClass().add("dealerRow");

        // Pair Plus Row
        HBox pairPlusRow = createWagerRow("", "PAIR PLUS", "chip_black.png", uiChips.get(0), uiChips.get(1));
        pairPlusRow.getStyleClass().add("pairPlusRow");

        // Ante Row
        HBox anteRow = createWagerRow(initInputValue, "ANTE WAGER", "chip_red.png", uiChips.get(2), uiChips.get(3));
        anteRow.getStyleClass().add("anteRow");

        // Players Cards Row
        HBox playersCardsRow = new HBox(
                UICard.createCardBox(uiCards.get(1), "player1Card"),
                UICard.createCardBox(uiCards.get(2), "player2Card")
        );
        playersCardsRow.getStyleClass().add("playersCardsRow");

        // Bottom Box
        VBox bottomBox = new VBox(pairPlusRow, anteRow, playersCardsRow);
        bottomBox.getStyleClass().add("bottomBox");

        // BorderPane for everything
        BorderPane pane = new BorderPane();
        pane.setTop(dealerRow);
        pane.setBottom(bottomBox);

        this.getChildren().addAll(tableBack, tableFront, pane);
    }

    private HBox createWagerBox(String value, String text, boolean left, String chipFilename, ArrayList<ImageView> chips) {
        UITextField wagerInput = new UITextField(value);
        wagerInput.setAlignment(left ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);

        uiInputs.add(wagerInput);

        StackPane chipStack = new StackPane();
        for(byte i=0; i<5; i++) {
            ImageView wagerChip = UIMisc.createImageView(chipFilename, 0.07);
            wagerChip.getStyleClass().addAll("wagerChip", "shadow");
            chips.add(wagerChip);

            StackPane chipWrap = new StackPane(wagerChip);
            chipWrap.setPadding(new Insets(0,0,i*5,0));
            chipStack.getChildren().add(chipWrap);
        }

        Text wagerRowText = new Text(text);
        wagerRowText.getStyleClass().addAll("wagerRowText", "shadow");

        HBox wagerBox = left ? new HBox(wagerRowText, chipStack, wagerInput) : new HBox(wagerInput, chipStack, wagerRowText);
        wagerBox.getStyleClass().add("wagerBox");
        return wagerBox;
    }

    private HBox createWagerRow(String value, String text, String chipFilename, ArrayList<ImageView> chipsL, ArrayList<ImageView> chipsR) {
        HBox wagerRow = new HBox();

        wagerRow.getChildren().add(createWagerBox(value, text, true, chipFilename, chipsL));
        if (text.equals("PAIR PLUS")) {
            warningText.setText("BET RANGE: $" + GameConstants.minBet +" - $" + GameConstants.maxBet);
            warningText.getStyleClass().addAll("warningText", "shadow");
            warningText.setVisible(false);
            wagerRow.getChildren().add(warningText);
        }
        wagerRow.getChildren().add(createWagerBox(value, text, false, chipFilename, chipsR));

        wagerRow.getStyleClass().add("wagerRow");
        return  wagerRow;
    }
}
