package ua.epam.final_project.dao;

import ua.epam.final_project.dao.realisation.*;
import ua.epam.final_project.exception.DataBaseConnectionException;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class MySQLDaoFactory extends DaoFactory {
//    private static final Logger logger = Logger.getAnonymousLogger();
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private static final String DRIVER;

    private static MySQLDaoFactory mySQLDaoFactory;
    private static IConnectionPool connectionPool;

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
            connectionPool = SQLConnectionPool.create(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hided private constructor
     */
    private MySQLDaoFactory() {
    }

    public IUserDao getUserDao() {
        return new UserDao(connectionPool);
    }

    public IEditionDao getEditionDao() {
        return new EditionDao(connectionPool);
    }

    @Override
    public IGenreDao getGenreDao() {
        return new GenreDao(connectionPool);
    }

    @Override
    public IEditionGenreDao getEditionGenreDao() {
        return new EditionGenreDao(connectionPool);
    }

    @Override
    public void openConnection() throws DataBaseConnectionException {

    }

    @Override
    public void closeConnection() throws DataBaseConnectionException {

    }

    @Override
    public void beginTransaction() throws DataBaseConnectionException {

    }

    @Override
    public void commitTransaction() throws DataBaseConnectionException {

    }

    @Override
    public void rollbackTransaction() throws DataBaseConnectionException {

    }

    /**
     * Get instance of Database Manager
     */
    protected static synchronized MySQLDaoFactory getInstance() {
        if (mySQLDaoFactory == null) {
            mySQLDaoFactory = new MySQLDaoFactory();
        }
        return mySQLDaoFactory;
    }
}
