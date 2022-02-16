package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IGenreDao;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.Genre;

import static ua.epam.final_project.dao.SQLConstant.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDao implements IGenreDao {
    private final Connection connection;

    public GenreDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfGenres() throws SQLException {
        int numberOfGenres = 0;

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_GENRES);

            if (rs.next()) {
                numberOfGenres = rs.getInt("rowcount");
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return numberOfGenres;
    }

    @Override
    public List<Genre> findAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_GENRES);
            while (rs.next()) {
                genres.add(extractGenre(rs));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return genres;
    }

    /**
     * UTILITY METHOD
     * create Edition entity according to data from database
     */
    private Genre extractGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        try {
            genre.setId(rs.getInt("id"));
            genre.setGenreEn(rs.getString("nameEn"));
            genre.setGenreUa(rs.getString("nameUa"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return genre;
    }
}
