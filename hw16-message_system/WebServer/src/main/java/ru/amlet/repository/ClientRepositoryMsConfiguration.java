package ru.amlet.repository;

import java.util.Set;

import messagesystem.HandlersStoreImpl;
import messagesystem.MessageSystem;
import messagesystem.RequestHandler;
import messagesystem.client.CallbackRegistry;
import messagesystem.client.MsClient;
import messagesystem.client.MsClientImpl;
import messagesystem.client.ResultDataType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.amlet.mappers.ClientDtoMapper;
import ru.amlet.repository.handler.GetClientsRequestHandler;
import ru.amlet.repository.handler.SaveClientRequestHandler;

@Configuration
public class ClientRepositoryMsConfiguration {

    public static final String CLIENT_REPOSITORY_MS_CLIENT_NAME = "ClientRepository";

    @Bean
    @Qualifier(value = CLIENT_REPOSITORY_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createGetAllClientsRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        return new GetClientsRequestHandler(clientRepository, clientDtoMapper);
    }

    @Bean
    @Qualifier(value = CLIENT_REPOSITORY_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createSaveClientRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        return new SaveClientRequestHandler(clientRepository, clientDtoMapper);
    }

    @Bean(value = CLIENT_REPOSITORY_MS_CLIENT_NAME)
    MsClient createClientServiceMsClient(MessageSystem messageSystem,
                                         @Qualifier(value = CLIENT_REPOSITORY_MS_CLIENT_NAME) Set<RequestHandler<? extends ResultDataType>> handlers,
                                         CallbackRegistry callbackRegistry) {
        MsClientImpl clientService = new MsClientImpl(CLIENT_REPOSITORY_MS_CLIENT_NAME, messageSystem, new HandlersStoreImpl(handlers), callbackRegistry);
        messageSystem.addClient(clientService);
        return clientService;
    }


}
