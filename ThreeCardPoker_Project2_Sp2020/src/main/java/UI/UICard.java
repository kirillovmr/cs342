package UI;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class UICard extends ImageView {
    Image backImage, frontImage;

    boolean showingFace = false;
    boolean animating = false;

    public UICard() {
        super(UIMisc.createImage(GameConstants.cardBackFilename));
        this.setFitHeight(this.getImage().getHeight() * GameConstants.cardScale);
        this.setFitWidth(this.getImage().getWidth() * GameConstants.cardScale);

        backImage = this.getImage();
        frontImage = null;

        this.setOnMouseClicked(e -> {
            flip(ev -> flip(null));
        });
    }

    public UICard(Image image) {
        super(image);
    }

    public void flip(EventHandler<ActionEvent> onFinish) {
        Timeline finishAnim = new Timeline(new KeyFrame(Duration.millis(GameConstants.cardFlipAnimationTime), event -> {
            animating = false;
        }));
        finishAnim.setOnFinished(onFinish);

        if (animating)
            return;

        animating = true;
        if (frontImage == null) {
            animateFlip(e -> { finishAnim.play(); });
            return;
        }

        animateFlip(e -> { finishAnim.play(); });
        this.setImage(showingFace ? backImage : frontImage);
        showingFace = !showingFace;
    }

    public void setFrontImage(String filename) {
        frontImage = UIMisc.createImage("cards/" + filename);
        if (showingFace)
            this.setImage(frontImage);
    }

    private void animateFlip(EventHandler<ActionEvent> onFinished) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(GameConstants.cardFlipAnimationTime), this);
        transition.setByX(-0.9f);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        transition.setOnFinished(onFinished);
        transition.play();
    }
}
