package button;

/*
    Concrete class implementation for the concrete platform
*/
public class LinuxButton extends Button {

    public LinuxButton(String text) {
        super(text);
    }

    @Override
    public void render() {
        System.out.println("I'm a Linux button with title \"" + this.text + "\"");
    }
}
