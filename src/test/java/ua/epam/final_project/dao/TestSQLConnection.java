package ua.epam.final_project.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestSQLConnection {
    private static final String SOURCE = "test_database_url.properties";
    private static String url;
    private static String user;
    private static String password;

    public static Connection getConnectionToTestDB() throws SQLException {
        if (createConnection()) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return connection;
        } else {
            throw new SQLException("Cannot connect to provided test DB");
        }
    }

    private static boolean createConnection() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(SOURCE)) {
            properties.load(resourceStream);

            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.user");
            password = properties.getProperty("jdbc.password");
            String driver = properties.getProperty("jdbc.driver");

            Class.forName(driver);
            return true;

        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }
}
