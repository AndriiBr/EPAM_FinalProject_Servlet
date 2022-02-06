package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLConnectionPool implements IConnectionPool {
    private final String url;
    private final String user;
    private final String password;

    private final List<Connection> connectionPool;
    private final List<Connection> connectionsInUse = new ArrayList<>();
    private static final int CONNECTION_POOL_SIZE = 5;

    private SQLConnectionPool(String url, String user, String password, List<Connection> pool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = pool;
    }

    public static SQLConnectionPool create(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(CONNECTION_POOL_SIZE);
        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new SQLConnectionPool(url, user, password, pool);
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            connectionPool.add(createConnection(url, user, password));
        }

        return connectionPool.remove(connectionPool.size()-1);
    }

    @Override
    public synchronized boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return connectionsInUse.remove(connection);
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
