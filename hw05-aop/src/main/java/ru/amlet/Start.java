package ru.amlet;

import ru.amlet.ioc.Invoker;
import ru.amlet.ioc.Logger;
import ru.amlet.ioc.LoggerImpl;

public class Start {

    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Logger log = invoker.createLoggedClass(new LoggerImpl());

        log.calculation(1);
        log.calculation(3, 4);
        log.calculation(5, 6, "7");
    }
}
