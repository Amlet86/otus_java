package ru.amlet.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ru.amlet.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    Map<Long, Message> map = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        map.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(map.get(id));
    }
}
