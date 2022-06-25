package ua.epam.final_project.service.implementation;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.UserEdition;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.DataBaseConnectionException;
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

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PostgresDaoFactory daoFactory;
    @InjectMocks
    private UserEditionService userEditionService;


    private final UserDto userDto;
    private final Edition edition;
    private final List<UserEdition> userEditionList;

    UserEditionServiceTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        this.userDto = new UserDto(new User());
        this.edition = new Edition();
        this.userEditionList = Arrays.asList(new UserEdition(), new UserEdition());
    }

    @BeforeEach
    public void setUp() throws DataBaseConnectionException {
        Mockito.doNothing().when(daoFactory).getConnection();
        Mockito.doNothing().when(daoFactory).releaseConnection();
        Mockito.doNothing().when(daoFactory).beginTransaction();
        Mockito.doNothing().when(daoFactory).commitTransaction();
    }


    @Test
    @DisplayName("Get number of user-edition pairs from DB")
    @Story("UserEdition service")
    void getNumberOfRows() throws UnknownUserEditionPairException, DataNotFoundException {
        Mockito.when(daoFactory.getUserEditionDao().getNumberOfRows())
                .thenReturn(27);

        assertEquals(27, userEditionService.getNumberOfRows());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get number of user-edition pairs from DB")
    @Story("UserEdition service")
    void getNumberOfRows_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserEditionDao().getNumberOfRows())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, userEditionService::getNumberOfRows);
    }

    @Test
    @DisplayName("Get All user-edition pairs from DB")
    @Story("UserEdition service")
    void findAllUserEdition() throws DataNotFoundException, UnknownUserEditionPairException {
        Mockito.when(daoFactory.getUserEditionDao().findAllUserEdition())
                .thenReturn(userEditionList);

        assertEquals(2, userEditionService.findAllUserEdition().size());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get All user-edition pairs from DB")
    @Story("UserEdition service")
    void findAllUserEdition_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserEditionDao().findAllUserEdition())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, userEditionService::findAllUserEdition);
    }

    @Test
    @DisplayName("Get All user-edition pairs from DB by user")
    @Story("UserEdition service")
    void findAllUserEditionByUser() throws UnknownUserEditionPairException, DataNotFoundException {
        Mockito.when(daoFactory.getUserEditionDao().findAllUserEditionByUser(any()))
                .thenReturn(userEditionList);

        assertEquals(2, userEditionService.findAllUserEditionByUser(userDto).size());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get All user-edition pairs from DB by user")
    @Story("UserEdition service")
    void findAllUserEditionByUser_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserEditionDao().findAllUserEditionByUser(any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, () ->
                userEditionService.findAllUserEditionByUser(userDto));
    }

    @Test
    @DisplayName("Get All user-edition pairs from DB by user/edition ids")
    @Story("UserEdition service")
    void findAllUserEditionByUserIdEditionId() throws UnknownUserEditionPairException, DataNotFoundException {
        Mockito.when(daoFactory.getUserEditionDao().findAllUserEditionByUserIdEditionId(any(), any()))
                .thenReturn(userEditionList);

        assertEquals(2, userEditionService.findAllUserEditionByUserIdEditionId(userDto, edition).size());
    }

    @Test
    @DisplayName("[UnknownUserEditionPairException] Get All user-edition pairs from DB by user/edition ids")
    @Story("UserEdition service")
    void findAllUserEditionByUserIdEditionId_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getUserEditionDao().findAllUserEditionByUserIdEditionId(any(), any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownUserEditionPairException.class, () ->
                userEditionService.findAllUserEditionByUserIdEditionId(userDto, edition));
    }

    @Test
    @DisplayName("[success] Add user-edition pair to DB")
    @Story("UserEdition service")
    void insertUserEdition_Success() {
        Mockito.when(daoFactory.getUserDao().updateUser(any())).thenReturn(true);
        Mockito.when(daoFactory.getUserEditionDao().insertUserEdition(any(), any()))
                .thenReturn(true);

        assertTrue(userEditionService.insertUserEdition(userDto, edition));
    }

    @Test
    @DisplayName("[fail] Add user-edition pair to DB")
    @Story("UserEdition service")
    void insertUserEdition_Fail() {
        Mockito.when(daoFactory.getUserDao().updateUser(any())).thenReturn(false);
        Mockito.when(daoFactory.getUserEditionDao().insertUserEdition(any(), any()))
                .thenReturn(true);

        assertFalse(userEditionService.insertUserEdition(userDto, edition));
    }

    @Test
    @DisplayName("[success] Delete user-edition pair from DB by user/edition")
    @Story("UserEdition service")
    void deleteUserEdition_Success() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEdition(any(), any()))
                .thenReturn(true);

        assertTrue(userEditionService.deleteUserEdition(userDto, edition));
    }

    @Test
    @DisplayName("[fail] Delete user-edition pair from DB by user/edition")
    @Story("UserEdition service")
    void deleteUserEdition_Fail() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEdition(any(), any()))
                .thenReturn(false);

        assertFalse(userEditionService.deleteUserEdition(userDto, edition));
    }

    @Test
    @DisplayName("[success] Delete user-edition pair from DB by edition")
    @Story("UserEdition service")
    void deleteUserEditionByEdition_Success() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEditionByEdition(any()))
                .thenReturn(true);

        assertTrue(userEditionService.deleteUserEditionByEdition(edition));
    }

    @Test
    @DisplayName("[fail] Delete user-edition pair from DB by edition")
    @Story("UserEdition service")
    void deleteUserEditionByEdition_Fail() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEditionByEdition(any()))
                .thenReturn(false);

        assertFalse(userEditionService.deleteUserEditionByEdition(edition));
    }

    @Test
    @DisplayName("[success] Delete user-edition pair from DB by user")
    @Story("UserEdition service")
    void deleteUserEditionByUser_Success() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEditionByUser(any()))
                .thenReturn(true);

        assertTrue(userEditionService.deleteUserEditionByUser(userDto));
    }

    @Test
    @DisplayName("[fail] Delete user-edition pair from DB by user")
    @Story("UserEdition service")
    void deleteUserEditionByUser_Fail() {
        Mockito.when(daoFactory.getUserEditionDao().deleteUserEditionByUser(any()))
                .thenReturn(false);

        assertFalse(userEditionService.deleteUserEditionByUser(userDto));
    }
}