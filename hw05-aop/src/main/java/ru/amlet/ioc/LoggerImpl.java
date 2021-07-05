package ru.amlet.ioc;

import ru.amlet.annotations.Log;

public class LoggerImpl implements Logger {

    @Log
    @Override
    public void calculation(int param1) {
        System.out.println(param1);
    }

    @Override
    public void calculation(int param1, int param2) {
        int sum = param1 + param2;
        System.out.println(sum);
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        int sum = param1 + param2;
        System.out.println(sum + " + " + param3);
    }

}
