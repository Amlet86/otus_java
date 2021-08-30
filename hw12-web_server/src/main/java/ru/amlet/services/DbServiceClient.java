package ru.amlet.services;

import java.util.List;
import java.util.Optional;

import ru.amlet.model.Client;

public interface DbServiceClient {

    Client saveClient(Client client);

    Optional<Client> findById(long id);

    Optional<Client> findByLogin(String login);

    List<Client> findAll();
}