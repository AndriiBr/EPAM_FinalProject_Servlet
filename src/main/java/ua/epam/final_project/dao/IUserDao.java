package ua.epam.final_project.dao;

import ua.epam.final_project.util.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {


    Integer getNumberOfUsers() throws SQLException;

    List<User> findAllUsers() throws SQLException;

    List<User> findAllUsersFromTo(int recordsPerPage, int page) throws SQLException;

    User findUserByLoginPassword(String login, String password) throws SQLException;

    User findUserByLogin(String login) throws SQLException;

    boolean insertUser(User user) throws SQLException;

    boolean updateUserBalance(User user, int money) throws SQLException;

    boolean deleteUserByLogin(String login) throws SQLException;
}
