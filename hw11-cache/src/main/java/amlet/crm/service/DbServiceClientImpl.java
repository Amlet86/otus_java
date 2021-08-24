package amlet.crm.service;

import java.util.List;
import java.util.Optional;

import amlet.cache.HwCache;
import amlet.core.repository.DataTemplate;
import amlet.core.sessionmanager.TransactionManager;
import amlet.crm.model.Client;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbServiceClientImpl implements DBServiceClient {

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> cache;

    public DbServiceClientImpl(TransactionManager transactionManager,
                               DataTemplate<Client> clientDataTemplate,
                               HwCache<String, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            if (client.getId() == null) {
                clientDataTemplate.insert(session, client);
                log.info("created client: {}", client);
                cache.put(String.valueOf(client.getId()), client);
                return client;
            }
            clientDataTemplate.update(session, client);
            log.info("updated client: {}", client);
            cache.put(String.valueOf(client.getId()), client);
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInTransaction(session -> {
            var clientOptional = getWithCache(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    private Optional<Client> getWithCache(Session session, long id) {
        String key = String.valueOf(id);
        Client client = cache.get(key);
        if (client != null) {
            return Optional.of(client);
        }
        var clientOptional = clientDataTemplate.findById(session, id);
        if (clientOptional.isPresent()) {
            client = clientOptional.get();
            cache.put(key, client);
        }
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }

}
