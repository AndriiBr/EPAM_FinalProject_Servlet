package ua.epam.final_project.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.dao.IRoleDao;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.util.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.epam.final_project.dao.SQLConstant.*;


public class RoleDao implements IRoleDao {
    private static final Logger logger = LogManager.getLogger(RoleDao.class);
    private static final String DB_TABLE = "role";
    private final Connection connection;

    public RoleDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getNumberOfRoles() throws DataNotFoundException {
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
    public List<Role> findAllRoles() throws DataNotFoundException {
        List<Role> genres = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            statement.setString(1, DB_TABLE);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                genres.add(extractRole(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return genres;
    }

    @Override
    public Role findRoleById(int id) throws DataNotFoundException {
        Role role = new Role();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                role = extractRole(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException();
        }
        return role;
    }

    /**
     * UTILITY METHOD
     * create Edition entity according to data from database
     */
    private Role extractRole(ResultSet rs) throws SQLException {
        Role role = new Role();

        role.setId(rs.getInt("id"));
        role.setRoleEn(rs.getString("roleEn"));
        role.setRoleUa(rs.getString("roleUa"));

        return role;
    }
}
