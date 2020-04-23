package TextField;

/*
    Concrete class implementation for the concrete platform
*/
public class WindowsTextField extends TextField {

    public WindowsTextField(String placeholder) {
        super(placeholder);
    }

    @Override
    public void render() {
        System.out.println("I'm a Windows TextField with placeholder \"" + this.placeholder + "\"");
    }
}
