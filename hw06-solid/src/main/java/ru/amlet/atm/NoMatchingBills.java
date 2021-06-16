package ru.amlet.atm;

public class NoMatchingBills extends RuntimeException {

    public NoMatchingBills(int countOfMoney) {
        super("No matching bills for issue: " + countOfMoney + " try once more.");
    }

}
