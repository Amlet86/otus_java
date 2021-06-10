package ru.amlet.atm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.amlet.money.Money;

public class ATMComputer {

    private int balance;

    void setBalance(int balance) {
        this.balance = balance;
    }

    int getBalance() {
        return balance;
    }

    public void checkEnoughBalance(int quantityOfMoney) {
        if (balance < quantityOfMoney) {
            throw new NotEnoughMoney(quantityOfMoney);
        }
    }

    public Map<Money, Integer> getIssueBills(int quantityOfMoney, StrongBox strongBox) {
        Set<Cash<?>> reversedStrongBox = Collections.unmodifiableSortedSet(strongBox.getCassettes().descendingSet());
        Map<Money, Integer> result = new HashMap<>();
        int tmpQuantityOfMoney = quantityOfMoney;
        for (Cash<?> cash : reversedStrongBox) {
            int resultOfComparing = compareRequestAndCurrentAmountOfMoney(tmpQuantityOfMoney, cash);
            if (tmpQuantityOfMoney != resultOfComparing) {
                result.compute(cash.getMoney(), (k, v) -> (v == null ? 0 : v) + 1);
            }
            tmpQuantityOfMoney = resultOfComparing;
        }
        could(quantityOfMoney, result);
        return result;
    }

    private int compareRequestAndCurrentAmountOfMoney(int quantityOfMoney, Cash<?> cash) {
        if (cash.getQuantity() == 0) {
            return quantityOfMoney;
        }
        int remainder = quantityOfMoney - cash.getMoney().getDenomination();
        return remainder >= 0 ? remainder : quantityOfMoney;
    }

    private void could(int quantityOfMoney, Map<Money, Integer> result) {
        int tmp = 0;
        for (Map.Entry<Money, Integer> entry : result.entrySet()) {
            Money money = entry.getKey();
            Integer currentValueOfMoney = entry.getValue();
            tmp += money.getDenomination() * currentValueOfMoney;
        }
        if (tmp != quantityOfMoney) {
            throw new NoMatchingBills(quantityOfMoney);
        }
    }

}