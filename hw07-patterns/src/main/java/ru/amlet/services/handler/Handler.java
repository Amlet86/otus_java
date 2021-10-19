package ru.amlet.services.handler;

import ru.amlet.listener.Listener;
import ru.amlet.model.Message;

public interface Handler {

    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
