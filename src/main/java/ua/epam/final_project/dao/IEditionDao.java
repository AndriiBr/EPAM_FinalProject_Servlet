package ua.epam.final_project.dao;

import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;

import java.util.List;

/**
 * An interface containing methods for working with the database.
 * Target entity - Edition table
 */
public interface IEditionDao {

    /**
     * Gets the number of rows in the table "edition" from DB
     * @return number of rows
     * @throws DataNotFoundException if nothing was found in Db
     */
    Integer getNumberOfEditions(String genreFilter) throws DataNotFoundException;

    /**
     * Gets the number of rows in the table "edition" without positions user already has/ not has.
     * @param user - user entity
     * @param has - TRUE - user subscribe for / FALSE - user does not subscribe for
     * @return - number of rows
     * @throws DataNotFoundException if nothing was found in Db
     */
    Integer getNumberOfEditions(User user, boolean has, String genreFilter) throws DataNotFoundException;

    /**
     * Gets a list of all editions retrieved from the database
     * @return List of editions from DB
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<Edition> findAllEditions() throws DataNotFoundException;

    /**
     * Find editions in DB by part on title name
     * @param field - field where try to find coincidence
     * @param name - word to search
     * @return list of editions
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<Edition> findAllEditionsByName(String field, String name) throws DataNotFoundException;

    /**
     * Gets a list of editions retrieved from the database
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String genreFilter, String orderBy) throws DataNotFoundException;

    /**
     * Gets a list of editions retrieved from the database
     * @param user - the user to be searched in the database
     * @param has - TRUE - user has / FALSE - user does not have
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, String genreFilter, String orderBy) throws DataNotFoundException;

    /**
     * Get edition from DB by title
     * @param title - edition title
     * @return edition entity
     * @throws DataNotFoundException if nothing was found in Db
     */
    Edition findEditionByTitle(String title) throws DataNotFoundException;

    /**
     * Gets edition entity from DB by id
     * @param id edition unique id
     * @return edition entity
     * @throws DataNotFoundException if nothing was found in Db
     */
    Edition findEditionById(int id) throws DataNotFoundException;

    /**
     * Inserts new edition into DB
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean insertNewEdition(Edition edition);

    /**
     * Updates edition in DB
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean updateEdition(Edition edition);

    /**
     * Deletes edition from DB
     * @param edition edition entity
     * @return TRUE - if operation was success. FALSE - if not.
     */
    boolean deleteEdition(Edition edition);
}
