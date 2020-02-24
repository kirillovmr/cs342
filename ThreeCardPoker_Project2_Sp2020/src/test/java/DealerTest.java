import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DealerTest {

    Dealer d;

    @BeforeEach
    void init() {
        d = new Dealer();
    }

    @Test
    @DisplayName("Initialization of all members")
    void t1() {
        assertEquals(52, d.theDeck.size(), "Deck was not initialized with 52 cards");
        assertEquals(0, d.dealersHand.size(), "Hand is not 0 at the beginning");
    }

    @Test
    @DisplayName("Draws 3 cards")
    void t2() {
        ArrayList<Card> cards = d.dealHand();
        assertNotEquals(null, cards, "Dealer dealHand() returned null instead of 3 cards");
        assertEquals(3, cards.size(), "Dealer returned not 3 cards");

        for(int i=0; i<cards.size(); i++) {
            assertEquals(cards.get(i), d.dealersHand.get(i), "Drawed cards are not same as dealers hand");
        }
    }

    @Test
    @DisplayName("Draw changes the hand")
    void t3() {
        ArrayList<Card> cards = d.dealHand();
        d.dealHand();

        for(int i=0; i<cards.size(); i++) {
            assertNotEquals(cards.get(i), d.dealersHand.get(i), "Draw for the second time doesn't changes the hand");
        }
    }

    @Test
    @DisplayName("New deck is creating after threshold")
    void t4() {
        for (int i=0; i<6; i++)
            d.dealHand();

        assertEquals(34, d.theDeck.size(), "Deck is not 36 in size after 6 draws");

        d.dealHand();

        assertEquals(49, d.theDeck.size(), "Deck was not reinitialized");
    }
}
