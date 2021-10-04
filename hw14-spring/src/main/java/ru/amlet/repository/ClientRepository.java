package ru.amlet.repository;

import org.springframework.data.repository.CrudRepository;
import ru.amlet.dao.ClientDAO;

public interface ClientRepository extends CrudRepository<ClientDAO, Long> {
}
