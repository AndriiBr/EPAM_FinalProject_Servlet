package ua.epam.final_project.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;

import ua.epam.final_project.dao.IGenreDao;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.IncorrectPropertyException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.entity.Genre;

import java.util.List;

public class GenreService implements IGenreService {
    private static final Logger logger = LogManager.getLogger(GenreService.class);

    private static final DataBaseSelector DB_SOURCE = DataBaseSelector.POSTGRES;
    private DaoFactory daoFactory;
    private IGenreDao genreDao;

    public GenreService() {
        try {
            daoFactory = DaoFactory.getDaoFactory(DB_SOURCE);
            genreDao = daoFactory.getGenreDao();
        } catch (IncorrectPropertyException | DataBaseNotSupportedException e) {
            logger.error(e);
        }
    }

    @Override
    public Integer getNumberOfGenres() throws UnknownGenreException {
        Integer numberOfRows;
        try {
            daoFactory.getConnection();
            numberOfRows = genreDao.getNumberOfGenres();
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
            userList = genreDao.findAllGenres();
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
            genre = genreDao.findGenreById(id);
            return genre;
        } catch (DataNotFoundException e) {
            logger.error(e);
            throw new UnknownGenreException();
        } finally {
            daoFactory.releaseConnection();
        }
    }
}
