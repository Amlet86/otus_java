package ru.amlet.money;

public enum Dollars implements Money {

    ONE(1),
    TEN(10),
    FIFTY(50),
    HUNDRED(100);

    private final Integer denomination;

    Dollars(int denomination) {
        this.denomination = denomination;
    }

    @Override
    public String getName() {
        return "Dollar";
    }

    @Override
    public Integer getDenomination() {
        return denomination;
    }

    class DollarsComparator implements Comparable<Dollars> {

        @Override
        public int compareTo(Dollars dollars) {
            return denomination.compareTo(dollars.getDenomination());
        }
    }

}
