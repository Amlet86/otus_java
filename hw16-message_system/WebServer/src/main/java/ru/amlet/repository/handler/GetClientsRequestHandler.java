package ru.amlet.repository.handler;

import java.util.ArrayList;
import java.util.Optional;

import messagesystem.RequestHandler;
import messagesystem.message.Message;
import messagesystem.message.MessageBuilder;
import messagesystem.message.MessageType;
import ru.amlet.dto.ClientDTO;
import ru.amlet.dto.ClientsListDto;
import ru.amlet.mappers.ClientDtoMapper;
import ru.amlet.repository.ClientRepository;

public class GetClientsRequestHandler implements RequestHandler<ClientsListDto> {

    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;

    public GetClientsRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoMapper = clientDtoMapper;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        var clientList = new ArrayList<ClientDTO>();
        clientRepository.findAll()
            .forEach(client ->
                clientList.add(clientDtoMapper.createClientDto(client))
            );
        return Optional.of(MessageBuilder.buildReplyMessage(msg, getMessageType(), new ClientsListDto(clientList)));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.GET_CLIENTS;
    }
}
