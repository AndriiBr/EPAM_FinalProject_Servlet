package ua.epam.final_project.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.epam.final_project.dao.SQLConstant.*;

public class EditionDaoImpl implements IEditionDao {

    private static final Logger logger = LogManager.getLogger(EditionDaoImpl.class);
    private final Connection connection;
    private static final String ROW_COUNT = "rowcount";

    public EditionDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfEditions(int genreFilter) throws DataNotFoundException {
        int numberOfRows = 0;
        String sqlPattern;

        if (genreFilter <= 0) {
            sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_All_GENRES;
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(sqlPattern);

                if (rs.next()) {
                    numberOfRows = rs.getInt(ROW_COUNT);
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DataNotFoundException();
            }
        } else {
            sqlPattern = SQL_GET_NUMBER_OF_EDITIONS;
            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, genreFilter);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    numberOfRows = rs.getInt(ROW_COUNT);
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DataNotFoundException();
            }
        }
        return numberOfRows;
    }

    @Override
    public Integer getNumberOfEditions(User user, boolean has, int genreFilter) throws DataNotFoundException {
        int numberOfEditions = 0;
        String sqlPattern;

        if (genreFilter <= 0) {
            if (has) {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_USER_ALREADY_HAS_ALL_GENRES;
            } else {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_WITHOUT_USER_ALREADY_HAS_ALL_GENRES;
            }
            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, user.getId());
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    numberOfEditions = rs.getInt(ROW_COUNT);
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DataNotFoundException();
            }

        } else {
            if (has) {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_USER_ALREADY_HAS;
            } else {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_WITHOUT_USER_ALREADY_HAS;
            }
            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, genreFilter);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    numberOfEditions = rs.getInt(ROW_COUNT);
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DataNotFoundException();
            }
        }
        return numberOfEditions;
    }

    @Override
    public List<Edition> findAllEditions() throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_EDITIONS);
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
    public List<Edition> findAllEditionsByName(String field, String name) throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();

        String flexName = "%".concat(name).concat("%");
        String sqlPattern = String.format(SQL_FIND_ALL_EDITIONS_BY_NAME, field);

        try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
            statement.setString(1, flexName);
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
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, int genreFilter, String orderBy) throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();
        String order = orderBy;
        String sqlPattern;

        if (order == null || order.equals("")) {
            order = "id";
        }

        if (genreFilter <= 0) {
            sqlPattern = String.format(SQL_FIND_EDITIONS_ORDER_BY_FROM_TO_ALL_GENRES, order) ;
            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, recordsPerPage);
                statement.setInt(2, (page - 1) * recordsPerPage);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    editionList.add(extractEdition(rs));
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DataNotFoundException();
            }

        } else {
            sqlPattern = String.format(SQL_FIND_EDITIONS_ORDER_BY_FROM_TO, order);
            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, genreFilter);
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
        }
        return editionList;
    }

    @Override
    public List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, int genreFilter, String orderBy) throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();
        String order = orderBy;
        String sqlPattern;

        if (order == null || order.equals("")) {
            order = "id";
        }

        if (genreFilter <= 0) {
            if (has) {
                sqlPattern = String.format(SQL_FIND_EDITIONS_FROM_TO_USER_ALREADY_HAS_ALL_GENRES, order);
            } else {
                sqlPattern = String.format(SQL_FIND_EDITIONS_FROM_TO_WITHOUT_USER_ALREADY_HAS_ALL_GENRES, order);
            }

            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, user.getId());
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

        } else {
            if (has) {
                sqlPattern = String.format(SQL_FIND_EDITIONS_FROM_TO_USER_ALREADY_HAS, order);
            } else {
                sqlPattern = String.format(SQL_FIND_EDITIONS_FROM_TO_WITHOUT_USER_ALREADY_HAS, order);
            }

            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, genreFilter);
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
        }
        return editionList;
    }

    @Override
    public Edition findEditionByTitle(String title) throws DataNotFoundException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_EDITION_BY_TITLE)) {
            statement.setString(1, title);

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
            if(findEditionByTitle(edition.getTitleEn()) != null) {
                return false;
            }
            prepareStatement(edition, statement);
            statement.executeUpdate();
        } catch (SQLException | DataNotFoundException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateEdition(Edition edition) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EDITION)) {
            prepareStatement(edition, statement);
            statement.setInt(8, edition.getId());
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
            Edition editionToBeDeleted = findEditionByTitle(edition.getTitleEn());
            statement.setInt(1, editionToBeDeleted.getId());
            statement.executeUpdate();
        } catch (SQLException | DataNotFoundException e) {
            logger.error(e);
            return false;
        }

        return true;
    }

    /**
     * UTILITY METHOD
     * prepare statement before execution
     */
    private void prepareStatement(Edition edition, PreparedStatement statement) throws SQLException {
        statement.setString(1, edition.getTitleEn());
        statement.setString(2, edition.getTitleUa());
        statement.setString(3, edition.getTextEn());
        statement.setString(4, edition.getTextUa());
        statement.setString(5, edition.getImagePath());
        statement.setInt(6, edition.getGenreId());
        statement.setInt(7, edition.getPrice());
    }

    /**
     * UTILITY METHOD
     * create Edition entity according to data from database
     */
    private Edition extractEdition(ResultSet rs) throws SQLException {
        Edition edition = new Edition();

        edition.setId(rs.getInt("id"));
        edition.setTitleEn(rs.getString("title_en"));
        edition.setTitleUa(rs.getString("title_ua"));
        edition.setTextEn(rs.getString("text_en"));
        edition.setTextUa(rs.getString("text_ua"));
        edition.setImagePath(rs.getString("title_image"));
        edition.setGenreId(rs.getInt("genre_id"));
        edition.setPrice(rs.getInt("price"));

        return edition;
    }
}
