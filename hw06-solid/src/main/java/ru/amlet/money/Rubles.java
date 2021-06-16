package ru.amlet.money;

public enum Rubles implements Money {

    ONE(1),
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    THOUSAND(1000);

    private final Integer denomination;

    Rubles(int denomination) {
        this.denomination = denomination;
    }

    @Override
    public String getName() {
        return "Ruble";
    }

    @Override
    public Integer getDenomination() {
        return denomination;
    }

    class RublesComparator implements Comparable<Rubles> {

        @Override
        public int compareTo(Rubles rubles) {
            return denomination.compareTo(rubles.getDenomination());
        }
    }
}
