package application;

public class Sugar extends CoffeeDecorator{

    private double cost = .50;

    Sugar(Coffee specialCoffee){
        super(specialCoffee);
    }

    public double makeCoffee() {
        return specialCoffee.makeCoffee()+ addSugar();
    }

    public static String getText() {
        return " + sugar: $0.50";
    }

    private double addSugar() {
        System.out.println(getText());
        return cost;
    }
}
