package ru.amlet;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.amlet.core.repository.DataTemplateHibernate;
import ru.amlet.core.repository.HibernateUtils;
import ru.amlet.core.sessionmanager.TransactionManagerHibernate;
import ru.amlet.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.amlet.crm.model.AddressDataSet;
import ru.amlet.crm.model.Client;
import ru.amlet.crm.model.PhoneDataSet;
import ru.amlet.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, AddressDataSet.class, PhoneDataSet.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        Client client1 = new Client("dbServiceFirst");
        client1.setAddress(new AddressDataSet("someAddress"));
        client1.setPhones(getPhoneDataSet(client1));
        dbServiceClient.saveClient(client1);

        var client2 = new Client("dbServiceSecond");
        client2.setAddress(new AddressDataSet("someElseAddress"));
        client2.setPhones(getPhoneDataSet(client2));
        dbServiceClient.saveClient(client2);

        var clientSecondSelected = dbServiceClient.getClient(client2.getId())
            .orElseThrow(() -> new RuntimeException("Client not found, id:" + client2.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);
        ///
        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated", clientSecondSelected.getAddress(), clientSecondSelected.getPhones()));
        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
            .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }

    private static List<PhoneDataSet> getPhoneDataSet(Client client) {
        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet(null, "8-800-1234567", client));
        phones.add(new PhoneDataSet(null, "8-800-7654321", client));
        return phones;
    }
}
