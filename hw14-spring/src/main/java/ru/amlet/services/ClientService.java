package ru.amlet.services;

import java.util.List;
import java.util.Optional;

import ru.amlet.dao.ClientDAO;

public interface ClientService {

    ClientDAO save(ClientDAO client);

    Optional<ClientDAO> get(long id);

    List<ClientDAO> findAll();
}
