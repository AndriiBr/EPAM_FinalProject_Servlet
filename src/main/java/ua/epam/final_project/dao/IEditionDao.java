package ua.epam.final_project.dao;

import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface containing methods for working with the database.
 * Target entity - Edition table
 */
public interface IEditionDao {

    /**
     * Gets the number of rows in the table "edition"
     * @return number of rows
     * @throws SQLException
     */
    Integer getNumberOfEditions() throws SQLException;

    /**
     * Gets the number of rows in the table "edition" without positions user already has.
     * @param user - user entity
     * @param has - TRUE - user subscribe for / FALSE - user does not subscribe for
     * @return - number of rows
     * @throws SQLException
     */
    Integer getNumberOfEditions(User user, boolean has) throws SQLException;

    /**
     * Creates a list of All publications retrieved from the database
     * @return List of editions from DB
     * @throws SQLException
     */
    List<Edition> findAllEditions() throws SQLException;

    /**
     * Creates a list of publications retrieved from the database
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws SQLException
     */
    List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String orderBy) throws SQLException;

    /**
     * Creates a list of publications retrieved from the database
     * @param user - the user to be searched in the database
     * @param has - TRUE - user has / FALSE - user does not have
     * @param recordsPerPage - the number of items to be unloaded from DB
     * @param page - start point to load from
     * @param orderBy - sort list by. You can put "" to sort by "id" as default value
     * @return List of editions from DB
     * @throws SQLException
     */
    List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, String orderBy) throws SQLException;

    Edition getEditionByTitle(String title) throws SQLException;

    boolean insertNewEdition(Edition edition) throws SQLException;

    /**
     * Updates edition in database
     * @param edition - modified edition entity
     * @return TRUE - if operation was successful.
     * @throws SQLException
     */
    boolean updateEdition(Edition edition) throws SQLException;

    boolean deleteEditionByTitle(String title) throws SQLException;
}
