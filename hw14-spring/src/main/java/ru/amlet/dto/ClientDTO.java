package ru.amlet.dto;

import ru.amlet.dao.Client;

public class ClientDTO {

    private String name;

    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Client toModel() {
        return new Client(getName(), getRole());
    }
}
