package ua.epam.final_project.dao;

import ua.epam.final_project.util.entity.Edition;

import java.sql.SQLException;
import java.util.List;

public interface IEditionDao {

    Integer getNumberOfEditions() throws SQLException;

    List<Edition> findAllEditions() throws SQLException;

    List<Edition> findAllEditionsFromTo(int recordsPerPage, int page) throws SQLException;

    List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String orderBy) throws SQLException;

    Edition getEditionByTitle(String title) throws SQLException;

    boolean insertNewEdition(Edition edition) throws SQLException;

    boolean deleteEditionByTitle(String title) throws SQLException;
}
