package ru.amlet.services;

import messagesystem.client.MessageCallback;
import messagesystem.client.MsClient;
import messagesystem.message.Message;
import messagesystem.message.MessageType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amlet.dto.ClientDTO;
import ru.amlet.dto.ClientsListDto;

import static ru.amlet.repository.ClientRepositoryMsConfiguration.CLIENT_REPOSITORY_MS_CLIENT_NAME;
import static ru.amlet.services.ClientServiceMsConfiguration.CLIENT_SERVICE_MS_CLIENT_NAME;

@Service
public class ClientServiceImpl implements ClientService {

    private final MsClient msClient;

    public ClientServiceImpl(@Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME) MsClient msClient) {
        this.msClient = msClient;
    }

    @Override
    @Transactional
    public void save(ClientDTO clientDto, MessageCallback<ClientDTO> callback) {
        Message outMsg = msClient.produceMessage(CLIENT_REPOSITORY_MS_CLIENT_NAME, clientDto, MessageType.SAVE_CLIENT, callback);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void findAll(MessageCallback<ClientsListDto> callback) {
        Message outMsg = msClient.produceMessage(CLIENT_REPOSITORY_MS_CLIENT_NAME, new ClientsListDto(), MessageType.GET_CLIENTS, callback);
        msClient.sendMessage(outMsg);
    }
}
