package ru.amlet.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;
    private String objectName;
    private String idField;
    private String allFields;
    private String fieldWithoutIdForInsert;
    private String fieldWithoutIdForUpdate;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT %s FROM %s",
            getAllFields(),
            getObjectName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("SELECT %s FROM %s WHERE %s = ?",
            getAllFields(),
            getObjectName(),
            getIdField());
    }

    @Override
    public String getInsertSql() {
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
            getObjectName(),
            getFieldsWithoutIdForInsert(),
            entityClassMetaData.getFieldsWithoutId().stream()
                .map(sql -> "?")
                .collect(Collectors.joining(",")));
    }

    @Override
    public String getUpdateSql() {
        return String.format("UPDATE %s SET %s WHERE %s = ?",
            getObjectName(),
            getFieldsWithoutIdForUpdate(),
            getIdField());
    }

    private String getAllFields() {
        if (allFields == null) {
            this.allFields = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(","))
                .toLowerCase();
        }
        return allFields;
    }

    private String getObjectName() {
        if (objectName == null) {
            this.objectName = entityClassMetaData.getName().toLowerCase();
        }
        return objectName;
    }

    private String getIdField() {
        if (idField == null) {
            this.idField = entityClassMetaData.getIdField().getName().toLowerCase();
        }
        return idField;
    }

    private String getFieldsWithoutIdForInsert() {
        if (fieldWithoutIdForInsert == null) {
            this.fieldWithoutIdForInsert = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(","))
                .toLowerCase();
        }
        return fieldWithoutIdForInsert;
    }

    private String getFieldsWithoutIdForUpdate() {
        if (fieldWithoutIdForUpdate == null) {
            this.fieldWithoutIdForUpdate = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(","))
                .toLowerCase();
        }
        return fieldWithoutIdForUpdate;
    }
}
