package ua.epam.final_project.dao;

import ua.epam.final_project.util.entity.UserEdition;

import java.sql.SQLException;
import java.util.List;

public interface IUserEditionDao {

    List<UserEdition> findAllUserEdition() throws SQLException;

    List<UserEdition> findAllUserEditionByUserId(int userId) throws SQLException;

    List<UserEdition> findAllUserEditionByUserIdEditionId(int userId, int editionId) throws SQLException;

    boolean insertUserEdition(int userId, int editionId) throws SQLException;
}
