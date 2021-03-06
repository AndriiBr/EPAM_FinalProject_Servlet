package ua.epam.final_project.service;

import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownUserException;

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
    List<UserDto> findAllUsers() throws UnknownUserException;

    /**
     * Get limited number of users from DB
     * @param recordsPerPage to define end element
     * @param page to define start element
     * @return list of users
     * @throws UnknownUserException if nothing return from request to DB.
     */
    List<UserDto> findAllUsersFromTo(int recordsPerPage, int page) throws UnknownUserException;

    /**
     * Get a user with a username and password match
     * @param login user login
     * @param password password
     * @return userDto entity
     * @throws UnknownUserException if nothing return from request to DB.
     */
    UserDto findUserByLoginPassword(String login, String password) throws UnknownUserException;

    /**
     * Get a user by login match
     * @param login user login
     * @return user entity
     */
    UserDto findUserByLogin(String login) throws UnknownUserException;

    /**
     * Get a user by login match
     * @param id user id
     * @return user entity
     */
    UserDto findUserById(int id) throws UnknownUserException;

    /**
     * Insert new user into DB
     * @param userDto userDto entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean insertUser(UserDto userDto);

    /**
     * Update user in DB
     * @param userDto userDto entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean updateUser(UserDto userDto);

    /**
     * Delete user from DB
     * @param userDto userDto entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean deleteUser(UserDto userDto);
}
