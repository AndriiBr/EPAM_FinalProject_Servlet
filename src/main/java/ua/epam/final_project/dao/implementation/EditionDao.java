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

public class EditionDao implements IEditionDao {
    private static final Logger logger = LogManager.getLogger(EditionDao.class);
    private final Connection connection;

    public EditionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfEditions(String genreFilter) throws DataNotFoundException {
        int numberOfRows = 0;
        String genre = genreFilter;
        String sqlPattern;

        if (genre == null || genre.equals("0")) {
            genre = "all";
        }

        if (genre.equals("all")) {
            sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_All_GENRES;
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(sqlPattern);

                if (rs.next()) {
                    numberOfRows = rs.getInt("rowcount");
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DataNotFoundException();
            }
        } else {
            sqlPattern = SQL_GET_NUMBER_OF_EDITIONS;
            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, Integer.parseInt(genre));
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    numberOfRows = rs.getInt("rowcount");
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DataNotFoundException();
            }
        }
        return numberOfRows;
    }

    @Override
    public Integer getNumberOfEditions(User user, boolean has, String genreFilter) throws DataNotFoundException {
        int numberOfEditions = 0;
        String genre = genreFilter;
        String sqlPattern;

        if (genre == null || genre.equals("0")) {
            genre = "all";
        }

        if (genre.equals("all")) {
            if (has) {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_USER_ALREADY_HAS_ALL_GENRES;
            } else {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_WITHOUT_USER_ALREADY_HAS_ALL_GENRES;
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

        } else {
            if (has) {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_USER_ALREADY_HAS;
            } else {
                sqlPattern = SQL_GET_NUMBER_OF_EDITIONS_WITHOUT_USER_ALREADY_HAS;
            }
            try (PreparedStatement statement = connection.prepareStatement(sqlPattern)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, Integer.parseInt(genre));
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    numberOfEditions = rs.getInt("rowcount");
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
    public List<Edition> findAllEditionsFromTo(int recordsPerPage, int page, String genreFilter, String orderBy) throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();
        String order = orderBy;
        String genre = genreFilter;
        String sqlPattern;

        if (order == null || order.equals("")) {
            order = "id";
        }
        if (genre == null || genre.equals("0")) {
            genre = "all";
        }

        if (genre.equals("all")) {
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
                statement.setInt(1, Integer.parseInt(genre));
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
    public List<Edition> findAllEditionsFromTo(User user, boolean has, int recordsPerPage, int page, String genreFilter, String orderBy) throws DataNotFoundException {
        List<Edition> editionList = new ArrayList<>();
        String order = orderBy;
        String genre = genreFilter;
        String sqlPattern;

        if (order == null || order.equals("")) {
            order = "id";
        }
        if (genre == null || genre.equals("0")) {
            genre = "all";
        }

        if (genre.equals("all")) {
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
                statement.setInt(2, Integer.parseInt(genre));
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
        edition.setTitleEn(rs.getString("title_en"));
        edition.setTitleUa(rs.getString("title_ua"));
        edition.setImagePath(rs.getString("title_image"));
        edition.setGenreId(rs.getInt("genre_id"));
        edition.setPrice(rs.getInt("price"));

        return edition;
    }
}
