package ua.epam.final_project.service;

import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownUserEditionPairException;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.UserEdition;

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
     * @param userDto userDto entity
     * @return list of user-edition pairs
     * @throws UnknownUserEditionPairException if nothing was found in Db
     */
    List<UserEdition> findAllUserEditionByUser(UserDto userDto) throws UnknownUserEditionPairException;

    /**
     * Gets a list of user-edition pairs for provided user and edition combunation
     * @param userDto userDto entity
     * @param edition edition entity
     * @return list of user-edition pairs
     * @throws UnknownUserEditionPairException if nothing was found in Db
     */
    List<UserEdition> findAllUserEditionByUserIdEditionId(UserDto userDto, Edition edition) throws UnknownUserEditionPairException;

    /**
     * Inserts new user-edition pair into DB
     * @param userDto userDto entity
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean insertUserEdition(UserDto userDto, Edition edition) ;

    /**
     * Deletes user-edition pair from DB. (Unsubscribe user from edition)
     * @param userDto - userDto entity
     * @param edition - edition entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean deleteUserEdition(UserDto userDto, Edition edition);

    /**
     * Deletes user-edition pair by Edition from DB. (Unsubscribe user from edition)
     * @param edition - edition entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean deleteUserEditionByEdition(Edition edition);

    /**
     * Deletes user-edition pair by User from DB. (Unsubscribe user from edition)
     * @param userDto - userDto entity
     * @return TRUE - if operation was success. FALSE - if not
     */
    boolean deleteUserEditionByUser(UserDto userDto);
}
