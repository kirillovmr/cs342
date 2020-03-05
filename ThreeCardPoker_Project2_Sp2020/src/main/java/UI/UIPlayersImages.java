package UI;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class UIPlayersImages extends HBox {

    public UIPlayersImages() {
        super();

        this.getChildren().addAll(
                createImage("playerOne.png"),
                UIMisc.leftSpacer(GameConstants.globalWidth - 600),
                createImage("playerTwo.png")
        );
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setDisable(true);
    }

    public ImageView createImage(String filename) {
        Image img = UIMisc.createImage(filename);

        ImageView view = new ImageView(img);
        assert img != null;
        view.setFitWidth(img.getWidth() * GameConstants.dealerScale * 0.9);
        view.setFitHeight(img.getHeight() * GameConstants.dealerScale * 0.9);
        return view;
    }
}
