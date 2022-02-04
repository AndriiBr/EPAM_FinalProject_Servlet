package ua.epam.final_project.dao;

public final class SQLConstant {

    private SQLConstant() {}

    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_FIND_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE (login=?) AND (password=?)";
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE (login=?)";
    public static final String SQL_FIND_ALL_EDITIONS = "SELECT * FROM edition";
    public static final String SQL_FIND_EDITION_BY_TITLE = "SELECT * FROM edition WHERE (title=?)";

    public static final String SQL_INSERT_USER =
            "INSERT INTO user (login, password, email, name, user_role_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_EDITION = "INSERT INTO edition (title, title_image, price) VALUES (?, ?, ?)";

    public static final String SQL_UPDATE_USER_BALANCE = "UPDATE user SET balance = ? where id = ?";

    public static final String SQL_DELETE_EDITION_BY_TITLE = "DELETE FROM edition WHERE (title=?)";

}
