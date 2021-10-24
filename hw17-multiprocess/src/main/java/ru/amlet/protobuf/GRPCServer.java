package ru.amlet.protobuf;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.amlet.protobuf.service.RemoteNumberGeneratorImpl;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        RemoteNumberGeneratorImpl remoteNumberGeneratorImpl = new RemoteNumberGeneratorImpl();

        Server server = ServerBuilder
            .forPort(SERVER_PORT)
            .addService(remoteNumberGeneratorImpl)
            .build();
        server.start();
        System.out.println("server is waiting for client connections...");
        server.awaitTermination();
    }
}
