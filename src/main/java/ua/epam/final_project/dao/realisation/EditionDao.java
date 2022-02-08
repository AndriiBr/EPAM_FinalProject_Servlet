package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IEditionDao;
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
    private final Connection connection;

    public EditionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfEditions() throws SQLException {
        int numberOfEditions = 0;

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_EDITIONS);

            if (rs.next()) {
                numberOfEditions = rs.getInt("rowcount");
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return numberOfEditions;
    }

    @Override
    public List<Edition> findAllEditions() throws SQLException {
        List<Edition> editions = new ArrayList<>();

        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_EDITIONS);
            while (rs.next()) {
                editions.add(extractEdition(rs));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return editions;
    }

    @Override
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page) throws SQLException {
        List<Edition> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_EDITIONS_FROM_TO);) {
            statement.setInt(1, recordsPerPage);
            statement.setInt(2, (page - 1) * recordsPerPage);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(extractEdition(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return list;
    }

    @Override
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String orderBy) throws SQLException {
        List<Edition> list = new ArrayList<>();
        String order = orderBy;

        if (order.equals("")) {
            order = "id";
        }

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_EDITIONS_ORDER_BY_FROM_TO)) {
            statement.setString(1, order);
            statement.setInt(2, recordsPerPage);
            statement.setInt(3, (page - 1) * recordsPerPage);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(extractEdition(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }

        return list;
    }

    @Override
    public Edition getEditionByTitle(String title) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_EDITION_BY_TITLE);) {
            statement.setString(1, title);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractEdition(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return null;
    }


    @Override
    public boolean insertNewEdition(String title, String imagePath, int genreId, String price) throws SQLException {
        //ToDo
        // Change image assigment when no image available
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

        //Add new edition into DB
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_EDITION)) {
            statement.setString(1, title);
            //insert path to image into DB
            statement.setString(2, newImagePath);
            statement.setInt(3, genreId);
            statement.setString(4, String.valueOf(price));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return true;
    }

    @Override
    public boolean deleteEditionByTitle(String title) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EDITION_BY_TITLE)) {
            statement.setString(1, title);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
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
            edition.setGenreId(rs.getInt("genre_id"));
            edition.setPrice(rs.getInt("price"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return edition;
    }
}
