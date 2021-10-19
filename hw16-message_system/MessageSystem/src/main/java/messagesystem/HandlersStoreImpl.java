package messagesystem;

import messagesystem.client.ResultDataType;
import messagesystem.message.MessageType;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HandlersStoreImpl implements HandlersStore {
    private final Map<String, RequestHandler<? extends ResultDataType>> handlers = new ConcurrentHashMap<>();

    public HandlersStoreImpl() {
    }

    public HandlersStoreImpl(Set<RequestHandler<? extends ResultDataType>> handlers) {
        for (RequestHandler<? extends ResultDataType> requestHandler : handlers) {
            this.addHandler(requestHandler.getMessageType(), requestHandler);
        }
    }

    @Override
    public RequestHandler<? extends ResultDataType> getHandlerByType(String messageTypeName) {
        return handlers.get(messageTypeName);
    }

    @Override
    public void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler) {
        handlers.put(messageType.getName(), handler);
    }


}
