package ua.epam.final_project.dao.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.TestSQLConnectionProvider;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.DataNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Integration] Dao layer")
@Feature("Dao layer")
class UserDaoImplTest {

    private static Connection connection;
    private static IUserDao userDao;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = TestSQLConnectionProvider.getConnectionToTestDB();
        userDao = new UserDaoImpl(connection);
    }

    @AfterAll
    static void resetAll() throws SQLException {
        TestSQLConnectionProvider.resetDb(connection);
    }

    @Test
    @DisplayName("Get number of users from DB")
    @Story("UserDao")
    @Description("Method execute SQL query to get total number of users in DB")
    void getNumberOfUsers() throws DataNotFoundException {
        assertTrue(userDao.getNumberOfUsers() >= 3);
    }

    @Test
    @DisplayName("Find all users from DB")
    @Story("UserDao")
    void findAllUsers() throws DataNotFoundException {
        assertTrue(userDao.findAllUsers().size() > 0);
    }

    @Test
    @DisplayName("Find users from DB with pagination")
    @Story("UserDao")
    void findAllUsersFromTo() throws DataNotFoundException {
        assertEquals(3, userDao.findAllUsersFromTo(3, 1).size());
    }

    @Test
    @DisplayName("[Success] Find user from DB using correct login/password")
    @Story("UserDao")
    void findUserByLoginPassword_Success() throws DataNotFoundException {
        assertNotNull(userDao.findUserByLoginPassword("user2", "Pass1234"));
    }

    @Test
    @DisplayName("[Fail] Find user from DB using wrong login/password")
    @Story("UserDao")
    void findUserByLoginPassword_Fail_() throws DataNotFoundException {
        assertNull(userDao.findUserByLoginPassword("user99test", "Pass1234"));
        assertNull(userDao.findUserByLoginPassword("admin", "IncorrectPass"));
    }

    @Test
    @DisplayName("Find single user from DB by login")
    @Story("UserDao")
    void findUserByLogin() throws DataNotFoundException {
        assertNotNull(userDao.findUserByLogin("admin"));
        assertNull(userDao.findUserByLogin("userAdminSomeTextWrong"));
    }

    @Test
    @DisplayName("Find single user from DB by id")
    @Story("UserDao")
    void findUserByID() throws DataNotFoundException {
        assertNotNull(userDao.findUserById(1));
        assertNull(userDao.findUserById(9999));
    }

    @Test
    @DisplayName("[Success] Insert new user in DB")
    @Story("UserDao")
    void insertUser_success() {
        User user = new User("user4", "Test1234", "user3@gmail.com");

        assertTrue(userDao.insertUser(user));
    }

    @Test
    @DisplayName("[Fail] Insert new user in DB who already exists")
    @Story("UserDao")
    void insertUser_fail() {
        User user = new User("admin", "Pass1234", "admin@gmail.com");

        assertFalse(userDao.insertUser(user));
    }

    @Test
    @DisplayName("Update user in DB")
    @Story("UserDao")
    @Description("Check each field after update with provided user")
    void updateUser() throws DataNotFoundException {
        User userOriginal = new User("user5", "Test1234", "user5@gmail.com");
        userDao.insertUser(userOriginal);

        User userUpdated = prepareUserBeforeUpdate(userOriginal.getLogin());
        userDao.updateUser(userUpdated);

        User userFinal = userDao.findUserByLogin(userUpdated.getLogin());

        assertEquals(userUpdated.getLogin(), userFinal.getLogin());
        assertEquals(userUpdated.getEmail(), userFinal.getEmail());
        assertEquals(userUpdated.getName(), userFinal.getName());
        assertEquals(userUpdated.getUserImage(), userFinal.getUserImage());
        assertEquals(userUpdated.getBalance(), userFinal.getBalance());
        assertEquals(userUpdated.getRole(), userFinal.getRole());
    }

    @Test
    @DisplayName("Delete user from DB")
    @Story("UserDao")
    void deleteUser() throws DataNotFoundException {
        User user = new User("user6", "Pass1234", "user6@email.com");
        userDao.insertUser(user);

        assertNotNull(userDao.findUserByLogin(user.getLogin()));
        assertTrue(userDao.deleteUser(user));
        assertNull(userDao.findUserByLogin(user.getLogin()));
    }

    @Step("Modify user fields before 'updateUser' testing")
    private User prepareUserBeforeUpdate(String login) throws DataNotFoundException {
        User userUpdated = userDao.findUserByLogin(login);
        userUpdated.setLogin("testLogin");
        userUpdated.setPassword("testPass");
        userUpdated.setEmail("testEmail");
        userUpdated.setName("testName");
        userUpdated.setUserImage("testImage");
        userUpdated.setBalance(9999);
        userUpdated.setRole("1");

        return userUpdated;
    }
}