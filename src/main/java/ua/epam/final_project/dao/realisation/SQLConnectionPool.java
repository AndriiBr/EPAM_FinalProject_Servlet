package ua.epam.final_project.dao.realisation;

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
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private static final String DRIVER;
    private static IConnectionPool connectionPool;

    private final List<Connection> connectionsAvailable;
    private final List<Connection> connectionsInUse = new ArrayList<>();
    private static final int CONNECTION_POOL_SIZE = 10;

    static {
        String resourceName = "database_url.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL = properties.getProperty("jdbc.url");
        USER = properties.getProperty("jdbc.user");
        PASSWORD = properties.getProperty("jdbc.password");
        DRIVER = properties.getProperty("jdbc.driver");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hidden private constructor
     */
    private SQLConnectionPool(List<Connection> pool) {
        connectionsAvailable = pool;
        System.out.println("<<<< SQLConnectionPool was created >>>>");
    }

    /**
     *
     * @return connectionPool instance
     */
    public static synchronized IConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = create(URL, USER, PASSWORD);
        }
        return connectionPool;
    }

    public static SQLConnectionPool create(String url, String user, String password) {
        List<Connection> pool = new ArrayList<>(CONNECTION_POOL_SIZE);
        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new SQLConnectionPool(pool);
    }

    @Override
    public synchronized Connection getConnection() {
        if (connectionsAvailable.isEmpty()) {
            connectionsAvailable.add(createConnection(URL, USER, PASSWORD));
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
        return URL;
    }

    @Override
    public String getUser() {
        return USER;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
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
