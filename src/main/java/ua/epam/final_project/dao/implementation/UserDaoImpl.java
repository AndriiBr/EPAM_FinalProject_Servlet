package ua.epam.final_project.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.entity.User;

import static ua.epam.final_project.dao.SQLConstant.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements IUserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfUsers() throws DataNotFoundException {
        int numberOfRows = 0;

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_USERS);

            if (rs.next()) {
                numberOfRows = rs.getInt("rowcount");
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return numberOfRows;
    }

    public List<User> findAllUsers() throws DataNotFoundException {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_USERS);

            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return users;
    }

    @Override
    public List<User> findAllUsersFromTo(int recordsPerPage, int page) throws DataNotFoundException {
        List<User> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_FROM_TO)) {
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, (page - 1) * recordsPerPage);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(extractUser(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return list;
    }

    public User findUserByLoginPassword(String login, String password) throws DataNotFoundException {
        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_PASSWORD);) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return user;
    }

    public User findUserByLogin(String login) throws DataNotFoundException {
        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = extractUser(rs);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return user;
    }

    public boolean insertUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            if (findUserByLogin(user.getLogin()) != null) {
                return false;
            }
            prepareUserStatement(user, statement);
            statement.executeUpdate();
        } catch (SQLException | DataNotFoundException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
            prepareUserStatement(user, statement);
            statement.setInt(8, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
            User userToBeDeleted = findUserByLogin(user.getLogin());
            statement.setInt(1, userToBeDeleted.getId());
            statement.executeUpdate();
        } catch (SQLException | DataNotFoundException e) {
            logger.error(e);
            return false;
        }

        return true;
    }

    /**
     * UTILITY METHOD
     * create preparedStatement for user insert/update operations
     */
    private void prepareUserStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getName());
        statement.setString(5, user.getUserImage());
        statement.setInt(6, user.getBalance());
        statement.setInt(7, Integer.parseInt(user.getRole()));
    }

    /**
     * UTILITY METHOD
     * create User entity according to data from database
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("username"));
        user.setPassword(rs.getString("pass"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("first_name"));
        user.setUserImage(rs.getString("user_image"));
        user.setBalance(Integer.parseInt(rs.getString("balance")));
        user.setRole(rs.getString("user_role_id"));

        return user;
    }
}