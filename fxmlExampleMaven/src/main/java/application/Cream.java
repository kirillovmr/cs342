package application;

public class Cream extends CoffeeDecorator{

    private double cost = .50;

    Cream(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee() + addCream();
    }

    public static String getText() {
        return " + cream: $0.50";
    }

    private double addCream() {
        System.out.println(getText());
        return cost;
    }
}

