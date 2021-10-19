package ru.amlet.mappers;

import org.springframework.stereotype.Component;
import ru.amlet.dao.Client;
import ru.amlet.dto.ClientDTO;

@Component
public class ClientDtoMapperImpl implements ClientDtoMapper {

    @Override
    public Client createClient(ClientDTO clientDto) {
        return new Client(null, clientDto.getName(), clientDto.getRole());
    }

    @Override
    public ClientDTO createClientDto(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getRole());
    }

}
