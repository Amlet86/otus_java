package ru.amlet.mappers;

import ru.amlet.dao.Client;
import ru.amlet.dto.ClientDTO;

public interface ClientDtoMapper {

    Client createClient(ClientDTO clientDto);

    ClientDTO createClientDto(Client client);
}
