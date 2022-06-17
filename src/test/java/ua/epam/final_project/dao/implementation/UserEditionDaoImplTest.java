package ua.epam.final_project.dao.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.dao.TestSQLConnectionProvider;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.exception.DataNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Integration] Dao layer")
@Feature("Dao layer")
class UserEditionDaoImplTest {

    private static Connection connection;
    private static IUserEditionDao userEditionDao;
    private static IUserDao userDao;
    private static IEditionDao editionDao;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = TestSQLConnectionProvider.getConnectionToTestDB();
        userEditionDao = new UserEditionDaoImpl(connection);
        userDao = new UserDaoImpl(connection);
        editionDao = new EditionDaoImpl(connection);
    }

    @AfterAll
    static void resetAll() throws SQLException {
        TestSQLConnectionProvider.resetDb(connection);
    }

    @Test
    @DisplayName("Get number of user-edition pairs from DB")
    @Story("UserEditionDao")
    @Description("Method execute SQL query to get total number of user-edition pairs in DB")
    void getNumberOfRows() throws DataNotFoundException {
        assertTrue(userEditionDao.getNumberOfRows() > 0);
    }

    @Test
    @DisplayName("Find all user-edition pairs from DB")
    @Story("UserEditionDao")
    void findAllUserEdition() throws DataNotFoundException {
        assertTrue(userEditionDao.findAllUserEdition().size() > 0);
    }

    @Test
    @DisplayName("Find all user-edition pairs from DB by user")
    @Story("UserEditionDao")
    void findAllUserEditionByUser() throws DataNotFoundException {
        User user = userDao.findUserByLogin("admin");
        assertTrue(userEditionDao.findAllUserEditionByUser(user).size() > 0);
    }

    @Test
    @DisplayName("Find all user-edition pairs from DB by user and edition ")
    @Story("UserEditionDao")
    void findAllUserEditionByUserIdEditionId() throws DataNotFoundException {
        User user = userDao.findUserByLogin("admin");
        Edition edition = editionDao.findEditionById(1);

        assertTrue(userEditionDao.findAllUserEditionByUserIdEditionId(user, edition).size() > 0);
    }

    @Test
    @DisplayName("[Success] Insert new user-edition pair in DB")
    @Story("UserEditionDao")
    void insertUserEdition() throws DataNotFoundException {
        User user = userDao.findUserByLogin("user3");
        Edition edition = editionDao.findEditionById(1);

        assertTrue(userEditionDao.insertUserEdition(user, edition));
        assertTrue(userEditionDao.findAllUserEditionByUser(user).size() > 0);
    }

    @Test
    @DisplayName("Delete user-edition pair from DB")
    @Story("UserEditionDao")
    void deleteUserEdition() throws DataNotFoundException {
        User user = userDao.findUserByLogin("user3");
        Edition edition = editionDao.findEditionById(2);
        userEditionDao.insertUserEdition(user, edition);

        assertTrue(userEditionDao.deleteUserEdition(user, edition));
        assertEquals(0, userEditionDao.findAllUserEditionByUserIdEditionId(user, edition).size());
    }

    @Test
    @DisplayName("Delete user-edition pair from DB by edition")
    @Story("UserEditionDao")
    void deleteUserEditionByEdition() throws DataNotFoundException {
        User user = userDao.findUserByLogin("user3");
        Edition edition = editionDao.findEditionById(3);
        userEditionDao.insertUserEdition(user, edition);

        assertTrue(userEditionDao.deleteUserEditionByEdition(edition));
    }

    @Test
    @DisplayName("Delete user-edition pair from DB by user")
    @Story("UserEditionDao")
    void deleteUserEditionByUser() throws DataNotFoundException {
        User user = userDao.findUserByLogin("user3");
        Edition edition = editionDao.findEditionById(3);
        userEditionDao.insertUserEdition(user, edition);

        assertTrue(userEditionDao.deleteUserEditionByUser(user));
    }
}