package button;

/*
    Concrete class implementation for the concrete platform
*/
public class WindowsButton extends Button {

    public WindowsButton(String text) {
        super(text);
    }

    @Override
    public void render() {
        System.out.println("I'm a Windows button with title \"" + this.text + "\"");
    }
}
