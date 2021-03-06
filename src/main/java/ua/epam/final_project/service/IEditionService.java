package ua.epam.final_project.service;

import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.entity.Edition;

import java.util.List;

public interface IEditionService {

    /**
     * Gets the number of rows in the table "edition"
     * @return number of rows
     * @throws UnknownEditionException if nothing was found in Db
     */
    Integer getNumberOfEditions(int genreFilter) throws UnknownEditionException;

    /**
     * Gets the number of rows in the table "edition" without positions user already has/ not has.
     * @param userDto - userDto entity
     * @param has - TRUE - user subscribe for / FALSE - user does not subscribe for
     * @return - number of rows
     * @throws UnknownEditionException if nothing was found in Db
     */
    Integer getNumberOfEditions(UserDto userDto, boolean has, int genreFilter) throws UnknownEditionException;

    /**
     * Get a list of all editions retrieved from the database
     * @return List of editions from DB
     * @throws UnknownEditionException if nothing was found in Db
     */
    List<Edition> findAllEditions() throws UnknownEditionException;

    /**
     * Find editions in DB by part on title name
     * @param field - field where try to find coincidence
     * @param name - word to search
     * @return list of editions
     */
    List<Edition> findAllEditionsByName(String field, String name) throws UnknownEditionException;

    /**
     * Get a list of editions retrieved from the database
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws UnknownEditionException if nothing was found in Db
     */
    List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, int genreFilter, String orderBy) throws UnknownEditionException;

    /**
     * Get a list of editions retrieved from the database
     * @param userDto - the userDto to be searched in the database
     * @param has - TRUE - user has / FALSE - user does not have
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws UnknownEditionException if nothing was found in Db
     */
    List<Edition> findAllEditionsFromTo(UserDto userDto, boolean has, int recordsPerPage, int page, int genreFilter, String orderBy) throws UnknownEditionException;

    /**
     * Get edition entity from DB by id
     * @param id edition unique id
     * @return edition entity
     * @throws UnknownEditionException if nothing was found in Db
     */
    Edition findEditionById(int id) throws UnknownEditionException;

    /**
     * Insert new edition into DB
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean insertNewEdition(Edition edition);

    /**
     * Update edition in DB
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean updateEdition(Edition edition);

    /**
     * Delete edition from DB
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean deleteEdition(Edition edition);
}
