package ru.amlet.services;

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
import ru.amlet.repository.handler.GetClientsRequestHandler;
import ru.amlet.services.handler.GetClientsResponseHandler;
import ru.amlet.services.handler.SaveClientResponseHandler;

@Configuration
public class ClientServiceMsConfiguration {

    public static final String CLIENT_SERVICE_MS_CLIENT_NAME = "ClientService";

    @Bean
    @Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createClientResponseHandler(CallbackRegistry callbackRegistry) {
        return new SaveClientResponseHandler(callbackRegistry);
    }

    @Bean
    @Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createClientsListResponseHandler(CallbackRegistry callbackRegistry) {
        return new GetClientsResponseHandler(callbackRegistry);
    }

    @Bean(value = CLIENT_SERVICE_MS_CLIENT_NAME)
    MsClient createWebMsClient(MessageSystem messageSystem,
                               @Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME) Set<RequestHandler<? extends ResultDataType>> handlers,
                               CallbackRegistry callbackRegistry) {
        MsClientImpl clientService = new MsClientImpl(CLIENT_SERVICE_MS_CLIENT_NAME, messageSystem, new HandlersStoreImpl(handlers), callbackRegistry);
        messageSystem.addClient(clientService);
        return clientService;
    }

}
