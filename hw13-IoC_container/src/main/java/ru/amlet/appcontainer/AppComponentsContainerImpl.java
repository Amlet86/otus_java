package ru.amlet.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.amlet.appcontainer.api.AppComponent;
import ru.amlet.appcontainer.api.AppComponentsContainer;
import ru.amlet.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        List<Method> components = getSortedMethods(configClass);

        Object config = createInstance(configClass);

        components.forEach(component -> {
            Object[] args = Arrays.stream(component.getParameterTypes()).map(this::getAppComponent).toArray();
            Object obj = null;
            try {
                obj = component.invoke(config, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            appComponents.add(obj);
            appComponentsByName.put(component.getAnnotation(AppComponent.class).name(), obj);
        });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private List<Method> getSortedMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(AppComponent.class))
            .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
            .collect(Collectors.toList());
    }

    private Object createInstance(Class<?> configClass) {
        try {
            return configClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Creating new instance of " + configClass.getSimpleName() + " was failed");
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
            .filter(object -> componentClass.isAssignableFrom(object.getClass()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Component: " + componentClass.getSimpleName() + " not found"));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object result = appComponentsByName.get(componentName);
        if (result != null) {
            return (C) result;
        }
        throw new RuntimeException("Component: " + componentName + " not found");
    }
}
