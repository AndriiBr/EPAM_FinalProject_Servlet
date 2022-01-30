package ua.epam.final_project.database;

import ua.epam.final_project.util.user.User;
import ua.epam.final_project.util.edition.Edition;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static ua.epam.final_project.database.SQLConstant.*;

public class DBManager {
    private static final Logger logger = Logger.getAnonymousLogger();
    private static final String URL;
    private static DBManager dbManager;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String resourceName = "database_url.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL = properties.getProperty("connection.url");
    }

    /**
     * Hided private constructor
     */
    private DBManager() {
    }

    /**
     * Get instance of Database Manager
     */
    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    /**
     * Get list of all users with their attributes
     */
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

    /**
     * Get single user from database by Login/Password
     */
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

    /**
     * Get single user from database by Login
     */
    public User getUserByLogin(String login) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);

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

    /**
     * Insert new user into database
     */
    public void insertUser(User user) throws SQLException {
        try (Connection con = getConnection()) {
            if (getUser(user.getLogin(), user.getPassword()) != null) {
                return;
            }

            try (PreparedStatement statement = con.prepareStatement(SQL_INSERT_USER)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getName());
                statement.setString(5, user.getRole());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Get list of all editions with their attributes
     */
    public List<Edition> findAllEditions() throws SQLException {
        List<Edition> editions = new ArrayList<>();

        try (Connection con = getConnection()) {
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(SQL_FIND_ALL_EDITIONS)) {
                while (rs.next()) {
                    editions.add(extractEdition(rs));
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return editions;
    }

    /**
     * Get single edition from database by Title
     */
    public Edition getEditionByTitle(String title) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_FIND_EDITION_BY_TITLE)) {
            statement.setString(1, title);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractEdition(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return null;
    }


    /**
     * Insert new position into table "edition"
     */
    public void insertNewEdition(String title, String imagePath, String price) throws SQLException {
        String newImagePath = "";

        String imageFolderName = "image_folder.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(imageFolderName)) {
            prop.load(resourceStream);

            if (imagePath.equals("no image")) {
                newImagePath = imagePath;
            } else {
                newImagePath = prop.getProperty("title_image_folder") + imagePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT_EDITION)) {
//            con.setAutoCommit(false);

            statement.setString(1, title);
            //insert path to image into DB
            statement.setString(2, newImagePath);
            statement.setString(3, String.valueOf(price));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void deleteEditionByTitle(String title) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_DELETE_EDITION_BY_TITLE)) {

            statement.setString(1, title);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }

    }


    /**
     * UTILITY METHOD
     * create User entity according to data from database
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setBalance(Integer.parseInt(rs.getString("balance")));
            user.setRole(rs.getString("user_role_id"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return user;
    }

    /**
     * UTILITY METHOD
     * create Edition entity according to data from database
     */
    private Edition extractEdition(ResultSet rs) throws SQLException {
        Edition edition = new Edition();
        try {
            edition.setId(rs.getInt("id"));
            edition.setTitle(rs.getString("title"));
            edition.setImagePath(rs.getString("title_image"));
            edition.setPrice(rs.getInt("price"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return edition;
    }

    /**
     * UTILITY METHOD
     * return connection to database
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
