package ua.epam.final_project.service;

import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;

import java.util.List;

public interface IEditionService {

    /**
     * Gets the number of rows in the table "edition"
     * @return number of rows
     * @throws UnknownEditionException if nothing was found in Db
     */
    Integer getNumberOfEditions(String genreFilter) throws UnknownEditionException;

    /**
     * Gets the number of rows in the table "edition" without positions user already has/ not has.
     * @param user - user entity
     * @param has - TRUE - user subscribe for / FALSE - user does not subscribe for
     * @return - number of rows
     * @throws UnknownEditionException if nothing was found in Db
     */
    Integer getNumberOfEditions(User user, boolean has, String genreFilter) throws UnknownEditionException;

    /**
     * Get a list of all editions retrieved from the database
     * @return List of editions from DB
     * @throws UnknownEditionException if nothing was found in Db
     */
    List<Edition> findAllEditions() throws UnknownEditionException;

    /**
     * Get a list of editions retrieved from the database
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws UnknownEditionException if nothing was found in Db
     */
    List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String genreFilter, String orderBy) throws UnknownEditionException;

    /**
     * Get a list of editions retrieved from the database
     * @param user - the user to be searched in the database
     * @param has - TRUE - user has / FALSE - user does not have
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws UnknownEditionException if nothing was found in Db
     */
    List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, String genreFilter, String orderBy) throws UnknownEditionException;

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
