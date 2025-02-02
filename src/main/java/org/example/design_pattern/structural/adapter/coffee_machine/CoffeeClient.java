package org.example.design_pattern.structural.adapter.coffee_machine;

public class CoffeeClient {

    public static void main(String[] args) {
        CoffeeMachineInterface client = new CoffeeTouchscreenAdapter(new OldCoffeeMachine());
        client.chooseFirstSelection();
        client.chooseSecondSelection();
    }
}
