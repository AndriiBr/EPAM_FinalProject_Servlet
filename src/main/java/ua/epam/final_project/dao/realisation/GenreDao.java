package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IConnectionPool;
import ua.epam.final_project.dao.IGenreDao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static ua.epam.final_project.dao.SQLConstant.*;

public class GenreDao implements IGenreDao {
    private final IConnectionPool connectionPool;

    public GenreDao(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Integer getNumberOfGenres() throws SQLException {
        int numberOfGenres = 0;
        Connection con = null;
        Statement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_GENRES);

            if (rs.next()) {
                numberOfGenres = rs.getInt("rowcount");
            }

        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return numberOfGenres;
    }

    @Override
    public Map<Integer, String> findAllGenres() throws SQLException {
        Map<Integer, String > genres = new HashMap<>();

        Connection con = null;
        Statement statement = null;

        try {
            con = connectionPool.getConnection();

            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_GENRES);
            while (rs.next()) {
                genres.put(rs.getInt("id"), rs.getString("name"));
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
