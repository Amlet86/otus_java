package ru.amlet.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.amlet.annotations.Log;

public class Invoker {

    List<String> list = new ArrayList<>();

    public Invoker() {
    }

    public Logger createLoggedClass(Logger logger) {
        Arrays.stream(logger.getClass().getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(Log.class))
            .forEach(it -> list.add(it.getName() + Arrays.toString(it.getParameters())));

        InvocationHandler handler = new DemoInvocationHandler(logger);
        return (Logger) Proxy.newProxyInstance(Invoker.class.getClassLoader(),
            new Class<?>[]{Logger.class}, handler);
    }

    class DemoInvocationHandler implements InvocationHandler {
        private final Logger logger;

        DemoInvocationHandler(Logger logger) {
            this.logger = logger;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String name = method.getName();
            String params = Arrays.toString(method.getParameters());

            System.out.println("---------------------------");
            if (list.contains(name + params)) {
                System.out.println("invoking method:" + name);
                System.out.println("invoking args:" + Arrays.toString(args));
            }
            System.out.println("---------------------------");
            System.out.println("Result of method execution: ");

            return method.invoke(logger, args);
        }

    }
}
