package ru.amlet.protobuf;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.amlet.protobuf.generated.InitiativeMessage;
import ru.amlet.protobuf.generated.NumberMessage;
import ru.amlet.protobuf.generated.RemoteNumbersServiceGrpc;

public class GRPCClient {

    static Logger logger = LoggerFactory.getLogger(GRPCClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    private static final int FIRST_VALUE = 0;
    private static final int LAST_VALUE = 30;
    private static final int NUMBER_OF_ITERATIONS = 50;

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger newValue = new AtomicInteger();

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
            .usePlaintext()
            .build();

        InitiativeMessage initiativeMessage = InitiativeMessage.newBuilder()
            .setFirstValue(FIRST_VALUE)
            .setLastValue(LAST_VALUE)
            .build();

        CountDownLatch latch = new CountDownLatch(1);

        RemoteNumbersServiceGrpc.RemoteNumbersServiceStub stub = RemoteNumbersServiceGrpc.newStub(channel);
        stub.generate(initiativeMessage, new StreamObserver<NumberMessage>() {

            @Override
            public void onNext(NumberMessage numberMessage) {
                logger.info("Generated value: {}", numberMessage.getValue());
                newValue.set(numberMessage.getValue());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\nThe end!");
            }
        });

        int currentValue = 0;

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            int atomicValue = newValue.getAndSet(0);
            currentValue += atomicValue + 1;
            logger.info("Current value {}", currentValue);
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }

        channel.shutdown();
    }

}
