package ua.epam.final_project.database;

public final class SQLConstant {

    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_FIND_USER_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE (login=?) AND (password=?)";
    public static final String SQL_INSERT_USER =
            "INSERT INTO user (login, password, email, name, user_role_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_USERS_TEAMS = "INSERT INTO users_teams VALUES (?, ?)";
    public static final String SQL_USERS_TEAMS_PAIR_CHECK = "SELECT COUNT(*) FROM users_teams WHERE user_id = ? AND team_id = ?";
    public static final String SQL_SELECT_ID_FROM_USERS_TEAM = "SELECT team_id FROM users_teams WHERE user_id = ?";
    public static final String SQL_SELECT_NAME_FROM_TEAMS_WHERE_ID = "SELECT name FROM teams WHERE id = ?";
    public static final String SQL_UPDATE_TEAMS_WHERE_NAME = "UPDATE teams SET name = ? WHERE id = ?";
    public static final String SQL_DELETE_TEAM_WHERE_ID = "DELETE FROM teams WHERE id = ?";

    private SQLConstant() {
    }
}
