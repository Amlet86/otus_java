package ru.amlet.atm;

import java.util.TreeSet;

public class StrongBox {

    private TreeSet<Cash<?>> cassettes;

    public StrongBox(TreeSet<Cash<?>> cassettes) {
        this.cassettes = cassettes;
    }

    public void setCassettes(TreeSet<Cash<?>> cassettes) {
        this.cassettes = cassettes;
    }

    public TreeSet<Cash<?>> getCassettes() {
        return cassettes;
    }

}
