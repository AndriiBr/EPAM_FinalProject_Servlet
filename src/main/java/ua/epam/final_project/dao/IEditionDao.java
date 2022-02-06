package ua.epam.final_project.dao;

import ua.epam.final_project.util.entity.Edition;

import java.sql.SQLException;
import java.util.List;

public interface IEditionDao {

    Integer getNumberOfEditions() throws SQLException;

    List<Edition> findAllEditions() throws SQLException;

    List<Edition> findAllEditionsFromTo(int recordsPerPage, int page) throws SQLException;

    Edition getEditionByTitle(String title) throws SQLException;

    boolean insertNewEdition(String title, String imagePath, String price) throws SQLException;

    boolean deleteEditionByTitle(String title) throws SQLException;
}
