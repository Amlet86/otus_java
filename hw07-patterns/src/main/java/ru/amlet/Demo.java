package ru.amlet;

import java.util.List;

import ru.amlet.services.handler.ComplexProcessor;
import ru.amlet.listener.ListenerPrinterConsole;
import ru.amlet.model.Message;
import ru.amlet.processor.LoggerProcessor;
import ru.amlet.processor.ProcessorConcatFields;
import ru.amlet.processor.ProcessorUpperField10;

public class Demo {

    public static void main(String[] args) {
        var processors = List.of(new ProcessorConcatFields(),
            new LoggerProcessor(new ProcessorUpperField10()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
            .field1("field1")
            .field2("field2")
            .field3("field3")
            .field6("field6")
            .field10("field10")
            .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
