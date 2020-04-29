package application;

public class BasicCoffee implements Coffee {

    private double cost = 3.99;

    @Override
    public double makeCoffee() {
        System.out.println(getText());
        return cost;
    }

    public static String getText() {
        return "Black Coffee: $3.99";
    }
}
