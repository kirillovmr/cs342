package UI;

import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class UITable extends StackPane {

    // Dealers / Players cards
    public ArrayList<UICard> dealersCards, p1Cards, p2Cards;

    // Warning text in the middle of the table
    public Timeline showWarningText;
    public Text warningText;

    // Dealing with inputs
    public ArrayList<UITextField> uiInputs;
    public ArrayList<ArrayList<ImageView>> uiChips;

    // Text to display hand value && to display game updates
    public UITextRow playersCardText, infoText;

    // Main table elements
    private Rectangle tableBack, tableFront;

    // For style change purposes
    private boolean showingRawStyle;
    private ImagePattern tableFrontPattern;

    public UITable() {
        super();

        this.dealersCards = new ArrayList<>();
        this.p1Cards = new ArrayList<>();
        this.p2Cards = new ArrayList<>();

        this.warningText = new Text();

        this.uiInputs = new ArrayList<>(); // [0],[1] - Pair plus, [2],[3] - Ante;
        this.uiChips = new ArrayList<>();
        for (byte i=0; i<4; i++)
            uiChips.add(new ArrayList<>());

        showingRawStyle = true;
        tableFrontPattern = new ImagePattern(Objects.requireNonNull(UIMisc.createImage("bg_darkgreen.png")));

        createTable();
    }

    // Flips the view of the table
    public void flipView() {
        if (showingRawStyle) {
            tableBack.setFill(Paint.valueOf("#202125"));
            tableFront.setFill(tableFrontPattern);
        }
        else {
            tableBack.setFill(Paint.valueOf(GameConstants.tableBackFill));
            tableFront.setFill(Paint.valueOf(GameConstants.tableFrontFill));
        }

        showingRawStyle = !showingRawStyle;
    }

    private void createTable() {
        // Back side of the table
        tableBack = new Rectangle(0, 0, GameConstants.tableWidth, GameConstants.tableHeight);
        tableBack.getStyleClass().addAll("tableShadow");
        tableBack.setId("table-back");
        tableBack.setArcWidth(GameConstants.tableWidth * 0.5);
        tableBack.setArcHeight(GameConstants.tableHeight);

        // Front side of the table
        tableFront = new Rectangle(0, 0, GameConstants.tableWidth * 0.9, GameConstants.tableHeight * 0.9);
        tableFront.getStyleClass().addAll("tableShadow");
        tableFront.setId("table-front");
        tableFront.getStyleClass().addAll("shadow");
        tableFront.setArcWidth(GameConstants.tableWidth * 0.9 * 0.5);
        tableFront.setArcHeight(GameConstants.tableHeight * 0.9);

        // Dealers Cards
        HBox dealerCardsBox = UICard.createCardBox(dealersCards, "dealerCard");
        dealerCardsBox.getStyleClass().add("dealerCardsBox");

        // Chip stacks
        UIChipStack letfChips = new UIChipStack();
        letfChips.getStyleClass().add("mainChipStack");
        VBox leftChipsBox = new VBox(UIMisc.spacer(50), letfChips);
        UIChipStack rightChips = new UIChipStack();
        rightChips.setVisible(false);

        // Info Text Row
        infoText = new UITextRow("textWhite", 320);
        VBox infoTextBox = new VBox(UIMisc.spacer(20), infoText);

        // Dealers Row
        HBox dealerRow = new HBox(leftChipsBox, dealerCardsBox, rightChips);
        dealerRow.getStyleClass().add("dealerRow");

        // Pair Plus Row
        HBox pairPlusRow = createWagerRow("", "PAIR PLUS", "chip_black.png", uiChips.get(0), uiChips.get(1));
        pairPlusRow.getStyleClass().add("pairPlusRow");

        // Ante Row
        HBox anteRow = createWagerRow(Integer.toString(GameConstants.minBet), "ANTE WAGER", "chip_red.png", uiChips.get(2), uiChips.get(3));
        anteRow.getStyleClass().add("anteRow");

        // Card Text Row
        playersCardText = new UITextRow("textGreen", 250);

        // Players Cards Row
        HBox playersCardsRow = new HBox(
                UICard.createCardBox(p1Cards, "player1Card"),
                UICard.createCardBox(p2Cards, "player2Card")
        );
        playersCardsRow.getStyleClass().add("playersCardsRow");

        // Bottom Box
        VBox bottomBox = new VBox(pairPlusRow, anteRow, playersCardText, playersCardsRow);
        bottomBox.getStyleClass().add("bottomBox");

        // BorderPane for everything
        BorderPane pane = new BorderPane();
        pane.setTop(dealerRow);
        pane.setCenter(infoTextBox);
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
