package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class CoffeeDecoratorTest {

    Coffee coffee;

    @BeforeEach
    void setup() {
        coffee = new BasicCoffee();
    }

    @Test
    void test1() {
        assertEquals(3.99, coffee.makeCoffee());
    }

    @Test
    void test2() {
        coffee = new Cream(coffee);
        assertEquals(4.49, Math.round(coffee.makeCoffee() * 100.0) / 100.0);
    }

    @Test
    void test3() {
        coffee = new Milk(coffee);
        assertEquals(4.39, Math.round(coffee.makeCoffee() * 100.0) / 100.0);
    }

    @Test
    void test4() {
        coffee = new Cream(new Milk(coffee));
        assertEquals(4.89, Math.round(coffee.makeCoffee() * 100.0) / 100.0);
    }

    @Test
    void test5() {
        coffee = new ExtraShot(new Sugar(new Ice(new Honey(new Cream(new Milk(coffee))))));
        assertEquals(7.19, Math.round(coffee.makeCoffee() * 100.0) / 100.0);
    }

    @Test
    void test6() {
        assertEquals(" + ice: $0.20", Ice.getText());
    }

    @Test
    void test7() {
        coffee = new Cream(new Honey(new Milk(new Sugar(new ExtraShot(new Ice(coffee))))));
        assertEquals(7.19, Math.round(coffee.makeCoffee() * 100.0) / 100.0);
    }

    @Test
    void test8() {
        assertEquals(" + honey: $0.40", Honey.getText());
    }

    @Test
    void test9() {
        coffee = new Ice(new Ice(new Ice(new Ice(new Ice(new Ice(coffee))))));
        assertEquals(5.19, Math.round(coffee.makeCoffee() * 100.0) / 100.0);
    }

    @Test
    void test10() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outContent));

        coffee = new Ice(coffee);
        coffee.makeCoffee();

        assertEquals("Black Coffee: $3.99\n" +
                " + ice: $0.20\n", outContent.toString());

        System.setOut(originalOut);
    }
}
