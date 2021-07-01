package ru.amlet.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT %s FROM %s",
            getAllFields(),
            entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("SELECT %s FROM %s WHERE %s = ?",
            getAllFields(),
            entityClassMetaData.getName().toLowerCase(),
            entityClassMetaData.getIdField().getName().toLowerCase());
    }

    private String getAllFields() {
        return entityClassMetaData.getAllFields().stream()
            .map(Field::getName)
            .collect(Collectors.joining(","))
            .toLowerCase();
    }

    @Override
    public String getInsertSql() {
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
            entityClassMetaData.getName().toLowerCase(),
            getFieldsWithoutIdForInsert(),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(sql -> "?")
                .collect(Collectors.joining(",")));
    }

    private String getFieldsWithoutIdForInsert() {
        return entityClassMetaData.getFieldsWithoutId().stream()
            .map(Field::getName)
            .collect(Collectors.joining(","))
            .toLowerCase();
    }

    @Override
    public String getUpdateSql() {
        return String.format("UPDATE %s SET %s WHERE %s = ?",
            entityClassMetaData.getName().toLowerCase(),
            getFieldsWithoutIdForUpdate(),
            entityClassMetaData.getIdField().getName().toLowerCase());
    }

    private String getFieldsWithoutIdForUpdate() {
        return entityClassMetaData.getFieldsWithoutId().stream()
            .map(field -> field.getName() + " = ?")
            .collect(Collectors.joining(","))
            .toLowerCase();
    }
}
