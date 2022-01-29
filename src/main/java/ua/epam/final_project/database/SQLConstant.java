package ua.epam.final_project.database;

public final class SQLConstant {

    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_FIND_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE (login=?) AND (password=?)";
    public static final String SQL_INSERT_USER =
            "INSERT INTO user (login, password, email, name, user_role_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_EDITION = "INSERT INTO edition (title, title_image, price) VALUES (?, ?, ?)";
    public static final String SQL_FIND_ALL_EDITIONS = "SELECT * FROM edition";

    private SQLConstant() {
    }
}
