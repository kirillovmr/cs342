import java.util.ArrayList;

/*
    This class represents the logic in the game.
 */

public class ThreeCardLogic {

    // Wrapper to be able to pass value "by reference"
    protected static class IntWrapper {
        public int value;

        IntWrapper(int value) {
            this.value = value;
        }
    }

    // Returns the value of Straight Flush
    protected static int straightFlush(ArrayList<Card> hand) {
        char suit = hand.get(0).suit;

        for (int i=1; i<hand.size(); i++) {
            // Checking for suit
            if (hand.get(i).suit != suit)
                return 0;

            // Checking for next card to be exactly 1 value less
            if (hand.get(i).value != hand.get(i-1).value - 1)
                return 0;

        }
        return hand.get(0).value * 3 - 3;
    }

    // Returns the value of 3 of a Kind
    protected static int threeOfAKind(ArrayList<Card> hand) {
        int value = hand.get(0).value;

        for (int i=1; i<hand.size(); i++)
            if (hand.get(i).value != value)
                return 0;

        return value * hand.size();
    }

    // Returns the value of Straight
    protected static int straight(ArrayList<Card> hand) {
        for (int i=1; i<hand.size(); i++)
            // Checking for next card to be exactly 1 value less
            if (hand.get(i).value != hand.get(i - 1).value - 1)
                return 0;

        return hand.get(0).value * 3 - 3;
    }

    // Returns the value of Flush
    protected static int flush(ArrayList<Card> hand) {
        char suit = hand.get(0).suit;
        int totalValue = hand.get(0).value;

        for (int i=1; i<hand.size(); i++) {
            // Checking for suit
            if (hand.get(i).suit != suit)
                return 0;

            totalValue += hand.get(i).value;
        }

        return totalValue;
    }

    // Returns the value of Pair
    protected static int pair(ArrayList<Card> hand) {
        int totalValue = 0;

        // Go through all cards for each card
        for (int i=0; i<hand.size(); i++) {
            for (int j = 0; j < hand.size(); j++) {
                // Skip is comparing with the same card
                if (i == j)
                    continue;

                // If found a pair
                if (hand.get(i).value == hand.get(j).value) {
                    int newTotalValue = 2 * hand.get(i).value;

                    // If new pair is better then the previous one
                    totalValue = Math.max(newTotalValue, totalValue);
                }
            }
        }

        return totalValue;
    }

    // Returns the value of High Card
    protected static int highCard(ArrayList<Card> hand) {
        int maxValue = hand.get(0).value;

        for (int i=1; i<hand.size(); i++)
            if (hand.get(i).value > maxValue)
                maxValue = hand.get(i).value;

        return  maxValue;
    }

    // Return an integer value representing the value of the hand passed in.
    public static int _evalHand(ArrayList<Card> hand, IntWrapper wrappedInt) {
        wrappedInt.value = straightFlush(hand);
        if (wrappedInt.value > 0)
            return 1;

        wrappedInt.value = threeOfAKind(hand);
        if (wrappedInt.value > 0)
            return 2;

        wrappedInt.value = straight(hand);
        if (wrappedInt.value > 0)
            return 3;

        wrappedInt.value = flush(hand);
        if (wrappedInt.value > 0)
            return 4;

        wrappedInt.value = pair(hand);
        if (wrappedInt.value > 0)
            return 5;

        wrappedInt.value = highCard(hand);
        return 0;
    }

    // Return an integer value representing the value of the hand passed in.
    public static int evalHand(ArrayList<Card> hand) {
        IntWrapper wrappedInt = new IntWrapper(0);
        return _evalHand(hand, wrappedInt);
    }

    // Besides what expected, returns the value of deck
    public static int evalHand(ArrayList<Card> hand, IntWrapper wrappedInt) {
        return _evalHand(hand, wrappedInt);
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

    // Compare the two hands passed in and return an integer based on which hand won
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {

        IntWrapper dealerDeckValue = new IntWrapper(0);
        int dealerHandValue = evalHand(dealer, dealerDeckValue);

        IntWrapper playerDeckValue = new IntWrapper(0);
        int playerHandValue = evalHand(player, playerDeckValue);

        if (dealerHandValue > playerHandValue)
            return 1;
        else if (playerHandValue > dealerHandValue)
            return 2;
        else
            return 0;

        // And only here I realized that in our program we dont really care what happens
        //      when players hand values are same.
        //   But I was going to calculate the winner by the deck value if hand values are same
        // 😩 😔 😣
    }
}
