package ua.epam.final_project.dao;

import java.sql.Connection;


public interface IConnectionPool {

    Connection getConnection();
    boolean releaseConnection(Connection connection);
}
