package ua.epam.final_project.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLConnectionPool implements IConnectionPool {

    private static final Logger logger = LogManager.getLogger(SQLConnectionPool.class);

    private static final String SOURCE = "database_url.properties";

    private static String url;
    private static String user;
    private static String password;
    private static IConnectionPool connectionPool;

    private final List<Connection> availableConnections;
    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int CONNECTION_POOL_SIZE = 5;


    /**
     * Hidden private constructor
     */
    private SQLConnectionPool(List<Connection> pool) {
        availableConnections = pool;
    }

    /**
     * Return instance of connection pool
     * @return connectionPool instance
     */
    public static synchronized IConnectionPool getInstance()  {
        if (connectionPool == null) {
            connectionPool = create();
        }
        return connectionPool;
    }

    private static SQLConnectionPool create()  {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(SOURCE)) {
            properties.load(resourceStream);

            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.user");
            password = properties.getProperty("jdbc.password");
            String driver = properties.getProperty("jdbc.driver");

            Class.forName(driver);

        } catch (IOException | ClassNotFoundException e) {
            logger.error(e);
        }

        List<Connection> pool = new ArrayList<>();
//        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
//            pool.add(createConnection(url, user, password));
//        }
        return new SQLConnectionPool(pool);
    }

    @Override
    public synchronized Connection getConnection() {
        if (availableConnections.isEmpty()) {
            availableConnections.add(createConnection(url, user, password));
        }

        return availableConnections.remove(availableConnections.size() - 1);
    }

    @Override
    public synchronized boolean releaseConnection(Connection connection) {
        availableConnections.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
