package ua.epam.final_project.dao;

public final class SQLConstant {

    private SQLConstant() {}

    //SQL requests for UserDAO
    public static final String SQL_GET_NUMBER_OF_USERS = "SELECT COUNT(*) AS rowcount FROM user";
    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_FIND_USERS_FROM_TO = "SELECT * FROM user LIMIT ? OFFSET ?";
    public static final String SQL_FIND_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE (login=?) AND (password=?)";
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE (login=?)";
    public static final String SQL_UPDATE_USER =
            "UPDATE user SET login = ?, password = ?, email = ?, name = ?, user_image = ?, balance = ?, user_role_id = ? WHERE id = ?";
    public static final String SQL_INSERT_USER =
            "INSERT INTO user (login, password, email, name, user_image, user_role_id) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE id = ?";

    //SQL requests for EditionDAO
    public static final String SQL_GET_NUMBER_OF_EDITIONS = "SELECT COUNT(*) AS rowcount FROM edition";
    public static final String SQL_GET_NUMBER_OF_EDITIONS_WITHOUT_USER_ALREADY_HAS =
            "SELECT COUNT(*) AS rowcount FROM edition WHERE id NOT IN (SELECT edition_id FROM user_edition WHERE user_id = ?)";
    public static final String SQL_GET_NUMBER_OF_EDITIONS_USER_ALREADY_HAS =
            "SELECT COUNT(*) AS rowcount FROM edition WHERE id IN (SELECT edition_id FROM user_edition WHERE user_id = ?)";
    public static final String SQL_FIND_ALL_EDITIONS = "SELECT * FROM edition";
    public static final String SQL_FIND_EDITIONS_ORDER_BY_FROM_TO = "SELECT * FROM edition ORDER BY ? LIMIT ? OFFSET ?";
    public static final String SQL_FIND_EDITIONS_FROM_TO_WITHOUT_USER_ALREADY_HAS =
            "SELECT * FROM edition WHERE id NOT IN (SELECT edition_id FROM user_edition WHERE user_id = ?) ORDER BY ? LIMIT ? offset ?";
    public static final String SQL_FIND_EDITIONS_FROM_TO_USER_ALREADY_HAS =
            "SELECT * FROM edition WHERE id IN (SELECT edition_id FROM user_edition WHERE user_id = ?) ORDER BY ? LIMIT ? offset ?";
    public static final String SQL_FIND_EDITION_BY_ID = "SELECT * FROM edition WHERE id = ?";
    public static final String SQL_INSERT_EDITION =
            "INSERT INTO edition (titleEn, titleUa, title_image, genre_id, price) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_UPDATE_EDITION =
            "UPDATE edition SET titleEn = ?, titleUa = ?, title_image = ?, genre_id = ?, price = ? WHERE id = ?;";
    public static final String SQL_DELETE_EDITION = "DELETE FROM edition WHERE id = ?";

    //SQL requests for UserEditionDAO
    public static final String SQL_GET_NUMBER_OF_USER_EDITIONS = "SELECT COUNT(*) AS rowcount FROM user_edition";
    public static final String SQL_FIND_ALL_USER_EDITIONS = "SELECT * FROM user_edition";
    public static final String SQL_FIND_ALL_USER_EDITION_BY_USER_ID = "SELECT * FROM user_edition WHERE user_id = ?";
    public static final String SQL_FIND_ALL_USER_EDITION_BY_USER_ID_EDITION_ID =
            "SELECT * FROM user_edition WHERE user_id = ? AND edition_id = ?";
    public static final String SQL_INSERT_USER_EDITION = "INSERT INTO user_edition (user_id, edition_id) VALUES (?, ?)";
    public static final String SQL_DELETE_USER_EDITION = "DELETE FROM user_edition WHERE user_id = ? AND edition_id = ?";
    public static final String SQL_DELETE_USER_EDITION_BY_EDITION = "DELETE FROM user_edition WHERE edition_id = ?;";
    public static final String SQL_DELETE_USER_EDITION_BY_USER = "DELETE FROM user_edition WHERE user_id = ?;";

    //SQL requests for GenreDAO
    public static final String SQL_GET_NUMBER_OF_GENRES = "SELECT COUNT(*) AS rowcount FROM genre";
    public static final String SQL_FIND_ALL_GENRES = "SELECT * FROM genre";
    public static final String SQL_FIND_GENRE_BY_ID = "SELECT * FROM genre WHERE id = ?";

    //SQL requests for RoleDAO
    public static final String SQL_GET_NUMBER_OF_ROLES = "SELECT COUNT(*) AS rowcount FROM role";
    public static final String SQL_FIND_ALL_ROLES = "SELECT * FROM role";
    public static final String SQL_FIND_ROLE_BY_ID = "SELECT * FROM role WHERE id = ?";
}
