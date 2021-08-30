package ru.amlet.services;

public class ClientAuthServiceImpl implements ClientAuthService {

    private final DbServiceClient dbServiceClient;

    public ClientAuthServiceImpl(DbServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceClient.findByLogin(login)
                .map(client -> client.getPassword().equals(password))
                .orElse(false);
    }

}
