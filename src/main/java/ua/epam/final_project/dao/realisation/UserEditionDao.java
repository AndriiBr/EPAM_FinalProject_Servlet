package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;
import ua.epam.final_project.util.entity.UserEdition;

import static ua.epam.final_project.dao.SQLConstant.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserEditionDao implements IUserEditionDao {
    private final Connection connection;

    public UserEditionDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<UserEdition> findAllUserEdition() throws SQLException {
        List<UserEdition> userEditionList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_USER_EDITION);

            while (rs.next()) {
                userEditionList.add(extractUserEdition(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return userEditionList;
    }

    @Override
    public List<UserEdition> findAllUserEditionByUserId(int userId) throws SQLException {
        List<UserEdition> userEditionList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USER_EDITION_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userEditionList.add(extractUserEdition(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return userEditionList;
    }

    @Override
    public List<UserEdition> findAllUserEditionByUserIdEditionId(int userId, int editionId) throws SQLException {
        List<UserEdition> userEditionList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USER_EDITION_BY_USER_ID_EDITION_ID)) {
            statement.setInt(1, userId);
            statement.setInt(2, editionId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userEditionList.add(extractUserEdition(rs));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return userEditionList;
    }

    @Override
    public boolean insertUserEdition(User user, Edition edition) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_EDITION)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, edition.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
        return true;
    }

    @Override
    public boolean deleteUserEdition(User user, Edition edition) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_EDITION)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, edition.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return true;
    }

    /**
     * create single entity user-edition
     * @param rs - Result set from DB
     * @return userEdition entity
     */
    private UserEdition extractUserEdition(ResultSet rs) {
        UserEdition userEdition = new UserEdition();
        try {
            userEdition.setUserId(rs.getInt("user_id"));
            userEdition.setEditionId(rs.getInt("edition_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userEdition;
    }
}
