package ru.amlet.processor;

import ru.amlet.model.Message;

public class ProcessorEvenSecondException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSecondException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0)
            throw new RuntimeException("Even second");
        return message;
    }
}
