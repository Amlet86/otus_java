package ru.amlet.services.handler;

import messagesystem.client.CallbackRegistry;
import messagesystem.message.MessageType;

import static messagesystem.message.MessageType.SAVE_CLIENT;

public class SaveClientResponseHandler extends BaseHandler {

    public SaveClientResponseHandler(CallbackRegistry callbackRegistry) {
        super(callbackRegistry);
    }

    @Override
    public MessageType getMessageType() {
        return SAVE_CLIENT;
    }
}
