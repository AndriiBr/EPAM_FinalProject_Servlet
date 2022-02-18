package ua.epam.final_project.dao.implementation;

import ua.epam.final_project.dao.IConnectionPool;
import ua.epam.final_project.exception.IncorrectPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLConnectionPool implements IConnectionPool {

    private static final String SOURCE = "database_url.properties";

    private static String url;
    private static String user;
    private static String password;
    private static IConnectionPool connectionPool;

    private final List<Connection> connectionsAvailable;
    private final List<Connection> connectionsInUse = new ArrayList<>();
    private static final int CONNECTION_POOL_SIZE = 10;


    /**
     * Hidden private constructor
     */
    private SQLConnectionPool(List<Connection> pool) {
        connectionsAvailable = pool;
    }

    /**
     *
     * @return connectionPool instance
     */
    public static synchronized IConnectionPool getInstance() throws IncorrectPropertyException {
        if (connectionPool == null) {
            connectionPool = create();
        }
        return connectionPool;
    }

    private static SQLConnectionPool create() throws IncorrectPropertyException {
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
            throw new IncorrectPropertyException();
        }

        List<Connection> pool = new ArrayList<>(CONNECTION_POOL_SIZE);
        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new SQLConnectionPool(pool);
    }

    @Override
    public synchronized Connection getConnection() {
        if (connectionsAvailable.isEmpty()) {
            connectionsAvailable.add(createConnection(url, user, password));
        }

        return connectionsAvailable.remove(connectionsAvailable.size() - 1);
    }

    @Override
    public synchronized boolean releaseConnection(Connection connection) {
        connectionsAvailable.add(connection);
        return connectionsInUse.remove(connection);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
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
