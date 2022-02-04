package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IConnectionPool;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.util.edition.Edition;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static ua.epam.final_project.dao.SQLConstant.*;

public class EditionDao implements IEditionDao {
    private final IConnectionPool connectionPool;

    public EditionDao(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
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
            if (con != null) {
                connectionPool.releaseConnection(con);
            }
            if (statement != null) {
                statement.close();
            }
        }
        return editions;
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
            if (con != null) {
                connectionPool.releaseConnection(con);
            }
            if (statement != null) {
                statement.close();
            }
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
            if (con != null) {
                connectionPool.releaseConnection(con);
            }
            if (statement != null) {
                statement.close();
            }
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
            if (con != null) {
                connectionPool.releaseConnection(con);
            }
            if (statement != null) {
                statement.close();
            }
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
            edition.setPrice(rs.getInt("price"));
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return edition;
    }
}
