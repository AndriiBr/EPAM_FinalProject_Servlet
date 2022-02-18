package ua.epam.final_project.service;

import ua.epam.final_project.exception.UnknownUserEditionPairException;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;
import ua.epam.final_project.util.entity.UserEdition;

import java.util.List;

public interface IUserEditionService {

    /**
     * Gets the number of rows in the table "user_edition" from DB
     * @return number of rows
     * @throws UnknownUserEditionPairException if nothing was found in Db
     */
    Integer getNumberOfRows() throws UnknownUserEditionPairException;

    /**
     * Gets a list of all user-edition pairs from "user_edition" table
     * @return list of all user-edition pairs
     * @throws UnknownUserEditionPairException if nothing was found in Db
     */
    List<UserEdition> findAllUserEdition() throws UnknownUserEditionPairException;

    /**
     * Gets a list of all user-edition pairs for provided user only
     * @param user user entity
     * @return list of user-edition pairs
     * @throws UnknownUserEditionPairException if nothing was found in Db
     */
    List<UserEdition> findAllUserEditionByUser(User user) throws UnknownUserEditionPairException;

    /**
     * Gets a list of user-edition pairs for provided user and edition combunation
     * @param user user entity
     * @param edition edition entity
     * @return list of user-edition pairs
     * @throws UnknownUserEditionPairException if nothing was found in Db
     */
    List<UserEdition> findAllUserEditionByUserIdEditionId(User user, Edition edition) throws UnknownUserEditionPairException;

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
