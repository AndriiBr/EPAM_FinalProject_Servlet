package ua.epam.final_project.dao.realisation;

import ua.epam.final_project.dao.IUserEditionDao;
import static ua.epam.final_project.dao.SQLConstant.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserEditionDao implements IUserEditionDao {
    private final Connection connection;

    public UserEditionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Map<Integer, Integer> findAllUserEdition() throws SQLException {
        Map<Integer, Integer> userEdition = new HashMap<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_FIND_ALL_USER_EDITION);

            while (rs.next()) {
                userEdition.put(rs.getInt("user_id"), rs.getInt("edition_id"));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return userEdition;
    }

    @Override
    public Map<Integer, Integer> findAllUserEditionByUserId(int userId) throws SQLException {
        Map<Integer, Integer> userEdition = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USER_EDITION_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userEdition.put(rs.getInt("user_id"), rs.getInt("edition_id"));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return userEdition;
    }

    @Override
    public Map<Integer, Integer> findAllUserEditionByUserIdEditionId(int userId, int editionId) throws SQLException {
        Map<Integer, Integer> userEdition = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USER_EDITION_BY_USER_ID_EDITION_ID)) {
            statement.setInt(1, userId);
            statement.setInt(2, editionId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userEdition.put(rs.getInt("user_id"), rs.getInt("edition_id"));
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return userEdition;
    }

    @Override
    public boolean insertUserEdition(int userId, int editionId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_EDITION)) {
            statement.setInt(1, userId);
            statement.setInt(2, editionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
        return true;
    }
}
