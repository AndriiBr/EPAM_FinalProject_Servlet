package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IConnectionPool;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.Edition;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static ua.epam.final_project.dao.SQLConstant.*;

public class EditionDao implements IEditionDao {
    private final IConnectionPool connectionPool;

    public EditionDao(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Integer getNumberOfEditions() throws SQLException {
        int numberOfEditions = 0;
        Connection con = null;
        Statement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_EDITIONS);

            if (rs.next()) {
                numberOfEditions = rs.getInt("rowcount");
            }

        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return numberOfEditions;
    }

    @Override
    public List<Edition> findAllEditions() throws SQLException {
        List<Edition> editions = new ArrayList<>();
        Connection con = null;
        Statement statement = null;

        try {
            con = connectionPool.getConnection();

            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_EDITIONS);
            while (rs.next()) {
                editions.add(extractEdition(rs));
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return editions;
    }

    @Override
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page) throws SQLException {
        List<Edition> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_FIND_EDITIONS_FROM_TO);
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, (page - 1) * recordsPerPage);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(extractEdition(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return list;
    }

    @Override
    public Edition getEditionByTitle(String title) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_FIND_EDITION_BY_TITLE);
            statement.setString(1, title);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractEdition(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return null;
    }

    @Override
    public boolean insertNewEdition(String title, String imagePath, String price) throws SQLException {
        String newImagePath = "";

        String imageFolderName = "image_folder.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(imageFolderName)) {
            prop.load(resourceStream);

            if (imagePath.equals("no image")) {
                newImagePath = imagePath;
            } else {
                newImagePath = prop.getProperty("title_image_folder") + imagePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_INSERT_EDITION);
            statement.setString(1, title);
            //insert path to image into DB
            statement.setString(2, newImagePath);
            statement.setString(3, String.valueOf(price));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return true;
    }

    @Override
    public boolean deleteEditionByTitle(String title) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = connectionPool.getConnection();
            statement = con.prepareStatement(SQL_DELETE_EDITION_BY_TITLE);
            statement.setString(1, title);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            closeAllResources(con);
            closeAllResources(statement);
        }
        return true;
    }

    /**
     * UTILITY METHOD
     * create Edition entity according to data from database
     */
    private Edition extractEdition(ResultSet rs) throws SQLException {
        Edition edition = new Edition();
        try {
            edition.setId(rs.getInt("id"));
            edition.setTitle(rs.getString("title"));
            edition.setImagePath(rs.getString("title_image"));

            List<String> result = new ArrayList<>();
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            List<Integer> genreId = daoFactory.getEditionGenreDao().getGenresToEditionById(edition.getId());
            Map<Integer, String> genres = daoFactory.getGenreDao().findAllGenres();
            for (Integer value: genreId) {
                result.add(genres.get(value));
            }

            edition.setGenres(result);
            edition.setPrice(rs.getInt("price"));
        } catch (SQLException | DataBaseNotSupportedException | DataBaseConnectionException e) {
            throw new SQLException(e);
        }
        return edition;
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
