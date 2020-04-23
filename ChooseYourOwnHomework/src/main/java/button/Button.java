package button;

import gui.Renderable;

/*
    Abstract Button class which encapsulates all the behaviour
    Concrete classes will implement the render method
*/
public abstract class Button implements Renderable {

    public String text;

    protected Button(String text) {
        this.text = text;
    }

}
