package ua.epam.final_project.dao;

import ua.epam.final_project.dao.realisation.*;
import ua.epam.final_project.exception.DataBaseConnectionException;

import java.sql.*;

public class MySQLDaoFactory extends DaoFactory {
//    private static final Logger logger = Logger.getAnonymousLogger();

    private final IConnectionPool connectionPool;
    private final Connection connection;

    public MySQLDaoFactory() {
        connectionPool = SQLConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }

    private void releaseConnection() {
        connectionPool.releaseConnection(this.connection);
    }

    @Override
    public void beginTransaction() throws DataBaseConnectionException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commitTransaction() throws DataBaseConnectionException {
        try {
            connection.commit();
            releaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollbackTransaction() throws DataBaseConnectionException {
        try {
            connection.rollback();
            releaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IUserDao getUserDao() {
        return new UserDao(this.connection);
    }

    public IEditionDao getEditionDao() {
        return new EditionDao(this.connection);
    }

    @Override
    public IGenreDao getGenreDao() {
        return new GenreDao(this.connection);
    }

}
