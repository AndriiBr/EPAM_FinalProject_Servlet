package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.epam.final_project.dao.IDaoFactory;
import ua.epam.final_project.dao.IGenreDao;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.IncorrectPropertyException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.entity.Genre;

import java.util.List;

public class GenreService implements IGenreService {
    private static final Logger logger = LogManager.getLogger(GenreService.class);

    private IDaoFactory daoFactory;

    public GenreService() {
        try {
            daoFactory = new PostgresDaoFactory();
        } catch (IncorrectPropertyException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfGenres() throws UnknownGenreException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            numberOfRows = daoFactory.getGenreDao().getNumberOfGenres();
            return numberOfRows;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownGenreException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public List<Genre> findAllGenres() throws UnknownGenreException {
        List<Genre> userList;
        try {
            daoFactory.getConnection();
            userList = daoFactory.getGenreDao().findAllGenres();
            return userList;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownGenreException();
        } finally {
            daoFactory.releaseConnection();
        }
    }

    @Override
    public Genre findGenreById(int id) throws UnknownGenreException {
        Genre genre;
        try {
            daoFactory.getConnection();
            genre = daoFactory.getGenreDao().findGenreById(id);
            return genre;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownGenreException();
        } finally {
            daoFactory.releaseConnection();
        }
    }
}
