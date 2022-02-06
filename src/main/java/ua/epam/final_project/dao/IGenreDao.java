package ua.epam.final_project.dao;



import java.sql.SQLException;
import java.util.Map;

public interface IGenreDao {

    Integer getNumberOfGenres() throws SQLException;

    Map<Integer, String> findAllGenres() throws SQLException;
}
