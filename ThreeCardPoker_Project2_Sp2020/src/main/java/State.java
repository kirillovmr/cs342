public class State {
    // Activates on deal button press
    boolean gameStarted;

    // Did players select a button?
    boolean player1Chosen;
    boolean player2Chosen;

    // Was UI updated?
    boolean player1UIUpdated;
    boolean player2UIUpdated;

    // Did players select play?
    boolean player1ChosenPlay;
    boolean player2ChosenPlay;

    State() {
        init();
    }

    // Restores initial game state
    void init() {
        gameStarted = false;
        player1Chosen = false;
        player2Chosen = false;
        player1UIUpdated = false;
        player2UIUpdated = false;
        player1ChosenPlay = false;
        player2ChosenPlay = false;
    }
}