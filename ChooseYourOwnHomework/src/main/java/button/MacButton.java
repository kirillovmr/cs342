package button;

/*
    Concrete class implementation for the concrete platform
*/
public class MacButton extends Button {

    public MacButton(String text) {
        super(text);
    }

    @Override
    public void render() {
        System.out.println("I'm a MacOS button with title \"" + this.text + "\"");
    }
}
