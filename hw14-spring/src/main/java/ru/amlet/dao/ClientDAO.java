package ru.amlet.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
public class ClientDAO {

    @Id
    private final Long id;

    private final String name;

    private final String role;

    public ClientDAO(String name, String role) {
        this.id = null;
        this.name = name;
        this.role = role;
    }

    @PersistenceConstructor
    public ClientDAO(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
