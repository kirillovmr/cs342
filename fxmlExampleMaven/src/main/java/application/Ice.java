package application;

public class Ice extends CoffeeDecorator {

    private double cost = .20;

    Ice(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee() + addIce();
    }

    public static String getText() {
        return " + ice: $0.20";
    }

    private double addIce() {
        System.out.println(getText());
        return cost;
    }
}
