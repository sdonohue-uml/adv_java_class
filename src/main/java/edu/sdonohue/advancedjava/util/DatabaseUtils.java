package edu.sdonohue.advancedjava.util;

import com.ibatis.common.jdbc.ScriptRunner;
import edu.sdonohue.advancedjava.service.userstocks.DatabaseUserStockService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that contains database related utility methods.
 */
public class DatabaseUtils {

    private static SessionFactory sessionFactory;
    private static Configuration configuration;

    // in a real program these values would be a configurable property and not hard coded.
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stocks";
//    public static final String initializationFile = "./src/main/sql/db_initialization.sql";

    //  Database credentials
    private static final String USER = "monty";
    private static final String PASS = "some_pass";

    public static Connection getConnection() throws DatabaseConnectionException{
        try {
//            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e)  {
           throw new  DatabaseConnectionException("Could not connect to database. " + e.getMessage(), e);
        }
    }

    /*
     * @return SessionFactory for use with database transactions
     */
    public static SessionFactory getSessionFactory() {

        // singleton pattern
        synchronized (DatabaseUserStockService.class) {
            if (sessionFactory == null) {

                Configuration configuration = getConfiguration();

                ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .buildServiceRegistry();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            }
        }
        return sessionFactory;
    }

    /**
     * A utility method that runs a db initialize script.
     * @param initializationScript    full path to the script to run to create the schema
     * @throws DatabaseInitializationException
     */
    public static void initializeDatabase(String initializationScript) throws DatabaseInitializationException {

        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new  FileInputStream(initializationScript);

            InputStreamReader reader = new InputStreamReader(inputStream);

            runner.runScript(reader);
            reader.close();
            connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException |IOException e) {
           throw new DatabaseInitializationException("Could not initialize db because of:"
                   + e.getMessage(),e);
        }
    }

    /**
     * Create a new or return an existing database configuration object.
     *
     * @return a Hibernate Configuration instance.
     */
    private static Configuration getConfiguration() {

        synchronized (edu.sdonohue.advancedjava.util.DatabaseUtils.class) {
            if (configuration == null) {
                configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
            }
        }
        return configuration;
    }
}
