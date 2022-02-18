package ua.epam.final_project.dao;

import ua.epam.final_project.dao.implementation.*;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.IncorrectPropertyException;

import java.sql.*;

public class MySQLDaoFactory extends DaoFactory {


    private final IConnectionPool connectionPool;
    private Connection connection;

    public MySQLDaoFactory() throws IncorrectPropertyException {
        connectionPool = SQLConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }

    @Override
    public void getConnection() {
        if (this.connection == null) {
            connection = connectionPool.getConnection();
        }
    }

    @Override
    public void releaseConnection() {
        if (this.connection != null) {
            connectionPool.releaseConnection(this.connection);
            this.connection = null;
        }
    }

    @Override
    public void beginTransaction() throws DataBaseConnectionException {
        try {
            getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataBaseConnectionException();
        }
    }

    @Override
    public void commitTransaction() throws DataBaseConnectionException {
        try {
            connection.commit();
            releaseConnection();
        } catch (SQLException e) {
            throw new DataBaseConnectionException();
        }
    }

    @Override
    public void rollbackTransaction() throws DataBaseConnectionException {
        try {
            connection.rollback();
            releaseConnection();
        } catch (SQLException e) {
            throw new DataBaseConnectionException();
        }
    }

    @Override
    public IUserDao getUserDao() {
        return new UserDao(this.connection);
    }

    @Override
    public IEditionDao getEditionDao() {
        return new EditionDao(this.connection);
    }

    @Override
    public IUserEditionDao getUserEditionDao() {
        return new UserEditionDao(this.connection);
    }

    @Override
    public IGenreDao getGenreDao() {
        return new GenreDao(this.connection);
    }

    @Override
    public IRoleDao getRoleDao() {
        return new RoleDao(this.connection);
    }


}
