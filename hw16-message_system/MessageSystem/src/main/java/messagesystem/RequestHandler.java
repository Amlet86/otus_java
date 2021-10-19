package messagesystem;

import messagesystem.client.ResultDataType;
import messagesystem.message.Message;
import messagesystem.message.MessageType;

import java.util.Optional;

public interface RequestHandler<T extends ResultDataType> {

    Optional<Message> handle(Message msg);

    MessageType getMessageType();
}
