package ru.amlet.services;

import messagesystem.client.MessageCallback;
import ru.amlet.dto.ClientDTO;
import ru.amlet.dto.ClientsListDto;

public interface ClientService {

    void findAll(MessageCallback<ClientsListDto> callback);

    void save(ClientDTO clientDto, MessageCallback<ClientDTO> callback);
}
