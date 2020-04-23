package TextField;

import gui.Renderable;

/*
    Abstract TextField class which encapsulates all the behaviour
    Concrete classes will implement the render method
*/
public abstract class TextField implements Renderable {

    public String placeholder;

    protected TextField(String placeholder) {
        this.placeholder = placeholder;
    }

}
