package ru.amlet.processor;

import ru.amlet.model.Message;

public class ProcessorSwapsFields11and12 implements Processor {

    @Override
    public Message process(Message message) {
        return message.toBuilder()
            .field12(message.getField11())
            .field11(message.getField12())
            .build();
    }
}
