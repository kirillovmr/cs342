package factory;

import TextField.TextField;
import TextField.MacTextField;
import button.Button;
import button.MacButton;

/*
    Concrete factory class to create GUI for specific platform
*/
public class MacFactory implements GUIFactory {

    @Override
    public Button createButton(String text) {
        return new MacButton(text);
    }

    @Override
    public TextField createTextField(String placeholder) {
        return new MacTextField(placeholder);
    }
}
