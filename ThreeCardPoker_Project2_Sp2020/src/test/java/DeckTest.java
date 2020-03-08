import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    Deck d;

    @BeforeEach
    void init() {
        d = new Deck();
    }

    @Test
    @DisplayName("Initialization")
    void test1() {
        assertEquals(52, d.size(), "Initial size of deck is wrong");
    }

    @Test
    @DisplayName("Draw card")
    void test2() {
        assertEquals("Card", d.draw().getClass().getName(), "Draw returned not a card");
        assertEquals(51, d.size(), "Size did not change after drawing");
    }

    @Test
    @DisplayName("Draw all cards")
    void test3() {
        for (int i=0; i<52; i++)
            d.draw();

        assertNull(d.draw(), "Returned not null when deck is supposed to be empty");
    }

    @Test
    @DisplayName("newDeck reinitialization")
    void test4() {
        Card[] a = { d.get(0), d.get(1), d.get(2) };
        d.newDeck();
        for (int i=0; i<a.length; i++) {
            assertFalse(d.get(0) == a[0] && d.get(1) == a[1] && d.get(2) == a[2], "Deck was not reshuffled properly");
        }
    }
}
