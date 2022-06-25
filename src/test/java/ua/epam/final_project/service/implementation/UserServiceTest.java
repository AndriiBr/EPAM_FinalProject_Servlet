package ua.epam.final_project.service.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.entity.dto.UserDtoMapper;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownUserException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@DisplayName("[Unit] Service layer")
@Feature("Service layer")
class UserServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PostgresDaoFactory daoFactory;
    @InjectMocks
    private UserService userService;

    private final User user;
    private final UserDto userDto;


    UserServiceTest() {
        MockitoAnnotations.openMocks(this);

        user = new User("test_login", "test_pass", "test_email");
        userDto = UserDtoMapper.convertEntityIntoDto(user);
    }

    @BeforeEach
    public void setUp() throws DataBaseConnectionException {
        Mockito.doNothing().when(daoFactory).getConnection();
        Mockito.doNothing().when(daoFactory).releaseConnection();
        Mockito.doNothing().when(daoFactory).beginTransaction();
        Mockito.doNothing().when(daoFactory).commitTransaction();
    }

    @Test
    @DisplayName("Get number of users from DB")
    @Story("User service")
    void getNumberOfUsers() throws DataNotFoundException, UnknownUserException {
        Mockito.when(daoFactory.getUserDao().getNumberOfUsers()).thenReturn(3);

        assertEquals(3, userService.getNumberOfUsers());
    }

    @Test
    @DisplayName("[UnknownUserException] when Get number of users from DB")
    @Story("User service")
    void getNumberOfUsers_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().getNumberOfUsers())
                .thenThrow(DataNotFoundException.class);

        Assertions.assertThrows(UnknownUserException.class, userService::getNumberOfUsers);
    }

    @Test
    @DisplayName("Get list of users from DB")
    @Story("User service")
    void findAllUsers() throws DataNotFoundException, UnknownUserException {
        Mockito.when(daoFactory.getUserDao().findAllUsers())
                .thenReturn(Arrays.asList(new User(), new User()));

        assertEquals(2, userService.findAllUsers().size());
    }

    @Test
    @DisplayName("[UnknownUserException] Get list of users from DB")
    @Story("User service")
    void findAllUsers_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findAllUsers())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserException.class, userService::findAllUsers);
    }

    @Test
    @DisplayName("Get list of users from DB with pagination")
    @Story("User service")
    void findAllUsersFromTo() throws DataNotFoundException, UnknownUserException {
        Mockito.when(daoFactory.getUserDao().findAllUsersFromTo(anyInt(), anyInt()))
                .thenReturn(Arrays.asList(new User(), new User()));

        assertEquals(2, userService.findAllUsersFromTo(1, 3).size());
    }

    @Test
    @DisplayName("[UnknownUserException] Get list of users from DB with pagination")
    @Story("User service")
    void findAllUsersFromTo_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findAllUsersFromTo(anyInt(), anyInt()))
                .thenThrow(DataNotFoundException.class);

        Assertions.assertThrows(UnknownUserException.class, () ->
                userService.findAllUsersFromTo(1, 3));
    }

    @Test
    @DisplayName("Find user by login and password")
    @Story("User service")
    void findUserByLoginPassword() throws UnknownUserException, DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findUserByLoginPassword(any(), any()))
                .thenReturn(user);

        UserDto userFromService = userService.findUserByLoginPassword("test_login", "test_pass");
        Assertions.assertNotNull(userFromService);
        assertEquals("test_login", userFromService.getLogin());
    }

    @Test
    @DisplayName("[UnknownUserException] Find user by login and password")
    @Story("User service")
    void findUserByLoginPassword_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findUserByLoginPassword(any(), any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserException.class, () -> userService.findUserByLoginPassword("test", "test"));
    }

    @Test
    @DisplayName("Find user by login")
    @Story("User service")
    void findUserByLogin_return_correct_user() throws UnknownUserException, DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findUserByLogin(any()))
                .thenReturn(user);

        assertEquals("test_login", userService.findUserByLogin("test_login").getLogin());
    }

    @Test
    @DisplayName("[Null] find user by wrong login")
    @Story("User service")
    @Description("Returns null if the user with this login was not found in the DB")
    void findUserByLogin_return_null() throws UnknownUserException, DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findUserByLogin("Howard"))
                .thenReturn(null);

        assertNull(userService.findUserByLogin("Howard"));
    }

    @Test
    @DisplayName("Find user by id")
    @Story("User service")
    void findUserById_return_correct_user() throws UnknownUserException, DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findUserById(anyInt()))
                .thenReturn(user);

        assertEquals("test_login", userService.findUserById(1).getLogin());
    }

    @Test
    @DisplayName("[Null] find user by wrong id")
    @Story("User service")
    @Description("Returns null if the user with this login was not found in the DB")
    void findUserById_return_null() throws UnknownUserException, DataNotFoundException {
        Mockito.when(daoFactory.getUserDao().findUserById(anyInt()))
                .thenReturn(null);

        assertNull(userService.findUserById(-1));
    }

    @Test
    @DisplayName("[success] Add user to DB")
    @Story("User service")
    void insertUser_Success() {
        Mockito.when(daoFactory.getUserDao().insertUser(any()))
                .thenReturn(true);

        assertTrue(userService.insertUser(userDto));
    }

    @Test
    @DisplayName("[fail] Add user to DB")
    @Story("User service")
    void insertUser_Fail() {
        Mockito.when(daoFactory.getUserDao().insertUser(any()))
                .thenReturn(false);

        Assertions.assertFalse(userService.insertUser(userDto));
    }

    @Test
    @DisplayName("[success] Update user in DB")
    @Story("User service")
    void updateUser_Success() {
        Mockito.when(daoFactory.getUserDao().updateUser(any()))
                .thenReturn(true);

        assertTrue(userService.updateUser(userDto));
    }

    @Test
    @DisplayName("[fail] Update user in DB")
    @Story("User service")
    void updateUser_Fail() {
        Mockito.when(daoFactory.getUserDao().updateUser(any()))
                .thenReturn(false);

        Assertions.assertFalse(userService.updateUser(userDto));
    }

    @Test
    @DisplayName("[success] Delete user from DB")
    @Story("User service")
    void deleteUser_Success() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEditionByUser(any()))
                .thenReturn(true);
        Mockito.when(daoFactory.getUserDao().deleteUser(any()))
                .thenReturn(true);

        assertTrue(userService.deleteUser(new UserDto(user)));
    }

    @Test
    @DisplayName("[fail] Delete user from DB")
    @Story("User service")
    void deleteUser_Fail() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEditionByUser(any())).thenReturn(false);
        Mockito.when(daoFactory.getUserDao().deleteUser(any()))
                .thenReturn(true);

        Assertions.assertFalse(userService.deleteUser(new UserDto(user)));
    }
}