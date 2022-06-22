package ua.epam.final_project.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.UserEdition;

import static ua.epam.final_project.dao.SQLConstant.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserEditionDaoImpl implements IUserEditionDao {
    private static final Logger logger = LogManager.getLogger(UserEditionDaoImpl.class);
    private final Connection connection;

    public UserEditionDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Integer getNumberOfRows() throws DataNotFoundException {
        int numberOfRows = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_NUMBER_OF_USER_EDITIONS);

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
    public List<UserEdition> findAllUserEdition() throws DataNotFoundException {
        List<UserEdition> userEditionList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_USER_EDITIONS);

            while (rs.next()) {
                userEditionList.add(extractUserEdition(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return userEditionList;
    }

    @Override
    public List<UserEdition> findAllUserEditionByUser(User user) throws DataNotFoundException {
        List<UserEdition> userEditionList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USER_EDITION_BY_USER_ID)) {
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userEditionList.add(extractUserEdition(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return userEditionList;
    }

    @Override
    public List<UserEdition> findAllUserEditionByUserIdEditionId(User user, Edition edition) throws DataNotFoundException {
        List<UserEdition> userEditionList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USER_EDITION_BY_USER_ID_EDITION_ID)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, edition.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userEditionList.add(extractUserEdition(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return userEditionList;
    }

    @Override
    public boolean insertUserEdition(User user, Edition edition) {
        return insertDeleteCombination(user, edition, SQL_INSERT_USER_EDITION);
    }

    @Override
    public boolean deleteUserEdition(User user, Edition edition) {
        return insertDeleteCombination(user, edition, SQL_DELETE_USER_EDITION);
    }

    @Override
    public boolean deleteUserEditionByEdition(Edition edition) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_EDITION_BY_EDITION)) {
            statement.setInt(1, edition.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUserEditionByUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_EDITION_BY_USER)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    /**
     * UTILITY METHOD
     *
     * @param user       user entity
     * @param edition    edition entity
     * @param sqlRequest sql request
     * @return TRUE - if operation was success. FALSE - if not.
     */
    private boolean insertDeleteCombination(User user, Edition edition, String sqlRequest) {

        try (PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            List<UserEdition> userEditionFromDB = findAllUserEditionByUserIdEditionId(user, edition);

            if (!userEditionFromDB.isEmpty()) {
                return false;
            }

            statement.setInt(1, user.getId());
            statement.setInt(2, edition.getId());
            statement.executeUpdate();
        } catch (SQLException | DataNotFoundException e) {
            logger.error(e);
            return false;
        }

        return true;
    }

    /**
     * UTILITY METHOD
     * Creates single entity user-edition
     *
     * @param rs - Result set from DB
     * @return userEdition entity
     */
    private UserEdition extractUserEdition(ResultSet rs) throws SQLException {
        UserEdition userEdition = new UserEdition();

        userEdition.setUserId(rs.getInt("user_id"));
        userEdition.setEditionId(rs.getInt("edition_id"));

        return userEdition;
    }
}
