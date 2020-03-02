import java.util.ArrayList;
import java.util.Collections;

/*
    This class represents a 52 card, standard deck, of playing cards. The constructor will
    create a new deck of 52 cards that have been sorted in random order. The second
    method will clear all the cards and create a brand new deck of 52 cards sorted in
    random order.
 */

public class Deck extends ArrayList<Card> {

    private char[] suits = {'C', 'D', 'S', 'H'};

    Deck() {
        newDeck();
    }

    // Returns the top card from deck
    Card draw() {
        if (size() == 0)
            return null;

        Card card = get(0);
        remove(0);
        return card;
    }

    // Create a new deck of 52 cards that have been sorted in random order.
    void newDeck() {
        // Clear the list
        this.clear();

        // Create card instances
        for (int value=2; value<15; value++) {
            for (int suitIdx=0; suitIdx<4; suitIdx++) {
                this.add(new Card(suits[suitIdx], value));
            }
        }

        // Shuffle the deck
        Collections.shuffle(this);
    }
}
