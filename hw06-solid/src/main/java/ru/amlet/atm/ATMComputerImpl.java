package ru.amlet.atm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import ru.amlet.money.Money;

public class ATMComputerImpl implements Computer {

    private int balance;

    void setBalance(TreeSet<Cash<?>> cassettes) {
        this.balance = convertBillsToBalance(cassettes);
    }

    int getBalance() {
        return balance;
    }

    @Override
    public void checkEnoughBalance(int quantityOfMoney) {
        if (balance < quantityOfMoney) {
            throw new NotEnoughMoney(quantityOfMoney);
        }
    }

    private int convertBillsToBalance(TreeSet<Cash<?>> cassettes) {
        AtomicInteger balance = new AtomicInteger();
        cassettes.forEach(
            cassette -> balance.addAndGet(cassette.getMoney().getDenomination() * cassette.getQuantity())
        );
        return balance.get();
    }

    public Map<Money, Integer> getIssueBills(int quantityOfMoney, StrongBox strongBox) {
        Set<Cash<?>> reversedStrongBox = Collections.unmodifiableSortedSet(strongBox.getCassettes().descendingSet());
        Map<Money, Integer> result = new HashMap<>();
        int restOfMoney = quantityOfMoney;
        for (Cash<?> cash : reversedStrongBox) {
            int resultOfComparing = compareRequestAndCurrentAmountOfMoney(restOfMoney, cash);
            if (restOfMoney != resultOfComparing) {
                result.compute(cash.getMoney(), (k, v) -> (v == null ? 0 : v) + 1);
            }
            restOfMoney = resultOfComparing;
        }
        couldIssueBills(quantityOfMoney, result);
        return result;
    }

    private int compareRequestAndCurrentAmountOfMoney(int quantityOfMoney, Cash<?> cash) {
        if (cash.getQuantity() == 0) {
            return quantityOfMoney;
        }
        int remainder = quantityOfMoney - cash.getMoney().getDenomination();
        return remainder >= 0 ? remainder : quantityOfMoney;
    }

    private void couldIssueBills(int quantityOfMoneyFromRequest, Map<Money, Integer> result) {
        int quantityOfMoneyInATM = 0;
        for (Map.Entry<Money, Integer> entry : result.entrySet()) {
            Money money = entry.getKey();
            Integer currentValueOfMoney = entry.getValue();
            quantityOfMoneyInATM += money.getDenomination() * currentValueOfMoney;
        }
        if (quantityOfMoneyInATM != quantityOfMoneyFromRequest) {
            throw new NoMatchingBills(quantityOfMoneyFromRequest);
        }
    }

}