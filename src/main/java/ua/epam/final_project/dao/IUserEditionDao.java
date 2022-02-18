package ua.epam.final_project.dao;

import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;
import ua.epam.final_project.util.entity.UserEdition;

import java.sql.SQLException;
import java.util.List;

public interface IUserEditionDao {

    /**
     * Gets the number of rows in the table "user_edition" from DB
     * @return number of rows
     * @throws DataNotFoundException if nothing was found in Db
     */
    Integer getNumberOfRows() throws DataNotFoundException;

    /**
     * Gets a list of all user-edition pairs from "user_edition" table
     * @return list of all user-edition pairs
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<UserEdition> findAllUserEdition() throws DataNotFoundException;

    /**
     * Gets a list of all user-edition pairs for provided user only
     * @param user user entity
     * @return list of user-edition pairs
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<UserEdition> findAllUserEditionByUser(User user) throws DataNotFoundException;

    /**
     * Gets a list of user-edition pairs for provided user and edition combunation
     * @param user user entity
     * @param edition edition entity
     * @return list of user-edition pairs
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<UserEdition> findAllUserEditionByUserIdEditionId(User user, Edition edition) throws DataNotFoundException;

    /**
     * Inserts new user-edition pair into DB
     * @param user user entity
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean insertUserEdition(User user, Edition edition) ;

    /**
     * Deletes user-edition pair from DB. (Unsubscribe user from edition)
     * @param user - user entity
     * @param edition - edition entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean deleteUserEdition(User user, Edition edition);

    /**
     * Deletes user-edition pair by Edition from DB. (Unsubscribe user from edition)
     * @param edition - edition entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean deleteUserEditionByEdition(Edition edition);

    /**
     * Deletes user-edition pair by User from DB. (Unsubscribe user from edition)
     * @param user - user entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean deleteUserEditionByUser(User user);
}
