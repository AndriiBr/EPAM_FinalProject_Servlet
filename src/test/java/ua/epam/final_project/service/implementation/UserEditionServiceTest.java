package ua.epam.final_project.service.implementation;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.UserEdition;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownUserEditionPairException;
import ua.epam.final_project.service.IUserEditionService;
import ua.epam.final_project.service.ServiceFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("[Unit] Service layer")
@Feature("Service layer")
class UserEditionServiceTest {

    @Mock
    private IUserEditionDao userEditionDao;

    @Mock
    private IUserDao userDao;

    private final IUserEditionService userEditionService;
    private final User user;
    private final Edition edition;
    private final List<UserEdition> userEditionList;

    UserEditionServiceTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        userEditionService = ServiceFactory.getUserEditionService();
        this.user = new User();
        this.edition = new Edition();
        this.userEditionList = Arrays.asList(new UserEdition(), new UserEdition());

        Field userEditionDaoField = userEditionService.getClass().getDeclaredField("userEditionDao");
        userEditionDaoField.setAccessible(true);
        userEditionDaoField.set(userEditionService, userEditionDao);

        Field userDaoField = userEditionService.getClass().getDeclaredField("userDao");
        userDaoField.setAccessible(true);
        userDaoField.set(userEditionService, userDao);
    }


    @Test
    @DisplayName("Get number of user-edition pairs from DB")
    @Story("UserEdition service")
    void getNumberOfRows() throws DataNotFoundException, UnknownUserEditionPairException {
        Mockito.when(userEditionDao.getNumberOfRows())
                .thenReturn(27);

        assertEquals(27, userEditionService.getNumberOfRows());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get number of user-edition pairs from DB")
    @Story("UserEdition service")
    void getNumberOfRows_Exception() throws DataNotFoundException {
        Mockito.when(userEditionDao.getNumberOfRows())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, userEditionService::getNumberOfRows);
    }

    @Test
    @DisplayName("Get All user-edition pairs from DB")
    @Story("UserEdition service")
    void findAllUserEdition() throws DataNotFoundException, UnknownUserEditionPairException {
        Mockito.when(userEditionDao.findAllUserEdition())
                .thenReturn(userEditionList);

        assertEquals(2, userEditionService.findAllUserEdition().size());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get All user-edition pairs from DB")
    @Story("UserEdition service")
    void findAllUserEdition_Exception() throws DataNotFoundException {
        Mockito.when(userEditionDao.findAllUserEdition())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, userEditionService::findAllUserEdition);
    }

    @Test
    @DisplayName("Get All user-edition pairs from DB by user")
    @Story("UserEdition service")
    void findAllUserEditionByUser() throws UnknownUserEditionPairException, DataNotFoundException {
        Mockito.when(userEditionDao.findAllUserEditionByUser(any()))
                .thenReturn(userEditionList);

        assertEquals(2, userEditionService.findAllUserEditionByUser(user).size());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get All user-edition pairs from DB by user")
    @Story("UserEdition service")
    void findAllUserEditionByUser_Exception() throws DataNotFoundException {
        Mockito.when(userEditionDao.findAllUserEditionByUser(any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, () ->
                userEditionService.findAllUserEditionByUser(user));
    }

    @Test
    @DisplayName("Get All user-edition pairs from DB by user/edition ids")
    @Story("UserEdition service")
    void findAllUserEditionByUserIdEditionId() throws UnknownUserEditionPairException, DataNotFoundException {
        Mockito.when(userEditionDao.findAllUserEditionByUserIdEditionId(any(), any()))
                .thenReturn(userEditionList);

        assertEquals(2, userEditionService.findAllUserEditionByUserIdEditionId(user, edition).size());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get All user-edition pairs from DB by user/edition ids")
    @Story("UserEdition service")
    void findAllUserEditionByUserIdEditionId_Exception() throws DataNotFoundException {
        Mockito.when(userEditionDao.findAllUserEditionByUserIdEditionId(any(), any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, () ->
                userEditionService.findAllUserEditionByUserIdEditionId(user, edition));
    }

    @Test
    @DisplayName("[success] Add user-edition pair to DB")
    @Story("UserEdition service")
    void insertUserEdition_Success() {
        Mockito.when(userDao.updateUser(any())).thenReturn(true);
        Mockito.when(userEditionDao.insertUserEdition(any(), any()))
                .thenReturn(true);

        assertTrue(userEditionService.insertUserEdition(user, edition));
    }

    @Test
    @DisplayName("[fail] Add user-edition pair to DB")
    @Story("UserEdition service")
    void insertUserEdition_Fail() {
        Mockito.when(userDao.updateUser(any())).thenReturn(false);
        Mockito.when(userEditionDao.insertUserEdition(any(), any()))
                .thenReturn(true);

        assertFalse(userEditionService.insertUserEdition(user, edition));
    }

    @Test
    @DisplayName("[success] Delete user-edition pair from DB by user/edition")
    @Story("UserEdition service")
    void deleteUserEdition_Success() {
        Mockito.when(userEditionDao.deleteUserEdition(any(), any()))
                .thenReturn(true);

        assertTrue(userEditionService.deleteUserEdition(user, edition));
    }

    @Test
    @DisplayName("[fail] Delete user-edition pair from DB by user/edition")
    @Story("UserEdition service")
    void deleteUserEdition_Fail() {
        Mockito.when(userEditionDao.deleteUserEdition(any(), any()))
                .thenReturn(false);

        assertFalse(userEditionService.deleteUserEdition(user, edition));
    }

    @Test
    @DisplayName("[success] Delete user-edition pair from DB by edition")
    @Story("UserEdition service")
    void deleteUserEditionByEdition_Success() {
        Mockito.when(userEditionDao.deleteUserEditionByEdition(any()))
                .thenReturn(true);

        assertTrue(userEditionService.deleteUserEditionByEdition(edition));
    }

    @Test
    @DisplayName("[fail] Delete user-edition pair from DB by edition")
    @Story("UserEdition service")
    void deleteUserEditionByEdition_Fail() {
        Mockito.when(userEditionDao.deleteUserEditionByEdition(any()))
                .thenReturn(false);

        assertFalse(userEditionService.deleteUserEditionByEdition(edition));
    }

    @Test
    @DisplayName("[success] Delete user-edition pair from DB by user")
    @Story("UserEdition service")
    void deleteUserEditionByUser_Success() {
        Mockito.when(userEditionDao.deleteUserEditionByUser(any()))
                .thenReturn(true);

        assertTrue(userEditionService.deleteUserEditionByUser(user));
    }

    @Test
    @DisplayName("[fail] Delete user-edition pair from DB by user")
    @Story("UserEdition service")
    void deleteUserEditionByUser_Fail() {
        Mockito.when(userEditionDao.deleteUserEditionByUser(any()))
                .thenReturn(false);

        assertFalse(userEditionService.deleteUserEditionByUser(user));
    }
}