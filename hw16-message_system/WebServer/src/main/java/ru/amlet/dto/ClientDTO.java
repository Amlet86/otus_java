package ru.amlet.dto;

import messagesystem.client.ResultDataType;

public class ClientDTO extends ResultDataType {

    private Long id;
    private String name;
    private String role;

    public ClientDTO() {
    }

    public ClientDTO(long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

}
