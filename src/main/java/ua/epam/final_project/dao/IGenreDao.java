package ua.epam.final_project.dao;


import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.entity.Genre;

import java.util.List;

public interface IGenreDao {

    /**
     * Gets number of rows in 'genre' table from DB
     * @return number of rows
     * @throws DataNotFoundException if nothing was found in Db
     */
    Integer getNumberOfGenres() throws DataNotFoundException;

    /**
     * Gets list of all genres from DB
     * @return list of genres
     * @throws DataNotFoundException if nothing was found in Db
     */
    List<Genre> findAllGenres() throws DataNotFoundException;

    /**
     * Gets single genre entity by id
     * @param id genre id
     * @return genre entity
     * @throws DataNotFoundException if nothing was found in Db
     */
    Genre findGenreById(int id) throws DataNotFoundException;
}
