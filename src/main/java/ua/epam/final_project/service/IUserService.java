package ua.epam.final_project.service;

import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.util.entity.User;

import java.util.List;

public interface IUserService {

    /**
     * Get number of rows in 'user' table from DB
     * @return number of rows
     * @throws UnknownUserException if nothing return from request to DB.
     */
    Integer getNumberOfUsers() throws UnknownUserException;

    /**
     * Get list of all users from DB
     * @return list of users
     * @throws UnknownUserException if nothing return from request to DB.
     */
    List<User> findAllUsers() throws UnknownUserException;

    /**
     * Get limited number of users from DB
     * @param recordsPerPage to define end element
     * @param page to define start element
     * @return list of users
     * @throws UnknownUserException if nothing return from request to DB.
     */
    List<User> findAllUsersFromTo(int recordsPerPage, int page) throws UnknownUserException;

    /**
     * Get a user with a username and password match
     * @param login user login
     * @param password password
     * @return user entity
     * @throws UnknownUserException if nothing return from request to DB.
     */
    User findUserByLoginPassword(String login, String password) throws UnknownUserException;

    /**
     * Get a user by login match
     * @param login user login
     * @return user entity
     */
    User findUserByLogin(String login) throws UnknownUserException;

    /**
     * Insert new user into DB
     * @param user user entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean insertUser(User user);

    /**
     * Update user in DB
     * @param user user entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean updateUser(User user);

    /**
     * Delete user from DB
     * @param user user entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean deleteUser(User user);
}
