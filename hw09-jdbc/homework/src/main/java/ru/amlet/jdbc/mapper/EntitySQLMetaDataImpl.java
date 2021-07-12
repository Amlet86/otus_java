package ru.amlet.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;
    private String selectAll;
    private String selectById;
    private String insert;
    private String update;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        if (selectAll == null) {
            selectAll = String.format("SELECT %s FROM %s",
                getAllFields(),
                entityClassMetaData.getName().toLowerCase());
        }
        return selectAll;
    }

    @Override
    public String getSelectByIdSql() {
        if (selectById == null) {
            selectById = String.format("SELECT %s FROM %s WHERE %s = ?",
                getAllFields(),
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getIdField().getName().toLowerCase());
        }
        return selectById;
    }

    private String getAllFields() {
        return entityClassMetaData.getAllFields().stream()
            .map(Field::getName)
            .collect(Collectors.joining(","))
            .toLowerCase();
    }

    @Override
    public String getInsertSql() {
        if (insert == null) {
            insert = String.format("INSERT INTO %s (%s) VALUES (%s)",
                entityClassMetaData.getName().toLowerCase(),
                getFieldsWithoutIdForInsert(),
                entityClassMetaData.getFieldsWithoutId().stream()
                    .map(sql -> "?")
                    .collect(Collectors.joining(",")));
        }
        return insert;
    }

    private String getFieldsWithoutIdForInsert() {
        return entityClassMetaData.getFieldsWithoutId().stream()
            .map(Field::getName)
            .collect(Collectors.joining(","))
            .toLowerCase();
    }

    @Override
    public String getUpdateSql() {
        if (update == null) {
            update = String.format("UPDATE %s SET %s WHERE %s = ?",
                entityClassMetaData.getName().toLowerCase(),
                getFieldsWithoutIdForUpdate(),
                entityClassMetaData.getIdField().getName().toLowerCase());
        }
        return update;
    }

    private String getFieldsWithoutIdForUpdate() {
        return entityClassMetaData.getFieldsWithoutId().stream()
            .map(field -> field.getName() + " = ?")
            .collect(Collectors.joining(","))
            .toLowerCase();
    }
}
