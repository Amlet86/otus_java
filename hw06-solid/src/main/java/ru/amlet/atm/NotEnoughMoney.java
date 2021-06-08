package ru.amlet.atm;

public class NotEnoughMoney extends RuntimeException {

    public NotEnoughMoney(int quantityOfMoney) {
        super("In ATM isn't enough money for issue: " + quantityOfMoney + " try once more.");
    }

}
