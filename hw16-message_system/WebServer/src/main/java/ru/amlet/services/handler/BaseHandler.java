package ru.amlet.services.handler;

import java.util.Optional;

import messagesystem.RequestHandler;
import messagesystem.client.CallbackRegistry;
import messagesystem.client.MessageCallback;
import messagesystem.client.ResultDataType;
import messagesystem.message.Message;
import messagesystem.message.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseHandler implements RequestHandler<ResultDataType> {

    private static final Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    private final CallbackRegistry callbackRegistry;

    public BaseHandler(CallbackRegistry callbackRegistry) {
        this.callbackRegistry = callbackRegistry;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            MessageCallback<? extends ResultDataType> callback = callbackRegistry.getAndRemove(msg.getCallbackId());
            if (callback != null) {
                callback.accept(MessageHelper.getPayload(msg));
            } else {
                logger.error("callback for Id:{} not found", msg.getCallbackId());
            }
        } catch (Exception ex) {
            logger.error("msg:{}", msg, ex);
        }
        return Optional.empty();
    }

}
