package ru.amlet.atm;

import java.util.Map;

import ru.amlet.money.Money;

public interface Computer {

    void checkEnoughBalance(int quantityOfMoney);

    Map<Money, Integer>  getIssueBills(int quantityOfMoney, StrongBox strongBox);
}
