package ua.epam.final_project.dao;

public final class SQLConstant {

    private SQLConstant() {}

    public static final String SQL_GET_NUMBER_OF_USERS = "SELECT COUNT(*) AS rowcount FROM user";
    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_FIND_USERS_FROM_TO = "SELECT * FROM user LIMIT ? OFFSET ?";
    public static final String SQL_FIND_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE (login=?) AND (password=?)";
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE (login=?)";
    public static final String SQL_UPDATE_USER_BALANCE = "UPDATE user SET balance = ? where id = ?";
    public static final String SQL_INSERT_USER =
            "INSERT INTO user (login, password, email, name, user_image, user_role_id) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_USER_BY_LOGIN = "DELETE FROM user WHERE login = ?";

    public static final String SQL_GET_NUMBER_OF_EDITIONS = "SELECT COUNT(*) AS rowcount FROM edition";
    public static final String SQL_FIND_ALL_EDITIONS = "SELECT * FROM edition";
    public static final String SQL_FIND_EDITIONS_FROM_TO = "SELECT * FROM edition LIMIT ? OFFSET ?";
    public static final String SQL_FIND_EDITIONS_ORDER_BY_FROM_TO = "SELECT * FROM edition ORDER BY ? LIMIT ? OFFSET ?";
    public static final String SQL_FIND_EDITIONS_FROM_TO_WHERE = "SELECT * FROM edition ORDER BY ? LIMIT ? OFFSET ?";

    public static final String SQL_FIND_EDITION_BY_TITLE = "SELECT * FROM edition WHERE (title=?)";
    public static final String SQL_INSERT_EDITION =
            "INSERT INTO edition (title, title_image, genre_id, price) VALUES (?, ?, ?, ?)";
    public static final String SQL_DELETE_EDITION_BY_TITLE = "DELETE FROM edition WHERE (title=?)";

    public static final String SQL_GET_NUMBER_OF_GENRES = "SELECT COUNT(*) AS rowcount FROM genre";
    public static final String SQL_FIND_ALL_GENRES = "SELECT * FROM genre";
    public static final String SQL_FIND_GENRE_WHERE = "SELECT * FROM genre WHERE name = ?";

    public static final String SQL_FIND_ALL_USER_EDITION = "SELECT * FROM user_edition";
    public static final String SQL_FIND_ALL_USER_EDITION_BY_USER_ID = "SELECT * FROM user_edition WHERE user_id = ?";
    public static final String SQL_FIND_ALL_USER_EDITION_BY_USER_ID_EDITION_ID =
            "SELECT * FROM user_edition WHERE user_id = ? AND edition_id = ?";
    public static final String SQL_INSERT_USER_EDITION = "INSERT INTO user_edition (user_id, edition_id) VALUES (?, ?)";

}
