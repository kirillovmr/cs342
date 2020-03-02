package UI;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;


public class UICard extends ImageView {
    Image backImage, frontImage;

    boolean showingFace = false;
    boolean animating = false;
    boolean scaled = false;

    public UICard(String id) {
        super(UIMisc.createImage(GameConstants.cardBackFilename));
        this.setFitHeight(this.getImage().getHeight() * GameConstants.cardScale);
        this.setFitWidth(this.getImage().getWidth() * GameConstants.cardScale);
        this.setId(id);

        backImage = this.getImage();
        frontImage = null;
    }

    public void flip(EventHandler<ActionEvent> onFinish) {
        Timeline finishAnim = getFinishAnimation(onFinish);
        if (animating)
            return;

        animating = true;
        if (frontImage == null) {
            animateFlip(e -> finishAnim.play());
            return;
        }

        animateFlip(e -> finishAnim.play());
        this.setImage(showingFace ? backImage : frontImage);
        showingFace = !showingFace;
    }

    public void setFrontImage(String filename) {
        frontImage = UIMisc.createImage("cards/" + filename);
        if (showingFace)
            this.setImage(frontImage);
    }

    public void makeSmaller() {
        Timeline finishAnim = getFinishAnimation(e -> scaled = true);
        if (animating || scaled)
            return;

        animating = true;
        ScaleTransition transition = new ScaleTransition(Duration.millis(GameConstants.cardScaleAnimationTime), this);
        transition.setByX(-0.5f);
        transition.setByY(-0.5f);
        transition.setOnFinished(e -> finishAnim.play());
        transition.play();
    }

    public void restoreSize() {
        Timeline finishAnim = getFinishAnimation(e -> scaled = false);
        if (animating || !scaled)
            return;

        animating = true;
        ScaleTransition transition = new ScaleTransition(Duration.millis(GameConstants.cardScaleAnimationTime), this);
        transition.setByX(0.5f);
        transition.setByY(0.5f);
        transition.setOnFinished(e -> finishAnim.play());
        transition.play();
    }

    private void animateFlip(EventHandler<ActionEvent> onFinish) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(GameConstants.cardFlipAnimationTime), this);
        transition.setByX(-0.9f);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        transition.setOnFinished(onFinish);
        transition.play();
    }

    private Timeline getFinishAnimation(EventHandler<ActionEvent> onFinish) {
        Timeline finishAnim = new Timeline(new KeyFrame(Duration.millis(GameConstants.cardFlipAnimationTime), event -> animating = false));
        finishAnim.setOnFinished(onFinish);
        return finishAnim;
    }


    public static HBox createCardBox(ArrayList<UICard> uiPlayerCards, String idPrefix) {
        HBox cardBox = new HBox();

        for(byte i=0; i<3; i++) {
            uiPlayerCards.add(new UICard(idPrefix + i));
            cardBox.getChildren().add( uiPlayerCards.get(i) );
        }

        cardBox.getStyleClass().add("cardBox");
        return cardBox;
    }
}
