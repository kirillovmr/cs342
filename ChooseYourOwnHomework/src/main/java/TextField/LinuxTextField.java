package TextField;

/*
    Concrete class implementation for the concrete platform
*/
public class LinuxTextField extends TextField {

    public LinuxTextField(String placeholder) {
        super(placeholder);
    }

    @Override
    public void render() {
        System.out.println("I'm a Linux TextField with placeholder \"" + this.placeholder + "\"");
    }
}
