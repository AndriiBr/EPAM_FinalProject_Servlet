package ua.epam.final_project.dao;

import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.entity.User;

import java.util.List;

public interface IUserDao {


    /**
     * Gets number of rows in 'user' table from DB
     * @return number of rows
     * @throws DataNotFoundException if nothing was found in Db
     */
    Integer getNumberOfUsers() throws DataNotFoundException;

    /**
     * Gets list of all users from DB
     * @return list of users
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<User> findAllUsers() throws DataNotFoundException;

    /**
     * Gets limited number of users from DB
     * @param recordsPerPage to define end element
     * @param page to define start element
     * @return list of users
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<User> findAllUsersFromTo(int recordsPerPage, int page) throws DataNotFoundException;

    /**
     * Gets a user with a username and password match
     * @param login user login
     * @param password password
     * @return user entity
     * @throws DataNotFoundException if nothing was found in Db
     */
    User findUserByLoginPassword(String login, String password) throws DataNotFoundException;

    /**
     * Gets a user by login match
     * @param login user login
     * @return user entity
     * @throws DataNotFoundException if nothing was found in Db
     */
    User findUserByLogin(String login) throws DataNotFoundException;

    /**
     * Gets a user by login match
     * @param id user id
     * @return user entity
     * @throws DataNotFoundException if nothing was found in Db
     */
    User findUserById(int id) throws DataNotFoundException;

    /**
     * Inserts new user into DB
     * @param user user entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean insertUser(User user);

    /**
     * Updates user in DB
     * @param user user entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean updateUser(User user);

    /**
     * Deletes user from DB
     * @param user user entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean deleteUser(User user);
}
