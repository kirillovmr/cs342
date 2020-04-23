package factory;

import TextField.TextField;
import TextField.LinuxTextField;
import button.Button;
import button.LinuxButton;

/*
    Concrete factory class to create GUI for specific platform
*/
public class LinuxFactory implements GUIFactory {

    @Override
    public Button createButton(String text) {
        return new LinuxButton(text);
    }

    @Override
    public TextField createTextField(String placeholder) {
        return new LinuxTextField(placeholder);
    }
}
