import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class ThreeCardLogicTest {

    @Test
    @DisplayName("Rules for straightFlush : True")
    void straightFlush() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('S',13));
        hand.add(new Card('S',12));
        assertEquals(39, ThreeCardLogic.straightFlush(hand), "Straight Flush: False Negative");

        hand.clear();

        hand.add(new Card('D',9));
        hand.add(new Card('D',8));
        hand.add(new Card('D',7));
        assertEquals(24, ThreeCardLogic.straightFlush(hand), "Straight Flush: False Negative");
    }

    @Test
    @DisplayName("Rules for straightFlush : False")
    void notStraightFlush() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',12));
        assertEquals(0, ThreeCardLogic.straightFlush(hand), "Straight Flush: False Positive");

        hand.clear();

        hand.add(new Card('S',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertEquals(0, ThreeCardLogic.straightFlush(hand), "Straight Flush: False Positive");
    }

    @Test
    @DisplayName("Rules for threeOfAKind : True")
    void threeOfKind() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S', 10));
        hand.add(new Card('C', 10));
        hand.add(new Card('D', 10));
        assertEquals(30, ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Negative");

        hand.clear();

        hand.add(new Card('H', 3));
        hand.add(new Card('S', 3));
        hand.add(new Card('D', 3));
        assertEquals(9, ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Negative");
    }

    @Test
    @DisplayName("Rules for threeOfAKind : False")
    void notThreeOfKind() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S', 10));
        hand.add(new Card('C', 11));
        hand.add(new Card('D', 10));
        assertEquals(0, ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Positive");

        hand.clear();

        hand.add(new Card('H', 4));
        hand.add(new Card('S', 4));
        hand.add(new Card('D', 3));
        assertEquals(0, ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Positive");
    }

    @Test
    @DisplayName("Rules for straight : True")
    void straight() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('H',12));
        assertEquals(39, ThreeCardLogic.straight(hand), "Straight: False Negative");

        hand.clear();

        hand.add(new Card('D',9));
        hand.add(new Card('H',8));
        hand.add(new Card('D',7));
        assertEquals(24, ThreeCardLogic.straight(hand), "Straight: False Negative");
    }

    @Test
    @DisplayName("Rules for straight : False")
    void notStraight() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',11));
        assertEquals(0, ThreeCardLogic.straight(hand), "Straight: False Positive");

        hand.clear();

        hand.add(new Card('S',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertEquals(0, ThreeCardLogic.straight(hand), "Straight: False Positive");
    }

    @Test
    @DisplayName("Rules for flush : True")
    void flush() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('S',7));
        hand.add(new Card('S',3));
        assertEquals(24, ThreeCardLogic.flush(hand), "Flush: False Negative");

        hand.clear();

        hand.add(new Card('D',2));
        hand.add(new Card('D',8));
        hand.add(new Card('D',7));
        assertEquals(17, ThreeCardLogic.flush(hand), "Flush: False Negative");
    }

    @Test
    @DisplayName("Rules for flush : False")
    void notFlush() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',11));
        assertEquals(0, ThreeCardLogic.flush(hand), "Flush: False Positive");

        hand.clear();

        hand.add(new Card('D',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertEquals(0, ThreeCardLogic.flush(hand), "Flush: False Positive");
    }

    @Test
    @DisplayName("Rules for pair : True")
    void pair() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',7));
        hand.add(new Card('D',14));
        assertEquals(28, ThreeCardLogic.pair(hand), "Pair: False Negative");

        hand.clear();

        hand.add(new Card('D',2));
        hand.add(new Card('D',8));
        hand.add(new Card('H',2));
        assertEquals(4, ThreeCardLogic.pair(hand), "Pair: False Negative");
    }

    @Test
    @DisplayName("Rules for pair : False")
    void notPair() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',11));
        assertEquals(0, ThreeCardLogic.pair(hand), "Pair: False Positive");

        hand.clear();

        hand.add(new Card('D',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertEquals(0, ThreeCardLogic.pair(hand), "Pair: False Positive");
    }

    @Test
    @DisplayName("Rules for highCard")
    void high() {
        ArrayList<Card> hand = new ArrayList<Card>();

        hand.add(new Card('S',11));
        hand.add(new Card('D',7));
        hand.add(new Card('D',14));
        assertEquals(14, ThreeCardLogic.highCard(hand), "High Card: False Negative");

        hand.clear();

        hand.add(new Card('D',2));
        hand.add(new Card('D',8));
        hand.add(new Card('H',3));
        assertEquals(8, ThreeCardLogic.highCard(hand), "High Card: False Negative");
    }

    @Test
    @DisplayName("Check for Int Wrapper")
    void intWrapper() {
        ThreeCardLogic.IntWrapper wrappedInt = new ThreeCardLogic.IntWrapper(7);

        Function<ThreeCardLogic.IntWrapper, Void> increment = (ThreeCardLogic.IntWrapper wrapped) -> {
            wrapped.value += 1;
            return null;
        };

        increment.apply(wrappedInt);
        assertEquals(8, wrappedInt.value, "IntWrapper does not work");
    }

    @Test
    @DisplayName("Checking evalHand")
    void evalHand() {
        ArrayList<Card> hand = new ArrayList<Card>();

        ThreeCardLogic.IntWrapper wrappedInt = new ThreeCardLogic.IntWrapper(0);

        // High Card
        hand.add(new Card('S',11));
        hand.add(new Card('D',7));
        hand.add(new Card('D',14));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Eval Hand returned wrong value");

        ThreeCardLogic.evalHand(hand, wrappedInt);
        assertEquals(14, wrappedInt.value, "Eval Hand returned wrong deck value");

        hand.clear();

        // Straight
        hand.add(new Card('S',11));
        hand.add(new Card('D',10));
        hand.add(new Card('D',9));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Eval Hand returned wrong value");

        ThreeCardLogic.evalHand(hand, wrappedInt);
        assertEquals(30, wrappedInt.value, "Eval Hand returned wrong deck value");
    }

    @Test
    @DisplayName("Comparing Hands")
    void compareHands() {
        ArrayList<Card> hand = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();

        // High Card
        hand.add(new Card('S',11));
        hand.add(new Card('D',7));
        hand.add(new Card('D',14));

        // Straight
        hand2.add(new Card('S',11));
        hand2.add(new Card('D',10));
        hand2.add(new Card('D',9));

        assertEquals(2, ThreeCardLogic.compareHands(hand, hand2), "compareHands() returns wrong value when player expected to win");
        assertEquals(1, ThreeCardLogic.compareHands(hand2, hand), "compareHands() returns wrong value when dealer expected to win");
        assertEquals(0, ThreeCardLogic.compareHands(hand, hand), "compareHands() returns wrong value when there is no winner");
    }

    @Test
    @DisplayName("Testing evalPPWinnings method")
    void evalPPWinnings() {
        ArrayList<Card> hand = new ArrayList<Card>();

        // High Card
        hand.add(new Card('S',11));
        hand.add(new Card('D',7));
        hand.add(new Card('D',14));
        assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, 10), "evalPPWinnings() returns wrong value when you lose");

        hand.clear();

        // Straight
        hand.add(new Card('S',11));
        hand.add(new Card('D',10));
        hand.add(new Card('D',9));
        assertEquals(60, ThreeCardLogic.evalPPWinnings(hand, 10), "evalPPWinnings() returns wrong value when you win straight");
    }
}
