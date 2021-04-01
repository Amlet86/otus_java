package ru.amlet;

import static com.google.common.base.Preconditions.checkArgument;

public class HelloOtus {

    public static void main(String[] args) {
        String someString = "qwerty";
        guavaExample(someString);
    }

    private static void guavaExample(String someString) {
        checkArgument(someString.length() > 0);
        System.out.println("SUCCESS");
    }

}
