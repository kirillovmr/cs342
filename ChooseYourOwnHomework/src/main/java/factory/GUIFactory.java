package factory;

import TextField.TextField;
import button.Button;

/*
    Interface to create all GUI components
*/
public interface GUIFactory {
    Button createButton(String text);
    TextField createTextField(String placeholder);
}
