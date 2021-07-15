package ru.amlet.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.amlet.core.annotations.Id;

public class EntityClassMetaDataImpl implements EntityClassMetaData {

    private final String className;

    private final Constructor<?> constructor;

    private final Field idField;

    private final List<Field> listFields;

    private final List<Field> listFieldsWithoutId;

    public EntityClassMetaDataImpl(Class<?> clazz) {
        this.className = clazz.getSimpleName();

        try {
            this.constructor = clazz.getConstructor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.idField = Arrays.stream(clazz.getDeclaredFields())
            .filter(it -> it.isAnnotationPresent(Id.class))
            .findFirst().orElseThrow(RuntimeException::new);

        this.listFields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());

        this.listFieldsWithoutId = Arrays.stream(clazz.getDeclaredFields())
            .filter(it -> !it.isAnnotationPresent(Id.class))
            .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public <T> Constructor<T> getConstructor() {
        return (Constructor<T>) constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return listFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return listFieldsWithoutId;
    }
}
