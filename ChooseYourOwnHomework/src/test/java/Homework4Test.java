import static org.junit.jupiter.api.Assertions.*;

import TextField.LinuxTextField;
import TextField.MacTextField;
import TextField.TextField;
import button.Button;
import button.LinuxButton;
import button.MacButton;
import button.WindowsButton;
import factory.GUIFactory;
import factory.LinuxFactory;
import factory.MacFactory;
import factory.WindowsFactory;
import gui.GUI;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class Homework4Test {

    @Test
    void test1() {
        LinuxButton btn = new LinuxButton("test");
        assertEquals("test", btn.text, "Button was not initialized with the given text");
    }

    @Test
    void test2() {
        LinuxTextField tf = new LinuxTextField("dummy");
        assertEquals("dummy", tf.placeholder, "TextField was not initialized with the given placeholder text");
    }

    @Test
    void test3() {
        MacButton btn = new MacButton("text");
        assertEquals(MacButton.class.getName(), btn.getClass().getName(), "Wrong concrete instance of the button");
    }

    @Test
    void test4() {
        Button btn = new WindowsButton("text");
        assertEquals(WindowsButton.class.getName(), btn.getClass().getName(), "Wrong abstract instance of the button");
    }

    @Test
    void test5() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        new WindowsButton("text").render();
        assertEquals("I'm a Windows button with title \"text\"\n", outContent.toString(), "Render output doesnt math the expected one");

        System.setOut(originalOut);
    }

    @Test
    void test6() {
        LinuxFactory f = new LinuxFactory();
        Button b = f.createButton("text");
        assertEquals(LinuxButton.class.getName(), b.getClass().getName(), "Factory returned wrong GUI element");
    }

    @Test
    void test7() {
        MacFactory f = new MacFactory();
        TextField b = f.createTextField("text");
        assertEquals(MacTextField.class.getName(), b.getClass().getName(), "Factory returned wrong GUI element");
    }

    @Test
    void test8() {
        WindowsFactory f = new WindowsFactory();
        Button b = f.createButton("text");
        assertEquals(WindowsButton.class.getName(), b.getClass().getName(), "Factory returned wrong GUI element");
    }

    @Test
    void test9() {
        GUIFactory f = GUI.createFactory(GUI.OS.MacOS);
        assertEquals(MacFactory.class.getName(), f.getClass().getName(), "Static createFactory returned wrong concrete factory class");
    }

    @Test
    void test10() {
        GUIFactory f = GUI.createFactory(GUI.OS.Linux);
        Button b = f.createButton("button");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        b.render();
        assertEquals("I'm a Linux button with title \"button\"\n", outContent.toString(), "Button returned by factory created for specific platform produced the wrong render output");

        System.setOut(originalOut);
    }

}
