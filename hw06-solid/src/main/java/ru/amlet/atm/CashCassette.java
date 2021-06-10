package ru.amlet.atm;

import ru.amlet.money.Money;

public class CashCassette<T extends Money> implements Cash<T> {

    private T money;
    private int currentQuantityOfRubles;
    private final int capacity;

    public CashCassette(T money, int quantityOfRubles) {
        this.money = money;
        this.currentQuantityOfRubles = quantityOfRubles;
        this.capacity = 100;
    }

    public CashCassette(T money, int quantityOfRubles, int capacity) {
        this.money = money;
        this.currentQuantityOfRubles = quantityOfRubles;
        this.capacity = capacity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.currentQuantityOfRubles = quantity;
    }

    @Override
    public void setMoney(T money) {
        this.money = money;
    }

    @Override
    public int getQuantity() {
        return currentQuantityOfRubles;
    }

    @Override
    public T getMoney() {
        return money;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public int compareTo(Cash<T> cash) {
        return money.getDenomination().compareTo(cash.getMoney().getDenomination());
    }

}
