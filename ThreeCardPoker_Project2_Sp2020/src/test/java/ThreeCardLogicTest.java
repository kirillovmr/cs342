import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ThreeCardLogicTest {

    @Test
    @DisplayName("Rules for straightFlush : True")
    void straightFlush() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('S',13));
        hand.add(new Card('S',12));
        assertTrue(ThreeCardLogic.straightFlush(hand), "Straight Flush: False Negative");

        hand.clear();

        hand.add(new Card('D',9));
        hand.add(new Card('D',8));
        hand.add(new Card('D',7));
        assertTrue(ThreeCardLogic.straightFlush(hand), "Straight Flush: False Negative");
    }

    @Test
    @DisplayName("Rules for straightFlush : False")
    void notStraightFlush() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',12));
        assertFalse(ThreeCardLogic.straightFlush(hand), "Straight Flush: False Positive");

        hand.clear();

        hand.add(new Card('S',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertFalse(ThreeCardLogic.straightFlush(hand), "Straight Flush: False Positive");
    }

    @Test
    @DisplayName("Rules for threeOfAKind : True")
    void threeOfKind() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S', 10));
        hand.add(new Card('C', 10));
        hand.add(new Card('D', 10));
        assertTrue(ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Negative");

        hand.clear();

        hand.add(new Card('H', 3));
        hand.add(new Card('S', 3));
        hand.add(new Card('D', 3));
        assertTrue(ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Negative");
    }

    @Test
    @DisplayName("Rules for threeOfAKind : False")
    void notThreeOfKind() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S', 10));
        hand.add(new Card('C', 11));
        hand.add(new Card('D', 10));
        assertFalse(ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Positive");

        hand.clear();

        hand.add(new Card('H', 4));
        hand.add(new Card('S', 4));
        hand.add(new Card('D', 3));
        assertFalse(ThreeCardLogic.threeOfAKind(hand), "3 Of A Kind: False Positive");
    }

    @Test
    @DisplayName("Rules for straight : True")
    void straight() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('H',12));
        assertTrue(ThreeCardLogic.straight(hand), "Straight: False Negative");

        hand.clear();
        hand.add(new Card('S',12));
        hand.add(new Card('D',13));
        hand.add(new Card('H',14));
        assertTrue(ThreeCardLogic.straight(hand), "Straight: False Negative");

        hand.clear();
        hand.add(new Card('S',14));
        hand.add(new Card('D',2));
        hand.add(new Card('H',3));
        assertTrue(ThreeCardLogic.straight(hand), "Straight: False Negative");

        hand.clear();
        hand.add(new Card('S',3));
        hand.add(new Card('D',2));
        hand.add(new Card('H',14));
        assertTrue(ThreeCardLogic.straight(hand), "Straight: False Negative");

        hand.clear();
        hand.add(new Card('D',9));
        hand.add(new Card('H',8));
        hand.add(new Card('D',7));
        assertTrue(ThreeCardLogic.straight(hand), "Straight: False Negative");
    }

    @Test
    @DisplayName("Rules for straight : False")
    void notStraight() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',11));
        assertFalse(ThreeCardLogic.straight(hand), "Straight: False Positive");

        hand.clear();

        hand.add(new Card('S',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertFalse(ThreeCardLogic.straight(hand), "Straight: False Positive");
    }

    @Test
    @DisplayName("Rules for flush : True")
    void flush() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('S',7));
        hand.add(new Card('S',3));
        assertTrue(ThreeCardLogic.flush(hand), "Flush: False Negative");

        hand.clear();

        hand.add(new Card('D',2));
        hand.add(new Card('D',8));
        hand.add(new Card('D',7));
        assertTrue(ThreeCardLogic.flush(hand), "Flush: False Negative");
    }

    @Test
    @DisplayName("Rules for flush : False")
    void notFlush() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',11));
        assertFalse(ThreeCardLogic.flush(hand), "Flush: False Positive");

        hand.clear();

        hand.add(new Card('D',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertFalse(ThreeCardLogic.flush(hand), "Flush: False Positive");
    }

    @Test
    @DisplayName("Rules for pair : True")
    void pair() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',7));
        hand.add(new Card('D',14));
        assertTrue(ThreeCardLogic.pair(hand), "Pair: False Negative");

        hand.clear();

        hand.add(new Card('D',2));
        hand.add(new Card('D',8));
        hand.add(new Card('H',2));
        assertTrue(ThreeCardLogic.pair(hand), "Pair: False Negative");
    }

    @Test
    @DisplayName("Rules for pair : False")
    void notPair() {
        ArrayList<Card> hand = new ArrayList<>();

        hand.add(new Card('S',14));
        hand.add(new Card('D',13));
        hand.add(new Card('S',11));
        assertFalse(ThreeCardLogic.pair(hand), "Pair: False Positive");

        hand.clear();

        hand.add(new Card('D',13));
        hand.add(new Card('S',14));
        hand.add(new Card('S',12));
        assertFalse(ThreeCardLogic.pair(hand), "Pair: False Positive");
    }

    @Test
    @DisplayName("Rules for highCard")
    void high() {
        ArrayList<Card> hand = new ArrayList<>();

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
    @DisplayName("Checking evalHand")
    void evalHand() {
        ArrayList<Card> hand = new ArrayList<>();

        // High Card
        hand.add(new Card('S',11));
        hand.add(new Card('D',7));
        hand.add(new Card('D',14));
        assertEquals(0, ThreeCardLogic.evalHand(hand), "Eval Hand returned wrong value");

        hand.clear();

        // Straight
        hand.add(new Card('S',11));
        hand.add(new Card('D',10));
        hand.add(new Card('D',9));
        assertEquals(3, ThreeCardLogic.evalHand(hand), "Eval Hand returned wrong value");
    }

    @Test
    @DisplayName("Compare hands flush - pair")
    void compareHand() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        ArrayList<Card> hand2 = new ArrayList<>();

        // Pair
        dealerHand.add(new Card('S',11));
        dealerHand.add(new Card('S',9));
        dealerHand.add(new Card('D',9));

        // Flush
        hand2.add(new Card('D',11));
        hand2.add(new Card('D',7));
        hand2.add(new Card('D',14));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, hand2), "compareHands() returns wrong value when player expected to win");
    }

    @Test
    @DisplayName("Comparing Hands")
    void compareHands() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        ArrayList<Card> playerHand = new ArrayList<>();

        // High Card
        dealerHand.add(new Card('S',11));
        dealerHand.add(new Card('D',7));
        dealerHand.add(new Card('D',14));

        // Straight
        playerHand.add(new Card('S',11));
        playerHand.add(new Card('D',10));
        playerHand.add(new Card('D',9));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "compareHands() returns wrong value when player expected to win");
        assertEquals(1, ThreeCardLogic.compareHands(playerHand, dealerHand), "compareHands() returns wrong value when dealer expected to win");
        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, dealerHand), "compareHands() returns wrong value when there is no winner");
    }

    @Test
    @DisplayName("Testing evalPPWinnings method")
    void evalPPWinnings() {
        ArrayList<Card> hand = new ArrayList<>();

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
