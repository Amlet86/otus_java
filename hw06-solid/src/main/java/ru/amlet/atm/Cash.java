package ru.amlet.atm;

import ru.amlet.money.Money;

public interface Cash<T extends Money> extends Comparable<Cash<T>> {

    void setQuantity(int quantity);

    void setMoney(T money);

    int getQuantity();

    T getMoney();

}
