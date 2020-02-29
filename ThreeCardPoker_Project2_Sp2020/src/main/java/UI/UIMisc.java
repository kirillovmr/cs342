package UI;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UIMisc {

    public static Image createImage(String filename) {
        try {
            return new Image(new FileInputStream("src/res/" + filename));
        }
        catch (FileNotFoundException e) {
            System.err.println(">< createImage: File " + filename + " was not found");
            return null;
        }
    }

    public static void animateFlip(ImageView image, EventHandler<ActionEvent> onFinished) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(GameConstants.cardFlipAnimationTime), image);
        transition.setByX(-0.9f);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        transition.setOnFinished(onFinished);
        transition.play();
    }

    public static void openCard(ImageView card, String filename, EventHandler<ActionEvent> onFinished) {
        UIMisc.animateFlip(card, onFinished);
        try {
            card.setImage(new Image(new FileInputStream("src/res/cards/" + filename)));
        } catch (FileNotFoundException e) {
            System.err.println(">< openCard: File " + filename + " was not found");
        }
    }

    public static void closeCard(ImageView card, EventHandler<ActionEvent> onFinished) {
        UIMisc.animateFlip(card, onFinished);
        try {
            card.setImage(new Image(new FileInputStream("src/res/card_back.png")));
        } catch (FileNotFoundException e) {
            System.err.println(">< openCard: File card_back.png was not found");
        }
    }

    public static void makeCardSmaller(ImageView card) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(GameConstants.cardScaleAnimationTime), card);
        transition.setByX(-0.5f);
        transition.setByY(-0.5f);
        transition.play();
    }

    public static void restoreCardSize(ImageView card) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(GameConstants.cardScaleAnimationTime), card);
        transition.setByX(0.5f);
        transition.setByY(0.5f);
        transition.play();
    }
}
