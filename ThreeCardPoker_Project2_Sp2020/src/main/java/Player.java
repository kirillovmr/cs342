import UI.MyHandler;

import java.util.ArrayList;

/*
    This class represents a player in the game. It keeps track of each games current hand
    and current bets as well as the total winnings for that player across multiple games. If
    the player has lost more than he/she has won, that number can be negative. Provide a
    no argument constructor for this class
 */

public class Player {
    protected ArrayList<Card> hand;
    protected int anteBet;
    protected int playBet;
    protected int pairPlusBet;
    protected int totalWinnings;

    protected int balance;
    private MyHandler onBetChange = (dummy) -> {};
    private MyHandler onBalanceChange = (newBalance) -> {};

    Player() {
        hand = new ArrayList<>();
        anteBet = playBet = pairPlusBet = totalWinnings = 0;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getAnteBet() {
        return anteBet;
    }

    public void setAnteBet(int anteBet) {
        this.anteBet = anteBet;
        this.onBetChange.run(0);
    }

    public int getPlayBet() {
        return playBet;
    }

    public void setPlayBet(int playBet) {
        this.playBet = playBet;
        this.onBetChange.run(0);
    }

    public int getPairPlusBet() {
        return pairPlusBet;
    }

    public void setPairPlusBet(int pairPlusBet) {
        this.pairPlusBet = pairPlusBet;
        this.onBetChange.run(0);
    }

    public int getTotalWinnings() {
        return totalWinnings;
    }

    public void setTotalWinnings(int totalWinnings) {
        this.totalWinnings = totalWinnings;
    }

    public void setOnBetChange(MyHandler onBetChange) {
        this.onBetChange = onBetChange;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        this.onBalanceChange.run(balance);
    }

    public int getBalance() {
        return balance;
    }

    public void setOnBalanceChange(MyHandler onBalanceChange) {
        this.onBalanceChange = onBalanceChange;
    }
}
