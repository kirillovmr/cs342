package UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.util.Duration;


public class UITextField extends TextField {

    private int minValue = 0;
    private int maxValue = GameConstants.maxBet;
    private int currentValue = 0;

    Timeline finishAnim;
    private boolean animatingError = false;
    EventHandler<ActionEvent> onShowErrorFinish;

    public UITextField(String value) {
        super(value);

        this.getStyleClass().addAll("wagerInput", "shadow");

        onShowErrorFinish = e -> {
            this.getStyleClass().remove("inputError");
            animatingError = false;
        };
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setOnSuccessChange(MyHandler onSuccessHandler, MyHandler onWarning) {
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            int parsedInt = -1;
            try {
                parsedInt = Integer.parseInt(newValue);
                if (parsedInt < minValue || parsedInt > maxValue) {
                    highlightError();
                    onWarning.run(0);
                    System.out.println("Bet size is restricted to (" + minValue + "," + maxValue + ")");
                    this.setText(oldValue);
                }
            }
            catch (NumberFormatException nfe) {
                if (newValue.length() == 0)
                    parsedInt = 0;
                else
                    this.setText(oldValue);
            }
            finally {
                if (parsedInt >= 0 && parsedInt <= maxValue) {
                    currentValue = parsedInt;
                    onSuccessHandler.run(parsedInt);
                }
            }
        });
    }

    private void highlightError() {
        if (animatingError) {
            finishAnim.stop();
            finishAnim = new Timeline(new KeyFrame(Duration.millis(GameConstants.showWarningTime), onShowErrorFinish));
            finishAnim.play();
            return;
        }

        animatingError = true;
        this.getStyleClass().add("inputError");
        finishAnim = new Timeline(new KeyFrame(Duration.millis(GameConstants.showWarningTime), onShowErrorFinish));
        finishAnim.play();
    }

}
