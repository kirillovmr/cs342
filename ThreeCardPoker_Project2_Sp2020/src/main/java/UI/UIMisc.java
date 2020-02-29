package UI;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UIMisc {

    // Returns an Image for given filename
    public static Image createImage(String filename) {
        try {
            return new Image(new FileInputStream("src/res/" + filename));
        }
        catch (FileNotFoundException e) {
            System.err.println(">< createImage: File " + filename + " was not found");
            return null;
        }
    }
}
