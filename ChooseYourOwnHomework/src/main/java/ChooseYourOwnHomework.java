import TextField.TextField;
import button.Button;
import factory.GUIFactory;
import gui.GUI;
import gui.GUI.OS;

/*
    Abstract Factory Design Pattern
    Implemented on a GUI application for multiple platforms
*/

public class ChooseYourOwnHomework {

	public static void main(String[] args) {
		System.out.println("Welcome to the Choose Your Own Design Pattern HW!");

		// Selecting the factory type
		GUIFactory factory = GUI.createFactory(OS.Windows);

		// Creating and rendering GUI elements based on the provided platform
		Button button = factory.createButton("Hello");
		button.render();

		TextField textField = factory.createTextField("dummy text");
		textField.render();
	}
}
