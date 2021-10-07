package ru.amlet.services;

import java.util.List;
import java.util.Optional;

import ru.amlet.dao.Client;

public interface ClientService {

    Client save(Client client);

    Optional<Client> get(long id);

    List<Client> findAll();
}
