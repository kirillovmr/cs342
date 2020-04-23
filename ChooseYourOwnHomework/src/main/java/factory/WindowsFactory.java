package factory;

import TextField.TextField;
import TextField.WindowsTextField;
import button.Button;
import button.WindowsButton;

/*
    Concrete factory class to create GUI for specific platform
*/
public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton(String text) {
        return new WindowsButton(text);
    }

    @Override
    public TextField createTextField(String placeholder) {
        return new WindowsTextField(placeholder);
    }
}
