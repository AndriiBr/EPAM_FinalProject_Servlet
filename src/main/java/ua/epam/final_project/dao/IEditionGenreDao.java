package ua.epam.final_project.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IEditionGenreDao {

    Map<Integer, Integer> getAllEditionGenre() throws SQLException;

    List<Integer> getGenresToEditionById(int id) throws SQLException;
}
