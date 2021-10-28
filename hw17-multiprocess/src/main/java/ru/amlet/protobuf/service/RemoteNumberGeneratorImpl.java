package ru.amlet.protobuf.service;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.amlet.protobuf.generated.NumberMessage;
import ru.amlet.protobuf.generated.InitiativeMessage;
import ru.amlet.protobuf.generated.RemoteNumbersServiceGrpc;

public class RemoteNumberGeneratorImpl extends RemoteNumbersServiceGrpc.RemoteNumbersServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(RemoteNumberGeneratorImpl.class);

    @Override
    public void generate(InitiativeMessage request, StreamObserver<NumberMessage> responseObserver) {
        var firstValue = request.getFirstValue();
        var lastValue = request.getLastValue();
        IntStream.range(firstValue, lastValue).forEach(value ->
            {
                sleep();
                logger.info("generated number: {}", value);
                responseObserver.onNext(value2GeneratedNumberMessage(value));
            }
        );
        responseObserver.onCompleted();
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private NumberMessage value2GeneratedNumberMessage(Integer value) {
        return NumberMessage.newBuilder()
            .setValue(value)
            .build();
    }
}
