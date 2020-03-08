import java.util.ArrayList;

/*
    This class represents the logic in the game.
 */

public class ThreeCardLogic {

    // Returns the value of Straight Flush
    protected static boolean straightFlush(ArrayList<Card> hand)
    {
        return (straight(hand) && hand.get(0).suit == hand.get(1).suit && hand.get(1).suit == hand.get(2).suit);
    }

    // Returns the value of 3 of a Kind
    protected static boolean threeOfAKind(ArrayList<Card> hand)
    {
        int value = hand.get(0).value;

        for (int i=1; i<hand.size(); i++) {
            if (hand.get(i).value != value) {
                return false;
            }
        }

        return true;
    }

    // Returns the value of Straight
    protected static boolean straight(ArrayList<Card> hand)
    {
        return (
                ( hand.get(0).value == hand.get(1).value + 1 && hand.get(0).value == hand.get(2).value + 2 ) ||
                ( hand.get(0).value == hand.get(1).value - 1 && hand.get(0).value == hand.get(2).value - 2 ) ||
                ( hand.get(0).value == 3 && hand.get(1).value == 2 && hand.get(2).value == 14 ) ||
                ( hand.get(0).value == 14 && hand.get(1).value == 2 && hand.get(2).value == 3 )
        );
    }

    // Returns the value of Flush
    protected static boolean flush(ArrayList<Card> hand) {
        char suit = hand.get(0).suit;

        for (int i=1; i<hand.size(); i++) {
            if (hand.get(i).suit != suit) {
                return false;
            }
        }

        return true;
    }

    // Returns the value of Pair
    protected static boolean pair(ArrayList<Card> hand) {

        // Go through all cards for each card
        for (int i=0; i<hand.size(); i++) {
            for (int j = 0; j < hand.size(); j++) {
                // Skip is comparing with the same card
                if (i == j) {
                    continue;
                }

                // If found a pair
                if (hand.get(i).value == hand.get(j).value) {
                    return true;
                }
            }
        }

        return false;
    }

    // Returns the value of High Card
    protected static int highCard(ArrayList<Card> hand) {
        int maxValue = hand.get(0).value;

        for (int i=1; i<hand.size(); i++) {
            if (hand.get(i).value > maxValue) {
                maxValue = hand.get(i).value;
            }
        }

        return  maxValue;
    }

    // Return an integer value representing the value of the hand passed in.
    public static int evalHand(ArrayList<Card> hand) {
        if (straightFlush(hand))
            return 1;
        if (threeOfAKind(hand))
            return 2;
        if (straight(hand))
            return 3;
        if (flush(hand))
            return 4;
        if (pair(hand))
            return 5;
        return 0;
    }

    // Return the amount won for the PairPlus bet.
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        int handValue = evalHand(hand);

        switch (handValue) {
            case 1: return 40 * bet;
            case 2: return 30 * bet;
            case 3: return 6 * bet;
            case 4: return 3 * bet;
            case 5: return bet;
            default: return 0;
        }
    }

    public static int evalHandToPairPlusMultiplier(int value) {
        switch (value) {
            case 1: return 40;
            case 2: return 30;
            case 3: return 6;
            case 4: return 3;
            case 5: return 1;
            default: return -1;
        }
    }

    public static String evalHandToPairPlusName(int value) {
        switch (value) {
            case 1: return "STRAIGHT FLUSH";
            case 2: return "THREE OF A KIND";
            case 3: return "STRAIGHT";
            case 4: return "FLUSH";
            case 5: return "PAIR";
            default: return "";
        }
    }

    public static String cardToName(int value) {
        switch (value) {
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            case 14: return "Ace";
            default: return "" + value;
        }
    }

    // Compare the two hands passed in and return an integer based on which hand won
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {

        int dealerHandValue = evalHand(dealer);
        int playerHandValue = evalHand(player);

        if (dealerHandValue == 0 && playerHandValue != 0)
            return 2;
        else if (playerHandValue == 0 && dealerHandValue != 0)
            return 1;
        else if (dealerHandValue < playerHandValue)
            return 1;
        else if (playerHandValue < dealerHandValue)
            return 2;
        else
            return 0;

    }
}
