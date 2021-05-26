package ru.amlet;

import ru.amlet.annotations.After;
import ru.amlet.annotations.Before;
import ru.amlet.annotations.Test;

public class SomeTest {

    @Before
    void beforeMethod() {
        System.out.println("Before each");
    }

    @Test
    void firstTest(){
        System.out.println("I'm the first");
    }

    @Test
    void secondTest(){
        System.out.println(5 / 0);
    }

    @Test
    void thirdTest(){
        System.out.println("I'm the third");
    }

    @After
    void afterMethod() {
        System.out.println("After each");
    }

}
