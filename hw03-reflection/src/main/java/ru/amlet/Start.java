package ru.amlet;

public class Start {

    public static void main(String[] args) {
        TestLauncher launcher = new TestLauncher();
        launcher.launchTests(SomeTest.class.getName());
    }

}
