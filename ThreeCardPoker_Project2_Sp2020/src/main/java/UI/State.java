package UI;

public class State {
    // Activates on deal button press
    public boolean gameStarted;

    // Did players select a button?
    public boolean player1Chosen;
    public boolean player2Chosen;

    // Was UI.UI updated?
    public boolean player1UIUpdated;
    public boolean player2UIUpdated;

    // Did players select play?
    public boolean player1ChosenPlay;
    public boolean player2ChosenPlay;

    public boolean showingDealButton;

    public State() {
        init();
    }

    // Restores initial game state
    public void init() {
        gameStarted = false;
        player1Chosen = false;
        player2Chosen = false;
        player1UIUpdated = false;
        player2UIUpdated = false;
        player1ChosenPlay = false;
        player2ChosenPlay = false;

        showingDealButton = true;
    }
}