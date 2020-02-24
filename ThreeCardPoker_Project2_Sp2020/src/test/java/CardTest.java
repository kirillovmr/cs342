import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    Card c;

    @BeforeEach
    void init() {
        c = new Card('S', 13);
    }

    @Test
    @DisplayName("Initialization")
    void test1() {
        assertEquals('S', c.suit, "Suit is wrong");
        assertEquals(13, c.value, "Value is wrong");
    }

    @Test
    @DisplayName("Properly displays in the output")
    void t2() {
        assertEquals("S:13", c.toString(), "Wrong string representation");
    }
}
