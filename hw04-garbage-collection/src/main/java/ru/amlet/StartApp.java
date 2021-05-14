package ru.amlet;

public class StartApp {

    public static void main(String[] args) throws Exception {
        Monitoring.logMXBeanName();
        Monitoring.switchOnMonitoring();

        Benchmark generator = new Benchmark(100000);
        Monitoring.mBeanRegister(generator);

        generator.run();

    }

}
