package UI;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class UISettings extends HBox {

    private Stage stage;
    private Popup popup;

    public Button settingsBtn;
    public Button freshStartBtn;
    public Button newLookBtn;
    public Button exitBtn;

    public UISettings(Stage stage) {
        super();

        this.stage = stage;
        this.getStyleClass().addAll("settingsRow");
        this.setPadding(new Insets(10,0,0,0));

        settingsBtn = new Button("Settings");
        settingsBtn.getStyleClass().addAll("settingsBtn", "settingsMainBtn", "shadow");
        this.getChildren().add(settingsBtn);

        createPopup();
    }

    public void hidePopup() {
        popup.hide();
    }

    private void createPopup() {
        popup = new Popup();
        popup.setAutoHide(true);

        freshStartBtn = createButton("Fresh Start");
        newLookBtn = createButton("New look");
        exitBtn = createButton("Exit");

        VBox popupBox = new VBox(freshStartBtn, newLookBtn, exitBtn);
        popupBox.getStyleClass().addAll("popupBox");

        popup.getContent().add(popupBox);

        settingsBtn.setOnAction(e -> {
            if (!popup.isShowing()) {
                Point2D buttonPoint = settingsBtn.localToScene(0, 0);
                popup.setX(stage.getX() + buttonPoint.getX());
                popup.setY(stage.getY() + buttonPoint.getY() + settingsBtn.getHeight() + 35);
                popup.show(stage);
            }
        });
    }

    private Button createButton(String text) {
        Button b = new Button(text);
        b.getStyleClass().addAll("settingsBtn");
        return b;
    }
}
