package ru.amlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ru.amlet.annotations.After;
import ru.amlet.annotations.Before;
import ru.amlet.annotations.Test;

public class TestLauncher {

    private int passed = 0;
    private int failed = 0;

    public void launchTests(String className) {
        executeTests(className);
        printResult();
    }

    private void executeTests(String className) {
        Class<?> clazz = createClassByName(className);

        List<Method> beforeMethods = findBeforeMethods(clazz);
        List<Method> testMethods = findTestMethods(clazz);
        List<Method> afterMethods = findAfterMethods(clazz);

        testMethods.forEach(method -> {

            Object instance = createInstance(clazz);

            beforeMethods.forEach(beforeMethod -> {
                try {
                    beforeMethod.invoke(instance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

            try {
                method.invoke(instance);
                passed++;
            } catch (IllegalAccessException | InvocationTargetException e) {
                failed++;
            }

            afterMethods.forEach(afterMethod -> {
                try {
                    afterMethod.invoke(instance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

        });
    }

    private Class<?> createClassByName(String className) {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clazz == null ? Optional.class : clazz;
    }

    private List<Method> findBeforeMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            if (Arrays.stream(method.getDeclaredAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getName().equals(Before.class.getName()))) {
                methods.add(method);
            }
        });
        return methods;
    }

    private List<Method> findTestMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            if (Arrays.stream(method.getDeclaredAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getName().equals(Test.class.getName()))) {
                methods.add(method);
            }
        });
        return methods;
    }

    private List<Method> findAfterMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            if (Arrays.stream(method.getDeclaredAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getName().equals(After.class.getName()))) {
                methods.add(method);
            }
        });
        return methods;
    }

    private Object createInstance(Class<?> clazz) {
        Object object = null;
        try {
            object = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return object == null ? Optional.empty() : object;
    }

    private void printResult() {
        System.out.println("Were launched: " + (passed + failed) + " tests, from them:");
        System.out.println("- passed = " + passed);
        System.out.println("- failed = " + failed);
    }

}
