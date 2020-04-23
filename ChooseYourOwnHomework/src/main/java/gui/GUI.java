package gui;

import factory.*;

/*
    Static GUI class which creates a factory for the specified OS platform
*/
public class GUI {

    private GUI() {}

    public enum OS {
        Windows, MacOS, Linux
    };

    public static GUIFactory createFactory(OS os) {

        switch (os) {
            case Windows:
                return new WindowsFactory();
            case MacOS:
                return new MacFactory();
            case Linux:
                return new LinuxFactory();
            default:
                throw new IllegalArgumentException("Can't yet handle " + os);
        }
    }
}
