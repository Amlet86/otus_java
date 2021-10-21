package ru.amlet.repository.handler;

import java.util.Optional;

import messagesystem.RequestHandler;
import messagesystem.message.Message;
import messagesystem.message.MessageBuilder;
import messagesystem.message.MessageHelper;
import messagesystem.message.MessageType;
import ru.amlet.dao.Client;
import ru.amlet.dto.ClientDTO;
import ru.amlet.mappers.ClientDtoMapper;
import ru.amlet.repository.ClientRepository;

public class SaveClientRequestHandler implements RequestHandler<ClientDTO> {

    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;

    public SaveClientRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoMapper = clientDtoMapper;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        ClientDTO clientDto = MessageHelper.getPayload(msg);
        Client savedClient = clientRepository.save(clientDtoMapper.createClient(clientDto));
        clientDto.setId(savedClient.getId());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, getMessageType(), clientDto));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.SAVE_CLIENT;
    }
}
