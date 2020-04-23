package TextField;

/*
    Concrete class implementation for the concrete platform
*/
public class MacTextField extends TextField {

    public MacTextField(String placeholder) {
        super(placeholder);
    }

    @Override
    public void render() {
        System.out.println("I'm a MacOS TextField with placeholder \"" + this.placeholder + "\"");
    }
}
