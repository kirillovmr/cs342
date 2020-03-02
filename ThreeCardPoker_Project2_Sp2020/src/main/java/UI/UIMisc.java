package UI;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UIMisc {

    public static HBox spacer(int padding) {
        HBox spacer = new HBox();
        spacer.setPadding(new Insets(padding, 0, 0, 0));
        return spacer;
    }

    public static HBox leftSpacer(int padding) {
        HBox spacer = new HBox();
        spacer.setPadding(new Insets(0, 0, 0, padding));
        return spacer;
    }

    // Returns Image for given filename
    public static Image createImage(String filename) {
        try {
            return new Image(new FileInputStream("src/res/" + filename));
        }
        catch (FileNotFoundException e) {
            System.err.println(">< createImage: File " + filename + " was not found");
            return null;
        }
    }

    // Returns ImageView for given filename
    public static ImageView createImageView(String filename, double scale) {
        ImageView imageView = new ImageView(createImage(filename));
        imageView.setFitHeight(imageView.getImage().getHeight() * scale);
        imageView.setFitWidth(imageView.getImage().getWidth() * scale);
        return imageView;
    }

    public static void shiftChips(StackPane chipsNode, int byX, int byY, int time, EventHandler<ActionEvent> onFinish) {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(time));
        translate.setNode(chipsNode);
        translate.setByX(byX);
        translate.setByY(byY);
        translate.setOnFinished(onFinish);
        translate.play();
    }

    public static void duplicateChips(StackPane chipStack, int times, String chipFilename) {
        int currentChipsNum = chipStack.getChildren().size();
        for(int i=0; i<currentChipsNum * times; i++) {
            ImageView wagerChip = UIMisc.createImageView(chipFilename, 0.07);
            wagerChip.getStyleClass().addAll("wagerChip", "shadow");
            StackPane chipWrap = new StackPane(wagerChip);
            chipWrap.setPadding(new Insets(0, 0, (currentChipsNum+i) * 5, 0));
            chipStack.getChildren().add(chipWrap);
        }
    }
}
