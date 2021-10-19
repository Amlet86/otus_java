package ru.amlet.services.handler;

import messagesystem.client.CallbackRegistry;
import messagesystem.message.MessageType;

import static messagesystem.message.MessageType.GET_CLIENTS;

public class GetClientsResponseHandler extends BaseHandler {

    public GetClientsResponseHandler(CallbackRegistry callbackRegistry) {
        super(callbackRegistry);
    }

    @Override
    public MessageType getMessageType() {
        return GET_CLIENTS;
    }
}
