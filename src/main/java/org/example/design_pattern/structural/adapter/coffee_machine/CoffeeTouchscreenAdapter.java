package org.example.design_pattern.structural.adapter.coffee_machine;

public class CoffeeTouchscreenAdapter implements CoffeeMachineInterface {

    private OldCoffeeMachine oldCoffeeMachine;

    public CoffeeTouchscreenAdapter(OldCoffeeMachine oldCoffeeMachine) {
        this.oldCoffeeMachine = oldCoffeeMachine;
    }

    @Override
    public void chooseFirstSelection() {
        System.out.println("The first selection is: ");
        oldCoffeeMachine.selectA();
    }

    @Override
    public void chooseSecondSelection() {
        System.out.println("The second selection is: ");
        oldCoffeeMachine.selectB();
    }

}
