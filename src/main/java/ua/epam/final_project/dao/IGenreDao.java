package ua.epam.final_project.dao;



import ua.epam.final_project.util.entity.Genre;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGenreDao {

    Integer getNumberOfGenres() throws SQLException;

    List<Genre> findAllGenres() throws SQLException;
}
