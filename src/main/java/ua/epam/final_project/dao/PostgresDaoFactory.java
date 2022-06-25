package ua.epam.final_project.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.implementation.*;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.IncorrectPropertyException;

import java.sql.*;

public class PostgresDaoFactory implements IDaoFactory {

    private static final Logger logger = LogManager.getLogger(PostgresDaoFactory.class);

    private Connection connection;

    public PostgresDaoFactory() throws IncorrectPropertyException {}

    @Override
    public void getConnection() {
        if (this.connection == null) {
            connection = SQLConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public void releaseConnection() {
        if (this.connection != null) {
            SQLConnectionPool.getInstance().releaseConnection(this.connection);
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
            logger.error(e);
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
        return new UserDaoImpl(this.connection);
    }

    @Override
    public IEditionDao getEditionDao() {
        return new EditionDaoImpl(this.connection);
    }

    @Override
    public IUserEditionDao getUserEditionDao() {
        return new UserEditionDaoImpl(this.connection);
    }

    @Override
    public IGenreDao getGenreDao() {
        return new GenreDaoImpl(this.connection);
    }

    @Override
    public IRoleDao getRoleDao() {
        return new RoleDaoImpl(this.connection);
    }

}
