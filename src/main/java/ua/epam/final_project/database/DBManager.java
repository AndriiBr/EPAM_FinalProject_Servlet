package ua.epam.final_project.database;

import ua.epam.final_project.util.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private static final Logger logger = Logger.getAnonymousLogger();
    private static final String URL;

    static {
        String resourceName = "database_url.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Properties properties = new Properties();
//        try {
//            properties.load(new FileInputStream("database_url.properties"));
//        } catch (IOException e) {
//            logger.log(Level.SEVERE, e.toString());
//        }
        URL = properties.getProperty("connection.url");
    }

    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?)";
    private static final String SQL_INSERT_USERS_TEAMS = "INSERT INTO users_teams VALUES (?, ?)";
    private static final String SQL_USERS_TEAMS_PAIR_CHECK = "SELECT COUNT(*) FROM users_teams WHERE user_id = ? AND team_id = ?";
    private static final String SQL_SELECT_ID_FROM_USERS_TEAM = "SELECT team_id FROM users_teams WHERE user_id = ?";
    private static final String SQL_SELECT_NAME_FROM_TEAMS_WHERE_ID = "SELECT name FROM teams WHERE id = ?";
    private static final String SQL_UPDATE_TEAMS_WHERE_NAME = "UPDATE teams SET name = ? WHERE id = ?";
    private static final String SQL_DELETE_TEAM_WHERE_ID = "DELETE FROM teams WHERE id = ?";

    private static DBManager dbManager;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    // getting list of all users with attributes
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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

}
