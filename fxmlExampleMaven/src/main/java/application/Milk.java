package application;

public class Milk extends CoffeeDecorator {

    private double cost = .40;

    Milk(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee() + addMilk();
    }

    public static String getText() {
        return " + milk: $0.40";
    }

    private double addMilk() {
        System.out.println(getText());
        return cost;
    }
}
