import java.util.ArrayList;

/*
    This class represents the dealer in the game. The no arg constructor will initialize
    theDeck. The data member dealersHand will hold the dealers hand in each game. The
    method dealHand() will return an ArrayList<Card> of three cards removed from
    theDeck.

    Before each game starts, the Dealer class must check to see if there are more
    than 34 cards left in the deck. If not, theDeck must be reshuffled with a new set of 52
    cards in random order.
 */

public class Dealer {
    protected Deck theDeck;
    protected ArrayList<Card> dealersHand;

    Dealer() {
        theDeck = new Deck();
        dealersHand = new ArrayList<>();
    }

    public ArrayList<Card> dealHand() {

        // Creating a new deck if needed
        if (theDeck.size() <= 34) {
            theDeck.newDeck();
        }

        // Clearing dealers hand
        dealersHand.clear();

        // Draw 3 cards
        for(int i=0; i<3; i++) {
            dealersHand.add(theDeck.get(0));
            theDeck.remove(0);
        }

        return new ArrayList<>(dealersHand);
    }

    public ArrayList<Card> getHand() {
        return dealersHand;
    }

    public void setHand(ArrayList<Card> dealersHand) {
        this.dealersHand = dealersHand;
    }

}
