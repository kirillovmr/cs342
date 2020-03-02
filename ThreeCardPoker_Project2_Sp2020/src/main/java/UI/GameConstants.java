package UI;

/*
    Stores all the constants that are used in game
 */

public class GameConstants {
    public final static int globalWidth = 1440;
    public final static int globalHeight = 880;

    public final static int minBet = 5;
    public final static int maxBet = 25;

    public final static int cardFlipAnimationTime = 100;
    public final static int cardScaleAnimationTime = 150;

    public final static int initialMoneyValue = 100;

    public final static String dealBtnText = "Deal";
    public final static String startBtnText = "Start new game";

    public final static String cardBackFilename = "card_back.png";
    public final static double cardScale = (double)(globalWidth) / 8000;

    public final static String dealerFilename = "dealer.png";
    public final static double dealerScale = (double)(globalWidth) / 3000;

    public final static int tableWidth = (int)(globalWidth * 0.7);
    public final static int tableHeight = (int)(980 * 0.56);

    public final static int showWarningTime = 1500;
}
