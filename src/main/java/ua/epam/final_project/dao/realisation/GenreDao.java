package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IGenreDao;
import static ua.epam.final_project.dao.SQLConstant.*;

import java.sql.*;
import java.util.HashMap;
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
    public Map<Integer, String> findAllGenres() throws SQLException {
        Map<Integer, String > genres = new HashMap<>();

        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_GENRES);
            while (rs.next()) {
                genres.put(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return genres;
    }
}
