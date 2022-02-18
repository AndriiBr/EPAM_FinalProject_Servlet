package ua.epam.final_project.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.IncorrectPropertyException;
import ua.epam.final_project.service.implementation.UserService;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.epam.final_project.dao.SQLConstant.*;

public class EditionDao implements IEditionDao {
    private static final Logger logger = LogManager.getLogger(EditionDao.class);
    private static final String DB_TABLE = "edition";
    private final Connection connection;

    public EditionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfEditions() throws DataNotFoundException {
        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_NUMBER_OF_ROWS)) {
            statement.setString(1, DB_TABLE);
            ResultSet rs = statement.executeQuery();

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
    public Integer getNumberOfEditions(User user, boolean has) throws DataNotFoundException {
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
            logger.error(e);
            throw new DataNotFoundException();
        }
        return numberOfEditions;
    }

    @Override
    public List<Edition> findAllEditions() throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            statement.setString(1, DB_TABLE);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                editionList.add(extractEdition(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return editionList;
    }

    @Override
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String orderBy) throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();
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
                editionList.add(extractEdition(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return editionList;
    }

    @Override
    public List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, String orderBy) throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();
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
                editionList.add(extractEdition(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return editionList;
    }

    @Override
    public Edition findEditionById(int id) throws DataNotFoundException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_EDITION_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractEdition(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return null;
    }


    @Override
    public boolean insertNewEdition(Edition edition) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_EDITION)) {
            statement.setString(1, edition.getTitleEn());
            statement.setString(2, edition.getTitleUa());
            //insert path to image into DB
            statement.setString(3, edition.getImagePath());
            statement.setInt(4, edition.getGenreId());
            statement.setInt(5, edition.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateEdition(Edition edition) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EDITION)) {
            statement.setString(1, edition.getTitleEn());
            statement.setString(2, edition.getTitleUa());
            statement.setString(3, edition.getImagePath());
            statement.setInt(4, edition.getGenreId());
            statement.setInt(5, edition.getPrice());
            statement.setInt(6, edition.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteEdition(Edition edition) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_EDITION)) {
            statement.setInt(1, edition.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    /**
     * UTILITY METHOD
     * create Edition entity according to data from database
     */
    private Edition extractEdition(ResultSet rs) throws SQLException {
        Edition edition = new Edition();

        edition.setId(rs.getInt("id"));
        edition.setTitleEn(rs.getString("titleEn"));
        edition.setTitleUa(rs.getString("titleUa"));
        edition.setImagePath(rs.getString("title_image"));
        edition.setGenreId(rs.getInt("genre_id"));
        edition.setPrice(rs.getInt("price"));

        return edition;
    }
}
