package ru.amlet.dto;

import java.util.List;

import messagesystem.client.ResultDataType;

public class ClientsListDto extends ResultDataType {

    private List<ClientDTO> list;

    public ClientsListDto() {
    }

    public ClientsListDto(List<ClientDTO> list) {
        this.list = list;
    }

    public List<ClientDTO> getAll() {
        return list;
    }

}
