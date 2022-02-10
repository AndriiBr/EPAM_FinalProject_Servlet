package ua.epam.final_project.dao;

import java.sql.SQLException;
import java.util.Map;

public interface IUserEditionDao {

    Map<Integer, Integer> findAllUserEdition() throws SQLException;

    Map<Integer, Integer> findAllUserEditionByUserId(int userId) throws SQLException;

    Map<Integer, Integer> findAllUserEditionByUserIdEditionId(int userId, int editionId) throws SQLException;

    boolean insertUserEdition(int userId, int editionId) throws SQLException;
}
