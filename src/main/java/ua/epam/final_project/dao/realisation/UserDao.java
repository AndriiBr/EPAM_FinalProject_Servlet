package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IConnectionPool;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.util.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.epam.final_project.dao.SQLConstant.*;
import static ua.epam.final_project.dao.SQLConstant.SQL_INSERT_USER;

public class UserDao implements IUserDao {
    private final IConnectionPool connectionPool;

    public UserDao(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Integer getNumberOfUsers() throws SQLException {
        int numberOfUsers = 0;
        Connection con = null;
        Statement statement =null;

        try {
            con = connectionPool.getConnection();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_USERS);

            if (rs.next()) {
                numberOfUsers = rs.getInt("rowcount");
            }

        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return numberOfUsers;
    }

    /**
     * Get list of all users with their attributes
     */
    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection con = null;
        Statement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_USERS);

            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return users;
    }


    @Override
    public List<User> findAllUsersFromTo(int recordsPerPage, int page) throws SQLException {
        List<User> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_FIND_USERS_FROM_TO);
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, (page - 1) * recordsPerPage);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(extractUser(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return list;
    }

    /**
     * Get single user from database by Login/Password
     */
    public User findUserByLoginPassword(String login, String password) throws SQLException {
        User user = null;
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_FIND_USER_BY_LOGIN_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return user;
    }

    /**
     * Get single user from database by Login
     */
    public User findUserByLogin(String login) throws  SQLException {
        User user = null;
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return user;
    }

    /**
     * Insert new user into database
     */
    public boolean insertUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = connectionPool.getConnection();
            if (findUserByLogin(user.getLogin()) != null) {
                return false;
            }

            statement = con.prepareStatement(SQL_INSERT_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getName());
            statement.setString(5, user.getRole());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return true;
    }

    /**
     * Update user balance value
     */
    public boolean updateUserBalance(User user, int money) throws SQLException {
        final int userID = user.getId();
        int balance = user.getBalance();
        balance += money;

        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = connectionPool.getConnection();

            statement = con.prepareStatement(SQL_UPDATE_USER_BALANCE);
            statement.setInt(1, balance);
            statement.setInt(2, userID);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return true;
    }

    /**
     * Delete user
     */
    @Override
    public boolean deleteUserByLogin(String login) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_DELETE_USER_BY_LOGIN);
            statement.setString(1, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }

        return true;
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
     * Release connection
     */
    private void closeAllResources(Connection connection) {
        if (connection != null) {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * UTILITY METHOD
     * close statement
     */
    private void closeAllResources(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * UTILITY METHOD
     * close prepared statement
     */
    private void closeAllResources(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }
}
