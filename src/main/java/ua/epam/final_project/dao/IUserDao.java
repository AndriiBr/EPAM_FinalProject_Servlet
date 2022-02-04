package ua.epam.final_project.dao;

import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.util.user.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {

    List<User> findAllUsers() throws SQLException;

    User findUserByLoginPassword(String login, String password) throws SQLException;

    User findUserByLogin(String login) throws SQLException;

    boolean insertUser(User user) throws SQLException;

    boolean updateUserBalance(User user, int money) throws SQLException;
}
