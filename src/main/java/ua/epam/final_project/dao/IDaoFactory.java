package ua.epam.final_project.dao;

import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.IncorrectPropertyException;

import java.sql.Connection;

public interface IDaoFactory {
    void getConnection();

    void releaseConnection();

    void beginTransaction() throws DataBaseConnectionException;

    void commitTransaction() throws DataBaseConnectionException;

    void rollbackTransaction() throws DataBaseConnectionException;

    IUserDao getUserDao();

    IEditionDao getEditionDao();

    IUserEditionDao getUserEditionDao();

    IGenreDao getGenreDao();

    IRoleDao getRoleDao();
}
