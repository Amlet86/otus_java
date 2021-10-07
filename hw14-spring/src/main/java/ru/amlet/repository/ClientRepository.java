package ru.amlet.repository;

import org.springframework.data.repository.CrudRepository;
import ru.amlet.dao.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
