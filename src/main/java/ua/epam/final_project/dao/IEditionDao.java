package ua.epam.final_project.dao;

import ua.epam.final_project.util.edition.Edition;

import java.sql.SQLException;
import java.util.List;

public interface IEditionDao {

    List<Edition> findAllEditions() throws SQLException;

    Edition getEditionByTitle(String title) throws SQLException;

    boolean insertNewEdition(String title, String imagePath, String price) throws SQLException;

    boolean deleteEditionByTitle(String title) throws SQLException;
}
