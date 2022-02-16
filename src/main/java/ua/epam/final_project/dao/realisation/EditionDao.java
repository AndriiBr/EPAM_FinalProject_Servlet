package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public Integer getNumberOfEditions(User user, boolean has) throws SQLException {
        int numberOfEditions = 0;
        String sqlPattern;

        if (has) {
            sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_USER_ALREADY_HAS;
        } else {
            sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_WITHOUT_USER_ALREADY_HAS;
        }

        try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();

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
    public List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, String orderBy) throws SQLException {
        List<Edition> list = new ArrayList<>();
        String order = orderBy;
        String sqlPattern;

        if (orderBy.equals("")) {
            order = "id";
        }

        if (has) {
            sqlPattern = SQL_FIND_EDITIONS_FROM_TO_USER_ALREADY_HAS;
        } else {
            sqlPattern = SQL_FIND_EDITIONS_FROM_TO_WITHOUT_USER_ALREADY_HAS;
        }

        try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
            statement.setInt(1, user.getId());
            statement.setString(2, order);
            statement.setInt(3, recordsPerPage);
            statement.setInt(4, (page - 1) * recordsPerPage);

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
    public Edition getEditionByTitle(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_EDITION_BY_ID)) {
            statement.setInt(1, id);

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
    public boolean insertNewEdition(Edition edition) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_EDITION)) {
            statement.setString(1, edition.getTitle());
            //insert path to image into DB
            statement.setString(2, edition.getImagePath());
            statement.setInt(3, edition.getGenreId());
            statement.setInt(4, edition.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return true;
    }

    @Override
    public boolean updateEdition(Edition edition) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EDITION)) {
            statement.setString(1, edition.getTitle());
            statement.setString(2, edition.getImagePath());
            statement.setInt(3, edition.getGenreId());
            statement.setInt(4, edition.getPrice());
            statement.setInt(5, edition.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException();
        }

        return true;
    }

    @Override
    public boolean deleteEdition(Edition edition) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EDITION)) {
            //Delete all user-edition relationships before delete edition from DB
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            daoFactory.getUserEditionDao().deleteUserEditionByEdition(edition);

            statement.setInt(1, edition.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (DataBaseNotSupportedException e) {
            e.printStackTrace();
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
