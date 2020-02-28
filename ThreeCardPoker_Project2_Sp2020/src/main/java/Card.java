/*
    This class represents a card in a deck of 52 playing cards. The data member suit will be
    a capitalized character representing the suit of the card(clubs, diamonds, spades, or
    hearts) ‘C’, ‘D’, ’S’, ‘H’
    The data member value will be an integer value between 2 - 14, with the value of an
    ace being 14, king 13, queen 12, jack 11, ten 10…..and so on.
*/

public class Card {
    protected char suit;
    protected int value;

    Card(char suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public char getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String toResourceName() {
        return "" + suit + value + ".png";
    }

    public String toString() {
        return "" + suit + ":" + value;
    }
}