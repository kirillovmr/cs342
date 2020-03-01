package UI;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UIMisc {

    public static HBox spacer(int padding) {
        HBox spacer = new HBox();
        spacer.setPadding(new Insets(padding, 0, 0, 0));
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
}
