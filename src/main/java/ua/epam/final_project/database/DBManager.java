package ua.epam.final_project.database;

import ua.epam.final_project.util.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static ua.epam.final_project.database.SQLConstants.*;

public class DBManager {
    private static final Logger logger = Logger.getAnonymousLogger();
    private static final String URL;
    private static DBManager dbManager;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String resourceName = "database_url.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL = properties.getProperty("connection.url");
    }

    /** Hided private constructor */
    private DBManager() {}

    /** Get instance of Database Manager */
    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    /** Get list of all users with their attributes */
    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection con = getConnection()) {
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(SQL_FIND_ALL_USERS)) {
                while (rs.next()) {
                    users.add(extractUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return users;
    }

    /** Insert new user into database */
    public boolean insertUser(User user) throws SQLException {
        try (Connection con = getConnection()) {
            if (getUser(user.getLogin(), user.getPassword()) != null) {
                return false;
            }

            try (PreparedStatement statement = con.prepareStatement(SQL_INSERT_USER)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getName());
                statement.setString(5, user.getRole());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /** Get single user from database */
    public User getUser(String login, String password) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_FIND_USER_BY_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return null;
    }


    /** UTILITY METHOD
     * create User entity according to data from database */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setRole(rs.getString("user_role_id"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return user;
    }

    /** UTILITY METHOD
     * return connection to database */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
