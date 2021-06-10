package ru.amlet.atm;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import ru.amlet.money.Money;

public class CashMachine {

    private final ATMComputerImpl atmComputer;
    private final StrongBox strongBox;

    public CashMachine() {
        this.atmComputer = new ATMComputerImpl();
        this.strongBox = new StrongBox(new TreeSet<>());
    }

    public CashMachine(ATMComputerImpl atmComputer, StrongBox strongBox) {
        this.atmComputer = atmComputer;
        this.strongBox = strongBox;
    }

    public int getBalance() {
        return atmComputer.getBalance();
    }

    public void setCassettes(TreeSet<Cash<?>> cassettes) {
        strongBox.setCassettes(cassettes);
        atmComputer.setBalance(cassettes);
    }

    public Map<Money, Integer> issueBills(int quantityOfMoney) {
        atmComputer.checkEnoughBalance(quantityOfMoney);
        return atmComputer.getIssueBills(quantityOfMoney, strongBox);
    }

}
