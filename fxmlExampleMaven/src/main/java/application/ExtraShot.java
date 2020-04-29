package application;

public class ExtraShot extends CoffeeDecorator {

    private double cost = 1.20;

    ExtraShot(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee() + addShot();
    }

    public static String getText() {
        return " + extra shot: $1.20";
    }

    private double addShot() {
        System.out.println(getText());
        return cost;
    }
}
