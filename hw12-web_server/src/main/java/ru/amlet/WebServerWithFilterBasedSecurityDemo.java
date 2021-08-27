package ru.amlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import ru.amlet.dao.HibernateUserDao;
import ru.amlet.dao.UserDao;
import ru.amlet.dbmigration.MigrationsExecutorFlyway;
import ru.amlet.model.Client;
import ru.amlet.repository.DataTemplateHibernate;
import ru.amlet.repository.HibernateUtils;
import ru.amlet.server.UsersWebServer;
import ru.amlet.server.UsersWebServerImpl;
import ru.amlet.services.TemplateProcessor;
import ru.amlet.services.TemplateProcessorImpl;
import ru.amlet.services.UserAuthService;
import ru.amlet.services.UserAuthServiceImpl;
import ru.amlet.sessionmanager.TransactionManagerHibernate;

public class WebServerWithFilterBasedSecurityDemo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        UserDao userDao = new HibernateUserDao(transactionManager, clientTemplate);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
            userDao, gson, templateProcessor, authService);

        usersWebServer.start();
        usersWebServer.join();
    }
}
