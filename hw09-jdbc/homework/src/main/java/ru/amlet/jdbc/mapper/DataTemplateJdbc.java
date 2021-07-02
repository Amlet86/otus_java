package ru.amlet.jdbc.mapper;

import ru.amlet.core.repository.DataTemplate;
import ru.amlet.core.repository.DataTemplateException;
import ru.amlet.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createInstance(rs);
                }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            List<T> clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    clientList.add(createInstance(rs));
                }
                return clientList;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            List<Object> values = extractValues(object);
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), values);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            List<Object> values = extractValues(object);
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), values);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> extractValues(T object) throws IllegalAccessException {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> values = new ArrayList<>(fields.size());

        for (Field field : fields) {
            field.setAccessible(true);
            values.add(field.get(object));
        }
        return values;
    }

    private T createInstance(ResultSet rs) throws Exception {
        Object instance = entityClassMetaData.getConstructor().newInstance();

        for (Field field : entityClassMetaData.getAllFields()) {
            Object value = rs.getObject(field.getName());
            field.setAccessible(true);
            field.set(instance, value);
        }

        return (T) instance;
    }

}
