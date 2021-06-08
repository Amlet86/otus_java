package ru.amlet.atm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
        AtomicInteger tmp = new AtomicInteger(quantityOfMoney);
        reversedStrongBox.forEach(
            cash -> {
                int tmp2 = countTmp(tmp.get(), cash);
                if (tmp.get() != tmp2) {
                    result.compute(cash.getMoney(), (k, v) -> (v == null ? 0 : v) + 1);
                }
                tmp.set(tmp2);
            }
        );
        could(quantityOfMoney, result);
        return result;
    }

    private int countTmp(int quantityOfMoney, Cash<?> cash) {
        if (cash.getQuantity() == 0) {
            return quantityOfMoney;
        }
        int remainder = quantityOfMoney - cash.getMoney().getDenomination();
        return remainder >= 0 ? remainder : quantityOfMoney;
    }

    private void could(int quantityOfMoney, Map<Money, Integer> result) {
        AtomicInteger tmp = new AtomicInteger(0);
        result.forEach((money, integer) -> tmp.addAndGet(money.getDenomination() * integer));
        if (tmp.get() != quantityOfMoney) {
            throw new NoMatchingBills(quantityOfMoney);
        }
    }

}