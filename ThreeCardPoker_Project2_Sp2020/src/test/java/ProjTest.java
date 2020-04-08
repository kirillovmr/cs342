//Author: Mehrnaz Najafi
//Version: 1.0 - March 23
//Version: 2.0 - March 25
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ProjTest {

    //hand refers to player's hand
    private ArrayList<Card> hand;
    //dhand refers to dealer's hand
    private ArrayList<Card> dHand;
    private ThreeCardLogic logic;
    private int bet;

    @BeforeEach
    void setUp() {
        //for player
        hand  = new ArrayList<Card>();
        //for dealer
        dHand = new ArrayList<Card>();
        logic = new ThreeCardLogic();
    }


    //evalHand for high card - suggested by Prof. Hallenbeck to be Q
    @Test
    void testEvalHandHC() {
        Card card1 = new Card('D', 12);
        hand.add(card1);
        Card card2 = new Card('S', 2);
        hand.add(card2);
        Card card3 = new Card('C', 7);
        hand.add(card3);
        assertEquals(0, logic.evalHand(hand));
    }

    //evalHand for straight flush
    @Test
    void testEvalHandSF() {
        Card card1 = new Card('C', 10);
        hand.add(card1);
        Card card2 = new Card('C', 9);
        hand.add(card2);
        Card card3 = new Card('C', 8);
        hand.add(card3);
        assertEquals(1, logic.evalHand(hand));
    }

    //evalHand for three of a kind
    @Test
    void testEvalHandTK() {
        Card card1 = new Card('C', 12);
        hand.add(card1);
        Card card2 = new Card('H', 12);
        hand.add(card2);
        Card card3 = new Card('S', 12);
        hand.add(card3);
        assertEquals(2, logic.evalHand(hand));
    }

    //evalHand for straight
    @Test
    void testEvalHandS() {
        Card card1 = new Card('D', 8);
        hand.add(card1);
        Card card2 = new Card('C', 7);
        hand.add(card2);
        Card card3 = new Card('D', 6);
        hand.add(card3);
        assertEquals(3, logic.evalHand(hand));
    }

    //evalHand for flush
    @Test
    void testEvalHandF() {
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('D', 9);
        hand.add(card2);
        Card card3 = new Card('D', 7);
        hand.add(card3);
        assertEquals(4, logic.evalHand(hand));
    }

    //evalHand for Pair
    @Test
    void testEvalHandP() {
        Card card1 = new Card('H', 13);
        hand.add(card1);
        Card card2 = new Card('C', 13);
        hand.add(card2);
        Card card3 = new Card('D', 9);
        hand.add(card3);
        assertEquals(5, logic.evalHand(hand));
    }

    //evalPPWinnings for high card
    @Test
    void testEvalWinningsHC() {
        Card card1 = new Card('D', 14);
        hand.add(card1);
        Card card2 = new Card('S', 2);
        hand.add(card2);
        Card card3 = new Card('C', 7);
        hand.add(card3);
        bet = 5;
        assertEquals(0, logic.evalPPWinnings(hand, bet));
    }

    //evalPPWinnings for straight flush
    @Test
    void testEvalWinningsSF() {
        Card card1 = new Card('C', 10);
        hand.add(card1);
        Card card2 = new Card('C', 9);
        hand.add(card2);
        Card card3 = new Card('C', 8);
        hand.add(card3);
        bet = 5;
        int value = logic.evalPPWinnings(hand, bet);
        assertTrue(value >= (bet * 40));
    }

    //evalPPWinnings for three of a kind
    @Test
    void testEvalWinningsTK() {
        Card card1 = new Card('C', 12);
        hand.add(card1);
        Card card2 = new Card('H', 12);
        hand.add(card2);
        Card card3 = new Card('S', 12);
        hand.add(card3);
        bet = 5;
        int value = logic.evalPPWinnings(hand, bet);
        assertTrue(value >= (bet * 30));
    }

    //evalPPWinnings for straight
    @Test
    void testEvalWinningsS() {
        Card card1 = new Card('D', 8);
        hand.add(card1);
        Card card2 = new Card('C', 7);
        hand.add(card2);
        Card card3 = new Card('D', 6);
        hand.add(card3);
        bet = 5;
        int value = logic.evalPPWinnings(hand, bet);
        assertTrue(value >= (bet * 6));
    }

    //evalPPWinnings for flush
    @Test
    void testEvalWinningsF() {
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('D', 9);
        hand.add(card2);
        Card card3 = new Card('D', 7);
        hand.add(card3);
        bet = 5;
        int value = logic.evalPPWinnings(hand, bet);
        assertTrue(value >= (bet * 3));
    }

    //evalPPWinnings for pair
    @Test
    void testEvalWinningsP() {
        Card card1 = new Card('H', 13);
        hand.add(card1);
        Card card2 = new Card('C', 13);
        hand.add(card2);
        Card card3 = new Card('D', 9);
        hand.add(card3);
        bet = 5;
        int value = logic.evalPPWinnings(hand, bet);
        assertTrue(value >= (bet * 1));
    }

    //compareHands dealer: straight flush, player: straight flush
    //MN March 24
    @Test
    void testCompareHandsSFSF() {
        //player: straight flush
        Card card11 = new Card('C', 10);
        hand.add(card11);
        Card card12 = new Card('C', 9);
        hand.add(card12);
        Card card13 = new Card('C', 8);
        hand.add(card13);

        //dealer: straight flush
        Card card21 = new Card('D', 9);
        dHand.add(card21);
        Card card22 = new Card('D', 8);
        dHand.add(card22);
        Card card23 = new Card('D', 7);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: three of a kind, player: three of a kind
    //MN March 24
    @Test
    void testCompareHandsTKTK() {
        //player: three of a kind
        Card card1 = new Card('C', 12);
        hand.add(card1);
        Card card2 = new Card('H', 12);
        hand.add(card2);
        Card card3 = new Card('S', 12);
        hand.add(card3);

        //dealer: three of a kind
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('H', 10);
        dHand.add(card22);
        Card card23 = new Card('S', 10);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: straight, player: straight
    //MN March 24
    @Test
    void testCompareHandsSS() {
        //player: straight
        Card card1 = new Card('D', 8);
        hand.add(card1);
        Card card2 = new Card('C', 7);
        hand.add(card2);
        Card card3 = new Card('D', 6);
        hand.add(card3);

        //dealer: straight
        Card card21 = new Card('D', 5);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer:  flush, player: flush
    //MN March 24
    //MN March 25 - removed based on Prof. Hallenbeck's feedback
    //@Test
    //void testCompareHandsFF() {
    //player: flush
    //	Card card1 = new Card('D', 13);
    //	hand.add(card1);
    //	Card card2 = new Card('D', 9);
    //	hand.add(card2);
    //	Card card3 = new Card('D', 7);
    //	hand.add(card3);

    //dealer: flush
    //	Card card21 = new Card('C', 10);
    //	dHand.add(card21);
    //	Card card22 = new Card('C', 4);
    //	dHand.add(card22);
    //	Card card23 = new Card('C', 3);
    //	dHand.add(card23);

    //	int value = logic.compareHands(dHand, hand);
    //	assertEquals(2, value);
    //}

    //compareHands dealer:  pair, player: pair
    //MN March 24
    @Test
    void testCompareHandsPP() {
        //player: pair
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('C', 13);
        hand.add(card2);
        Card card3 = new Card('H', 7);
        hand.add(card3);

        //dealer: pair
        Card card21 = new Card('H', 12);
        dHand.add(card21);
        Card card22 = new Card('C', 12);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: straight flush, player: three of a kind
    @Test
    void testCompareHandsTKSF() {
        //player: three of a kind
        Card card1 = new Card('C', 12);
        hand.add(card1);
        Card card2 = new Card('H', 12);
        hand.add(card2);
        Card card3 = new Card('S', 12);
        hand.add(card3);

        //dealer: straight flush
        Card card21 = new Card('D', 10);
        dHand.add(card21);
        Card card22 = new Card('D', 9);
        dHand.add(card22);
        Card card23 = new Card('D', 8);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }

    //compareHands dealer: straight flush, player: straight
    @Test
    void testCompareHandsSSF() {
        //player: straight
        Card card1 = new Card('D', 8);
        hand.add(card1);
        Card card2 = new Card('C', 7);
        hand.add(card2);
        Card card3 = new Card('D', 6);
        hand.add(card3);

        //dealer: straight flush
        Card card21 = new Card('D', 10);
        dHand.add(card21);
        Card card22 = new Card('D', 9);
        dHand.add(card22);
        Card card23 = new Card('D', 8);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }

    //compareHands dealer: straight flush, player: flush
    @Test
    void testCompareHandsFSF() {
        //player: flush
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('D', 9);
        hand.add(card2);
        Card card3 = new Card('D', 7);
        hand.add(card3);

        //dealer: straight flush
        Card card21 = new Card('D', 10);
        dHand.add(card21);
        Card card22 = new Card('D', 9);
        dHand.add(card22);
        Card card23 = new Card('D', 8);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }

    //compareHands dealer: straight flush, player: pair
    @Test
    void testCompareHandsPSF() {
        //player: pair
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('C', 13);
        hand.add(card2);
        Card card3 = new Card('H', 7);
        hand.add(card3);

        //dealer: straight flush
        Card card21 = new Card('D', 10);
        dHand.add(card21);
        Card card22 = new Card('D', 9);
        dHand.add(card22);
        Card card23 = new Card('D', 8);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }

    //compareHands dealer: three of a kind, player: straight flush
    @Test
    void testCompareHandsSFTK() {
        //player: straight flush
        Card card11 = new Card('C', 10);
        hand.add(card11);
        Card card12 = new Card('C', 9);
        hand.add(card12);
        Card card13 = new Card('C', 8);
        hand.add(card13);

        //dealer: three of a kind
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('H', 10);
        dHand.add(card22);
        Card card23 = new Card('S', 10);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: three of a kind, player: straight
    @Test
    void testCompareHandsSTK() {
        //player: straight
        Card card1 = new Card('D', 8);
        hand.add(card1);
        Card card2 = new Card('C', 7);
        hand.add(card2);
        Card card3 = new Card('D', 6);
        hand.add(card3);

        //dealer: three of a kind
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('H', 10);
        dHand.add(card22);
        Card card23 = new Card('S', 10);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }


    //compareHands dealer: three of a kind, player: flush
    @Test
    void testCompareHandsFTK() {
        //player: flush
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('D', 9);
        hand.add(card2);
        Card card3 = new Card('D', 7);
        hand.add(card3);

        //dealer: three of a kind
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('H', 10);
        dHand.add(card22);
        Card card23 = new Card('S', 10);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }

    //compareHands dealer: three of a kind, player: pair
    @Test
    void testCompareHandsPTK() {
        //player: pair
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('C', 13);
        hand.add(card2);
        Card card3 = new Card('H', 7);
        hand.add(card3);

        //dealer: three of a kind
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('H', 10);
        dHand.add(card22);
        Card card23 = new Card('S', 10);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }


    //compareHands dealer: straight, player: straight flush
    @Test
    void testCompareHandsSFS() {
        //player: straight flush
        Card card11 = new Card('C', 10);
        hand.add(card11);
        Card card12 = new Card('C', 9);
        hand.add(card12);
        Card card13 = new Card('C', 8);
        hand.add(card13);

        //dealer: straight
        Card card21 = new Card('D', 5);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: straight, player: three of a kind
    @Test
    void testCompareHandsTKS() {
        //player: three of a kind
        Card card1 = new Card('C', 12);
        hand.add(card1);
        Card card2 = new Card('H', 12);
        hand.add(card2);
        Card card3 = new Card('S', 12);
        hand.add(card3);

        //dealer: straight
        Card card21 = new Card('D', 5);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: straight, player: flush
    @Test
    void testCompareHandsFS() {
        //player: flush
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('D', 9);
        hand.add(card2);
        Card card3 = new Card('D', 7);
        hand.add(card3);

        //dealer: straight
        Card card21 = new Card('D', 5);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }

    //compareHands dealer: straight, player: pair
    @Test
    void testCompareHandsPS() {
        //player: pair
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('C', 13);
        hand.add(card2);
        Card card3 = new Card('H', 7);
        hand.add(card3);

        //dealer: straight
        Card card21 = new Card('D', 5);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }


    //compareHands dealer: flush, player: straight flush
    //MN March 24
    @Test
    void testCompareHandsSFF() {
        //player: straight flush
        Card card11 = new Card('C', 10);
        hand.add(card11);
        Card card12 = new Card('C', 9);
        hand.add(card12);
        Card card13 = new Card('C', 8);
        hand.add(card13);

        //dealer: flush
        Card card21 = new Card('C', 6);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('C', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: flush, player: three of a kind
    @Test
    void testCompareHandsTKF() {
        //player: three of a kind
        Card card1 = new Card('C', 12);
        hand.add(card1);
        Card card2 = new Card('H', 12);
        hand.add(card2);
        Card card3 = new Card('S', 12);
        hand.add(card3);

        //dealer: flush
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('C', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: flush, player: straight
    @Test
    void testCompareHandsSF() {
        //player: straight
        Card card1 = new Card('D', 8);
        hand.add(card1);
        Card card2 = new Card('C', 7);
        hand.add(card2);
        Card card3 = new Card('D', 6);
        hand.add(card3);

        //dealer: flush
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('C', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: flush, player: pair
    @Test
    void testCompareHandsPF() {
        //player: pair
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('C', 13);
        hand.add(card2);
        Card card3 = new Card('H', 7);
        hand.add(card3);

        //dealer: flush
        Card card21 = new Card('C', 10);
        dHand.add(card21);
        Card card22 = new Card('C', 4);
        dHand.add(card22);
        Card card23 = new Card('C', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(1, value);
    }

    //compareHands dealer: pair, player: straight flush
    @Test
    void testCompareHandsSFP() {
        //player: straight flush
        Card card11 = new Card('C', 10);
        hand.add(card11);
        Card card12 = new Card('C', 9);
        hand.add(card12);
        Card card13 = new Card('C', 8);
        hand.add(card13);

        //dealer: pair
        Card card21 = new Card('H', 12);
        dHand.add(card21);
        Card card22 = new Card('C', 12);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: pair, player: three of a kind
    //MN March 24
    @Test
    void testCompareHandsTKP() {
        //player: three of a kind
        Card card1 = new Card('C', 13);
        hand.add(card1);
        Card card2 = new Card('H', 13);
        hand.add(card2);
        Card card3 = new Card('S', 13);
        hand.add(card3);

        //dealer: pair
        Card card21 = new Card('H', 12);
        dHand.add(card21);
        Card card22 = new Card('C', 12);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: pair, player: straight
    @Test
    void testCompareHandsSP() {
        //player: straight
        Card card1 = new Card('D', 8);
        hand.add(card1);
        Card card2 = new Card('C', 7);
        hand.add(card2);
        Card card3 = new Card('D', 6);
        hand.add(card3);

        //dealer: pair
        Card card21 = new Card('H', 12);
        dHand.add(card21);
        Card card22 = new Card('C', 12);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }

    //compareHands dealer: pair, player: flush
    @Test
    void testCompareHandsFP() {
        //player: flush
        Card card1 = new Card('D', 13);
        hand.add(card1);
        Card card2 = new Card('D', 9);
        hand.add(card2);
        Card card3 = new Card('D', 7);
        hand.add(card3);

        //dealer: pair
        Card card21 = new Card('H', 12);
        dHand.add(card21);
        Card card22 = new Card('C', 12);
        dHand.add(card22);
        Card card23 = new Card('D', 3);
        dHand.add(card23);

        int value = logic.compareHands(dHand, hand);
        assertEquals(2, value);
    }
}