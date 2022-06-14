package ua.epam.final_project.service;

import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.entity.Genre;

import java.util.List;

public interface IGenreService {

    /**
     * Gets number of rows in 'genre' table from DB
     * @return number of rows
     * @throws UnknownGenreException if nothing was found in Db
     */
    Integer getNumberOfGenres() throws UnknownGenreException;

    /**
     * Gets list of all genres from DB
     * @return list of genres
     * @throws UnknownGenreException if nothing was found in Db
     */
    List<Genre> findAllGenres() throws UnknownGenreException;

    /**
     * Gets single genre entity by id
     * @param id genre id
     * @return genre entity
     * @throws UnknownGenreException if nothing was found in Db
     */
    Genre findGenreById(int id) throws UnknownGenreException;
}
