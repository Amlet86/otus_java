package ru.amlet;

import java.time.LocalDateTime;
import java.util.List;

import ru.amlet.handler.ComplexProcessor;
import ru.amlet.listener.ListenerPrinterConsole;
import ru.amlet.model.Message;
import ru.amlet.model.ObjectForMessage;
import ru.amlet.processor.LoggerProcessor;
import ru.amlet.processor.Processor;
import ru.amlet.processor.ProcessorConcatFields;
import ru.amlet.processor.ProcessorEvenSecondException;
import ru.amlet.processor.ProcessorSwapsFields11and12;
import ru.amlet.processor.ProcessorUpperField10;

public class HomeWork {

    public static void main(String[] args) {
        List<Processor> processors = List.of(
            new ProcessorConcatFields(),
            new LoggerProcessor(new ProcessorUpperField10()),
            new ProcessorSwapsFields11and12(),
            new ProcessorEvenSecondException(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
            .field1("field1")
            .field2("field2")
            .field3("field3")
            .field6("field6")
            .field10("field10")
            .field11("field11")
            .field12("field12")
            .field13(new ObjectForMessage())
            .build();

        var result = complexProcessor.handle(message);
        System.out.println("result: " + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
