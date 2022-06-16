package ua.epam.final_project.service.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@DisplayName("[Unit] Service layer")
@Feature("Service layer")
class UserServiceTest {

    @Mock
    private IUserDao userDao;
    @Mock
    private IUserEditionDao userEditionDao;

    private final IUserService userService;
    private final User user;


    UserServiceTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        userService = ServiceFactory.getUserService();

        user = new User("test_login", "test_pass", "test_email");

        Field userDaoField = userService.getClass().getDeclaredField("userDao");
        userDaoField.setAccessible(true);
        userDaoField.set(userService, userDao);

        Field userEditionDaoField = userService.getClass().getDeclaredField("userEditionDao");
        userEditionDaoField.setAccessible(true);
        userEditionDaoField.set(userService, userEditionDao);
    }

    @Test
    @DisplayName("Get number of users from DB")
    @Story("User service")
    void getNumberOfUsers() throws UnknownUserException, DataNotFoundException {
        Mockito.when(userDao.getNumberOfUsers())
                .thenReturn(3);

        assertEquals(3, userService.getNumberOfUsers());
    }

    @Test
    @DisplayName("[UnknownUserException] when Get number of users from DB")
    @Story("User service")
    void getNumberOfUsers_Exception() throws DataNotFoundException {
        Mockito.when(userDao.getNumberOfUsers())
                .thenThrow(DataNotFoundException.class);

        Assertions.assertThrows(UnknownUserException.class, userService::getNumberOfUsers);
    }

    @Test
    @DisplayName("Get list of users from DB")
    @Story("User service")
    void findAllUsers() throws DataNotFoundException, UnknownUserException {
        Mockito.when(userDao.findAllUsers())
                .thenReturn(Arrays.asList(new User(), new User()));

        assertEquals(2, userService.findAllUsers().size());
    }

    @Test
    @DisplayName("[UnknownUserException] Get list of users from DB")
    @Story("User service")
    void findAllUsers_Exception() throws DataNotFoundException {
        Mockito.when(userDao.findAllUsers())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserException.class, userService::findAllUsers);
    }

    @Test
    @DisplayName("Get list of users from DB with pagination")
    @Story("User service")
    void findAllUsersFromTo() throws DataNotFoundException, UnknownUserException {
        Mockito.when(userDao.findAllUsersFromTo(anyInt(), anyInt()))
                .thenReturn(Arrays.asList(new User(), new User()));

        assertEquals(2, userService.findAllUsersFromTo(1, 3).size());
    }

    @Test
    @DisplayName("[UnknownUserException] Get list of users from DB with pagination")
    @Story("User service")
    void findAllUsersFromTo_Exception() throws DataNotFoundException {
        Mockito.when(userDao.findAllUsersFromTo(anyInt(), anyInt()))
                .thenThrow(DataNotFoundException.class);

        Assertions.assertThrows(UnknownUserException.class, () ->
                userService.findAllUsersFromTo(1, 3));
    }

    @Test
    @DisplayName("Find user by login and password")
    @Story("User service")
    void findUserByLoginPassword() throws UnknownUserException, DataNotFoundException {
        Mockito.when(userDao.findUserByLoginPassword(any(), any()))
                .thenReturn(user);

        User userFromService = userService.findUserByLoginPassword("test_login", "test_pass");
        Assertions.assertNotNull(userFromService);
        assertEquals("test_login", userFromService.getLogin());
        assertEquals("test_pass", userFromService.getPassword());
    }

    @Test
    @DisplayName("[UnknownUserException] Find user by login and password")
    @Story("User service")
    void findUserByLoginPassword_Exception() throws DataNotFoundException {
        Mockito.when(userDao.findUserByLoginPassword(any(), any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserException.class, () -> userService.findUserByLoginPassword("test", "test"));
    }

    @Test
    @DisplayName("Find user by login")
    @Story("User service")
    void findUserByLogin_return_correct_user() throws UnknownUserException, DataNotFoundException {
        Mockito.when(userDao.findUserByLogin(any()))
                .thenReturn(user);

        assertEquals("test_login", userService.findUserByLogin("test_login").getLogin());
    }

    @Test
    @DisplayName("[Null] find user by wrong login")
    @Story("User service")
    @Description("Returns null if the user with this login was not found in the DB")
    void findUserByLogin_return_null() throws UnknownUserException {
        Mockito.when(userService.findUserByLogin("test"))
                .thenReturn(new User());

        assertNull(userService.findUserByLogin("Howard"));
    }

    @Test
    @DisplayName("[success] Add user to DB")
    @Story("User service")
    void insertUser_Success() {
        Mockito.when(userDao.insertUser(any()))
                .thenReturn(true);

        assertTrue(userService.insertUser(user));
    }

    @Test
    @DisplayName("[fail] Add user to DB")
    @Story("User service")
    void insertUser_Fail() {
        Mockito.when(userDao.insertUser(any()))
                .thenReturn(false);

        Assertions.assertFalse(userService.insertUser(user));
    }

    @Test
    @DisplayName("[success] Update user in DB")
    @Story("User service")
    void updateUser_Success() {
        Mockito.when(userDao.updateUser(any()))
                .thenReturn(true);

        assertTrue(userService.updateUser(user));
    }

    @Test
    @DisplayName("[fail] Update user in DB")
    @Story("User service")
    void updateUser_Fail() {
        Mockito.when(userDao.updateUser(any()))
                .thenReturn(false);

        Assertions.assertFalse(userService.updateUser(user));
    }

    @Test
    @DisplayName("[success] Delete user from DB")
    @Story("User service")
    void deleteUser_Success() {
        Mockito.when(userEditionDao.deleteUserEditionByUser(any()))
                .thenReturn(true);
        Mockito.when(userDao.deleteUser(any()))
                .thenReturn(true);

        assertTrue(userService.deleteUser(user));
    }

    @Test
    @DisplayName("[fail] Delete user from DB")
    @Story("User service")
    void deleteUser_Fail() {
        Mockito.when(userEditionDao.deleteUserEditionByUser(any())).thenReturn(false);
        Mockito.when(userDao.deleteUser(any()))
                .thenReturn(true);

        Assertions.assertFalse(userService.deleteUser(user));
    }
}