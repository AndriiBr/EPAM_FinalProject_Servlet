package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IConnectionPool;
import ua.epam.final_project.dao.IEditionGenreDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.epam.final_project.dao.SQLConstant.SQL_FIND_ALL_EDITION_GENRES;
import static ua.epam.final_project.dao.SQLConstant.SQL_FIND_ALL_EDITION_GENRES_BY_EDITION_ID;

public class EditionGenreDao implements IEditionGenreDao {
    private final IConnectionPool connectionPool;

    public EditionGenreDao(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Map<Integer, Integer> getAllEditionGenre() throws SQLException {
        Map<Integer, Integer > editionGenre = new HashMap<>();
        Connection con = null;
        Statement statement = null;

        try {
            con = connectionPool.getConnection();

            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_EDITION_GENRES);
            while (rs.next()) {
                editionGenre.put(rs.getInt("edition_id"), rs.getInt("genre_id"));
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }

        return editionGenre;
    }

    @Override
    public List<Integer> getGenresToEditionById(int id) throws SQLException {
        List<Integer> genres = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = connectionPool.getConnection();

            statement = con.prepareStatement(SQL_FIND_ALL_EDITION_GENRES_BY_EDITION_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                genres.add(rs.getInt("genre_id"));
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }

        return genres;
    }

    /**
     * UTILITY METHOD
     * Release connection
     */
    private void closeAllResources(Connection connection) {
        if (connection != null) {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * UTILITY METHOD
     * close statement
     */
    private void closeAllResources(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * UTILITY METHOD
     * close prepared statement
     */
    private void closeAllResources(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }
}
