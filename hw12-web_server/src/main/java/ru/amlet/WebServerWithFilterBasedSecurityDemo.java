package ru.amlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import ru.amlet.services.DbServiceClientImpl;
import ru.amlet.services.DbServiceClient;
import ru.amlet.dbmigration.MigrationsExecutorFlyway;
import ru.amlet.model.Client;
import ru.amlet.repository.DataTemplateHibernate;
import ru.amlet.repository.HibernateUtils;
import ru.amlet.server.ClientsWebServer;
import ru.amlet.server.ClientsWebServerImpl;
import ru.amlet.services.TemplateProcessor;
import ru.amlet.services.TemplateProcessorImpl;
import ru.amlet.services.ClientAuthService;
import ru.amlet.services.ClientAuthServiceImpl;
import ru.amlet.sessionmanager.TransactionManagerHibernate;

public class WebServerWithFilterBasedSecurityDemo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbClientName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbClientName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        DbServiceClient dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        ClientAuthService authService = new ClientAuthServiceImpl(dbServiceClient);

        ClientsWebServer clientsWebServer = new ClientsWebServerImpl(WEB_SERVER_PORT,
            dbServiceClient, gson, templateProcessor, authService);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
