package ru.amlet.atm;

import ru.amlet.money.Rubles;

public class RubleCashCassette implements Cash<Rubles> {

    private Rubles rubles;
    private int currentQuantityOfRubles;
    private final int capacity;

    public RubleCashCassette(Rubles rubles, int quantityOfRubles) {
        this.rubles = rubles;
        this.currentQuantityOfRubles = quantityOfRubles;
        this.capacity = 100;
    }

    public RubleCashCassette(Rubles rubles, int quantityOfRubles, int capacity) {
        this.rubles = rubles;
        this.currentQuantityOfRubles = quantityOfRubles;
        this.capacity = capacity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.currentQuantityOfRubles = quantity;
    }

    @Override
    public void setMoney(Rubles rubles) {
        this.rubles = rubles;
    }

    @Override
    public int getQuantity() {
        return currentQuantityOfRubles;
    }

    public Rubles getMoney() {
        return rubles;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public int compareTo(Cash<Rubles> cash) {
        return rubles.getDenomination().compareTo(cash.getMoney().getDenomination());
    }

}
