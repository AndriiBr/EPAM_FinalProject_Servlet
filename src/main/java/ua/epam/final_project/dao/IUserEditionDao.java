package ua.epam.final_project.dao;

import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;
import ua.epam.final_project.util.entity.UserEdition;

import java.sql.SQLException;
import java.util.List;

public interface IUserEditionDao {

    List<UserEdition> findAllUserEdition() throws SQLException;

    List<UserEdition> findAllUserEditionByUserId(int userId) throws SQLException;

    List<UserEdition> findAllUserEditionByUserIdEditionId(int userId, int editionId) throws SQLException;

    boolean insertUserEdition(User user, Edition edition) throws SQLException;

    /**
     * Deletes user - edition link in DB. (Unsubscribe user from edition)
     * @param user - user entity
     * @param edition - edition entity
     * @return TRUE - if operation was successful.
     * @throws SQLException
     */
    boolean deleteUserEdition(User user, Edition edition) throws SQLException;

    boolean deleteUserEditionByEdition(Edition edition) throws SQLException;

    boolean deleteUserEditionByUser(User user) throws SQLException;
}
