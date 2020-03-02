package UI;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UIChipStack extends VBox {

    public UIChipStack() {
        super();

        int spaceBetweenChipBoxes = 10;

        StackPane chips11 = new StackPane();
        for(int i=0; i<12; i++)
            chips11.getChildren().add(createChip("chip_black.png", i));
        StackPane chips12 = new StackPane();
        for(int i=0; i<8; i++)
            chips12.getChildren().add(createChip("chip_black.png", i));
        HBox row1 = new HBox(chips11, UIMisc.leftSpacer(spaceBetweenChipBoxes), chips12);
        row1.getStyleClass().add("chipStackRow");

        StackPane chips21 = new StackPane();
        for(int i=0; i<9; i++)
            chips21.getChildren().add(createChip("chip_black.png", i));
        StackPane chips22 = new StackPane();
        for(int i=0; i<8; i++)
            chips22.getChildren().add(createChip("chip_black.png", i));
        StackPane chips32 = new StackPane();
        for(int i=0; i<6; i++)
            chips32.getChildren().add(createChip("chip_black.png", i));
        HBox row2 = new HBox(chips21, UIMisc.leftSpacer(spaceBetweenChipBoxes), chips22, UIMisc.leftSpacer(spaceBetweenChipBoxes), chips32);
        row2.getStyleClass().add("chipStackRow");

        VBox vbox = new VBox(row1, row2);
        vbox.setSpacing(-40);
        this.getChildren().add(vbox);
    }

    public static StackPane createChip(String filename, int bottomPadding) {
        ImageView wagerChip = UIMisc.createImageView(filename, 0.07);
        wagerChip.getStyleClass().addAll("wagerChip", "shadow");

        StackPane chipWrap = new StackPane(wagerChip);
        chipWrap.setPadding(new Insets(0, 0, bottomPadding * 5, 0));
        return chipWrap;
    }

}
