package application;

public class Honey extends CoffeeDecorator {

    private double cost = .40;

    Honey(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee() + addHoney();
    }

    public static String getText() {
        return " + honey: $0.40";
    }

    private double addHoney() {
        System.out.println(getText());
        return cost;
    }
}
