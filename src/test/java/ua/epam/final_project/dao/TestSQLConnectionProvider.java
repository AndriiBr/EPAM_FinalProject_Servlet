package ua.epam.final_project.dao;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TestSQLConnectionProvider {
    private static final String SQL_SCRIPT_SOURCE = "src/test/java/ua/epam/final_project/dao/test_DB_SQL_script.sql";

    private static final String SOURCE = "test_database_url.properties";
    private static String url;
    private static String user;
    private static String password;

    public static Connection getConnectionToTestDB() throws SQLException {
        if (createConnection()) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                executeScript(connection);
                return connection;
            } catch (SQLException e) {
                throw new SQLException("Cannot connect to provided test DB");
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Cannot find sql script for test DB");
            }
        } else {
            throw new SQLException("Cannot connect to provided test DB");
        }
    }

    public static void resetDb (Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP ALL OBJECTS DELETE FILES");
        connection.close();
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

    private static void executeScript(Connection connection) throws FileNotFoundException {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        //disabling the output of the script in the console
        scriptRunner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader(SQL_SCRIPT_SOURCE));
        scriptRunner.runScript(reader);
    }
}
