package ua.epam.final_project.dao.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.dao.IRoleDao;
import ua.epam.final_project.dao.TestSQLConnectionProvider;
import ua.epam.final_project.exception.DataNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Integration] Dao layer")
@Feature("Dao layer")
class RoleDaoImplTest {

    private static Connection connection;
    private static IRoleDao roleDao;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = TestSQLConnectionProvider.getConnectionToTestDB();
        roleDao = new RoleDaoImpl(connection);
    }

    @AfterAll
    static void resetAll() throws SQLException {
        TestSQLConnectionProvider.resetDb(connection);
    }

    @Test
    @DisplayName("Get number of roles from DB")
    @Story("RoleDao")
    @Description("Method execute SQL query to get total number of roles in DB")
    void getNumberOfRoles() throws DataNotFoundException {
        assertTrue(roleDao.getNumberOfRoles() >= 2);
    }

    @Test
    @DisplayName("Find all roles from DB")
    @Story("RoleDao")
    void findAllRoles() throws DataNotFoundException {
        assertTrue(roleDao.findAllRoles().size() >= 2);
    }

    @Test
    @DisplayName("Find single role in DB")
    @Story("RoleDao")
    void findRoleById() throws DataNotFoundException {
        assertEquals("admin", roleDao.findRoleById(1).getRoleEn());
        assertEquals("user", roleDao.findRoleById(2).getRoleEn());
        assertNull(roleDao.findRoleById(99));
    }
}