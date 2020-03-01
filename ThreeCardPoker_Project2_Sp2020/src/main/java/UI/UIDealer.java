package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UIDealer extends StackPane {

    ImageView dealerView;

    public UIDealer() {
        super();

        this.getChildren().add(createDealerImage());
        this.setAlignment(Pos.BOTTOM_CENTER);

        // Creating and placing smoke
        ImageView smoke = UIMisc.createImageView("smoke.gif", 0.2);
        StackPane smokeStack = new StackPane(smoke);
        smokeStack.setPadding(new Insets(0,38,0,0));
        VBox box = new VBox(
                UIMisc.spacer(30), // Top padding before dealer
                smokeStack,
                UIMisc.spacer(76) // Aligning smoke
        );
        this.getChildren().add(box);
    }


    public ImageView createDealerImage() {
        Image dealerImage = UIMisc.createImage(GameConstants.dealerFilename);

        dealerView = new ImageView(dealerImage);
        dealerView.setFitWidth(dealerImage.getWidth() * GameConstants.dealerScale);
        dealerView.setFitHeight(dealerImage.getHeight() * GameConstants.dealerScale);
        dealerView.getStyleClass().addAll("dealerImage");
        return dealerView;
    }
}
