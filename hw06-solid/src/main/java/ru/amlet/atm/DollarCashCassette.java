package ru.amlet.atm;

import ru.amlet.money.Dollars;

public class DollarCashCassette implements Cash<Dollars>, Cassette {

    private Dollars dollars;
    private int currentQuantityOfDollars;
    private final int capacity;

    public DollarCashCassette(Dollars dollars, int quantityOfDollars) {
        this.dollars = dollars;
        this.currentQuantityOfDollars = quantityOfDollars;
        this.capacity = CAPACITY;
    }

    @Override
    public void setQuantity(int quantity) {
        this.currentQuantityOfDollars = quantity;
    }

    @Override
    public void setMoney(Dollars dollars) {
        this.dollars = dollars;
    }

    @Override
    public int getQuantity() {
        return currentQuantityOfDollars;
    }

    @Override
    public Dollars getMoney() {
        return dollars;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int compareTo(Cash<Dollars> cash) {
        return dollars.getDenomination().compareTo(cash.getMoney().getDenomination());
    }

}
