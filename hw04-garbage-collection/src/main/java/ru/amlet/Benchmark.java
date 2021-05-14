package ru.amlet;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {

    private int size;

    private static List list = new ArrayList();

    public Benchmark(int size) {
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                list.add(new BigInteger(2340, new SecureRandom()).toString(32));
            }
            for (int j = size / 2; j < list.size(); j++) {
                list.remove(j);
            }
            Thread.sleep(100);
        }
        list.forEach(System.out::println);
    }

}
