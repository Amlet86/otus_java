package ru.amlet.atm;

import java.util.TreeSet;

/*
 * Сейф.
 * Хранит кассеты с наличностью.
 */
public class StrongBox {

    private TreeSet<Cash<?>> cassettes;

    public StrongBox(TreeSet<Cash<?>> cassettes) {
        this.cassettes = cassettes;
    }

    void setCassettes(TreeSet<Cash<?>> cassettes) {
        this.cassettes = cassettes;
    }

    public TreeSet<Cash<?>> getCassettes() {
        TreeSet<Cash<?>> cassettes = new TreeSet<>();
        cassettes.addAll(this.cassettes);
        return cassettes;
    }

}
