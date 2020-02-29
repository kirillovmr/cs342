package UI;

import javafx.scene.control.TextField;

public class UITextField extends TextField {

    private int minValue = 0;
    private int maxValue = GameConstants.maxBet;

    public UITextField(String value) {
        super(value);
    }

    public void setOnSuccessChange(MyHandler onSuccessHandler) {
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            int parsedInt = -1;
            try {
                parsedInt = Integer.parseInt(newValue);
                if (parsedInt < minValue || parsedInt > maxValue) {
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
                    onSuccessHandler.run(parsedInt);
                }
            }
        });
    }

}
