package ru.amlet.listener;

import java.util.Optional;

import ru.amlet.model.Message;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
