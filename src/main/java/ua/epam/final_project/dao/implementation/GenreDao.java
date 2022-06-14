package ua.epam.final_project.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IGenreDao;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.entity.Genre;

import static ua.epam.final_project.dao.SQLConstant.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDao implements IGenreDao {
    private static final Logger logger = LogManager.getLogger(GenreDao.class);
    private final Connection connection;

    public GenreDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfGenres() throws DataNotFoundException {
        int numberOfRows = 0;

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_GENRES);

            if (rs.next()) {
                numberOfRows = rs.getInt("rowcount");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return numberOfRows;
    }

    @Override
    public List<Genre> findAllGenres() throws DataNotFoundException {
        List<Genre> genres = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_GENRES);
            while (rs.next()) {
                genres.add(extractGenre(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return genres;
    }

    @Override
    public Genre findGenreById(int id) throws DataNotFoundException {
        Genre genre = new Genre();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_GENRE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                genre = extractGenre(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return genre;
    }

    /**
     * UTILITY METHOD
     * create Edition entity according to data from database
     */
    private Genre extractGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();

        genre.setId(rs.getInt("id"));
        genre.setGenreEn(rs.getString("name_en"));
        genre.setGenreUa(rs.getString("name_ua"));

        return genre;
    }
}
