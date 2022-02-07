package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IEditionGenreDao;
import static ua.epam.final_project.dao.SQLConstant.SQL_FIND_ALL_EDITION_GENRES;
import static ua.epam.final_project.dao.SQLConstant.SQL_FIND_ALL_EDITION_GENRES_BY_EDITION_ID;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditionGenreDao implements IEditionGenreDao {
    private final Connection connection;

    public EditionGenreDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Map<Integer, Integer> getAllEditionGenre() throws SQLException {
        Map<Integer, Integer > editionGenre = new HashMap<>();

        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_EDITION_GENRES);
            while (rs.next()) {
                editionGenre.put(rs.getInt("edition_id"), rs.getInt("genre_id"));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return editionGenre;
    }

    @Override
    public List<Integer> getGenresToEditionById(int id) throws SQLException {
        List<Integer> genres = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_EDITION_GENRES_BY_EDITION_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                genres.add(rs.getInt("genre_id"));
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return genres;
    }
}
