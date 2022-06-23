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
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.entity.dto.UserDtoMapper;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.ServiceFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@DisplayName("[Unit] Service layer")
@Feature("Service layer")
class EditionServiceTest {

    @Mock
    private IEditionDao editionDao;

    @Mock
    private IUserEditionDao userEditionDao;

    private final IEditionService editionService;
    private final UserDto userDto;
    private final Edition edition;
    private final List<Edition> userList;

    public EditionServiceTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        editionService = ServiceFactory.getEditionService();

        User user = new User("Herald", "1234", "herald@gmail.com");
        userDto = UserDtoMapper.convertEntityIntoDto(user);
        edition = new Edition();
        userList = Arrays.asList(new Edition(), new Edition(), new Edition());

        Field editionDaoField = editionService.getClass().getDeclaredField("editionDao");
        editionDaoField.setAccessible(true);
        editionDaoField.set(editionService, editionDao);

        Field userEditionDaoField = editionService.getClass().getDeclaredField("userEditionDao");
        userEditionDaoField.setAccessible(true);
        userEditionDaoField.set(editionService, userEditionDao);
    }

    @Test
    @DisplayName("Get number of editions from DB: 1 param")
    @Story("Edition service")
    @Description("Method with sorting: 1 param - 'String filter'")
    void getNumberOfEditions() throws DataNotFoundException, UnknownEditionException {
        Mockito.when(editionDao.getNumberOfEditions(anyInt()))
                .thenReturn(99);

        assertEquals(99, editionService.getNumberOfEditions(77));
    }

    @Test
    @DisplayName("[UnknownEditionException] Get number of editions from DB: 1 param")
    @Story("Edition service")
    @Description("Method with sorting: 1 param - 'String filter'")
    void getNumberOfEditions_Exception() throws DataNotFoundException {
        Mockito.when(editionDao.getNumberOfEditions(anyInt()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownEditionException.class, () -> editionService.getNumberOfEditions(999));
    }

    @Test
    @DisplayName("Get number of editions from DB: 3 param")
    @Story("Edition service")
    @Description("Method with sorting: 3 params - 'User user, String filter, boolean has'")
    void getNumberOfEditions_2() throws DataNotFoundException, UnknownEditionException {
        Mockito.when(editionDao.getNumberOfEditions(any(), anyBoolean(), anyInt()))
                .thenReturn(88);

        assertEquals(88, editionService.getNumberOfEditions(userDto, true,345));
    }

    @Test
    @DisplayName("[UnknownEditionException] Get number of editions from DB: 3 param")
    @Story("Edition service")
    @Description("Method with sorting: 3 params - 'User user, String filter, boolean has'")
    void getNumberOfEditions_2_Exception() throws DataNotFoundException {
        Mockito.when(editionDao.getNumberOfEditions(any(), anyBoolean(), anyInt()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownEditionException.class, () ->
                editionService.getNumberOfEditions(userDto, true,435));
    }

    @Test
    @DisplayName("Get all editions from DB")
    @Story("Edition service")
    void findAllEditions() throws DataNotFoundException, UnknownEditionException {
        Mockito.when(editionDao.findAllEditions()).thenReturn(userList);

        assertEquals(3, editionService.findAllEditions().size());
    }

    @Test
    @DisplayName("[UnknownEditionException] Get all editions from DB")
    @Story("Edition service")
    void findAllEditions_Exception() throws DataNotFoundException {
        Mockito.when(editionDao.findAllEditions())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownEditionException.class, editionService::findAllEditions);
    }

    @Test
    @DisplayName("Get all editions from DB with pagination: 4 params")
    @Story("Edition service")
    @Description("Method with pagination: 4 params - 'int recordsPerPage, int page, String genreFilter, String orderBy'")
    void findAllEditionsFromTo() throws UnknownEditionException, DataNotFoundException {
        Mockito.when(editionDao.findAllEditionsFromTo(anyInt(), anyInt(), anyInt(), any()))
                .thenReturn(userList);

        assertEquals(3, editionService
                .findAllEditionsFromTo(5, 1, 33, "").size());
    }

    @Test
    @DisplayName("[UnknownEditionException] Get all editions from DB with pagination: 4 params")
    @Story("Edition service")
    @Description("Method with pagination: 4 params - 'int recordsPerPage, int page, String genreFilter, String orderBy'")
    void findAllEditionsFromTo_Exception() throws  DataNotFoundException {
        Mockito.when(editionDao.findAllEditionsFromTo(anyInt(), anyInt(), anyInt(), any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownEditionException.class, () ->
                editionService.findAllEditionsFromTo(5, 1, 333, ""));
    }

    @Test
    @DisplayName("Get all editions from DB with pagination: 6 params")
    @Story("Edition service")
    @Description("Method with pagination: 6 params - 'User user, boolean has, int recordsPerPage, int page, String genreFilter, String orderBy'")
    void findAllEditionsFromTo_2() throws UnknownEditionException, DataNotFoundException {
        Mockito.when(editionDao.findAllEditionsFromTo(any(), anyBoolean(), anyInt(), anyInt(), anyInt(), any()))
                .thenReturn(userList);

        assertEquals(3, editionService
                .findAllEditionsFromTo(userDto, true,5, 1, 345, "").size());
    }

    @Test
    @DisplayName("[UnknownEditionException] Get all editions from DB with pagination: 6 params")
    @Story("Edition service")
    @Description("Method with pagination: 6 params - 'User user, boolean has, int recordsPerPage, int page, String genreFilter, String orderBy'")
    void findAllEditionsFromTo_2_Exception() throws DataNotFoundException {
        Mockito.when(editionDao.findAllEditionsFromTo(any(), anyBoolean(), anyInt(), anyInt(), anyInt(), any()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownEditionException.class, () -> 
                editionService
                        .findAllEditionsFromTo(userDto, true,5, 1, 436, ""));
    }
    

    @Test
    @DisplayName("Get single edition from DB by id")
    @Story("Edition service")
    void findEditionById() throws DataNotFoundException, UnknownEditionException {
        Mockito.when(editionDao.findEditionById(anyInt()))
                .thenReturn(edition);

        Assertions.assertNotNull(editionService.findEditionById(1));
    }

    @Test
    @DisplayName("[UnknownEditionException] Get single edition from DB by id")
    @Story("Edition service")
    void findEditionById_Exception() throws DataNotFoundException {
        Mockito.when(editionDao.findEditionById(anyInt()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownEditionException.class, () -> editionService.findEditionById(1));
    }

    @Test
    @DisplayName("[success] Add edition to DB")
    @Story("Edition service")
    void insertNewEdition_Success() {
        Mockito.when(editionDao.insertNewEdition(any()))
                .thenReturn(true);

        assertTrue(editionService.insertNewEdition(edition));
    }

    @Test
    @DisplayName("[fail] Add edition to DB")
    @Story("Edition service")
    void insertNewEdition_Fail() {
        Mockito.when(editionDao.insertNewEdition(any()))
                .thenReturn(false);

        Assertions.assertFalse(editionService.insertNewEdition(edition));
    }

    @Test
    @DisplayName("[success] Update edition in DB")
    @Story("Edition service")
    void updateEdition_Success() {
        Mockito.when(editionDao.updateEdition(any()))
                .thenReturn(true);

        assertTrue(editionService.updateEdition(edition));
    }

    @Test
    @DisplayName("[fail] Update edition in DB")
    @Story("Edition service")
    void updateEdition_Fail() {
        Mockito.when(editionDao.updateEdition(any()))
                .thenReturn(false);

        Assertions.assertFalse(editionService.updateEdition(edition));
    }

    @Test
    @DisplayName("[success] Delete edition from DB")
    @Story("Edition service")
    void deleteEdition_Success() {
        Mockito.when(userEditionDao.deleteUserEditionByEdition(any()))
                .thenReturn(true);
        Mockito.when(editionDao.deleteEdition(any()))
                .thenReturn(true);

        assertTrue(editionService.deleteEdition(edition));
    }

    @Test
    @DisplayName("[fail] Delete edition from DB")
    @Story("Edition service")
    void deleteEdition_Fail() {
        Mockito.when(userEditionDao.deleteUserEditionByEdition(any()))
                .thenReturn(false);
        Mockito.when(editionDao.deleteEdition(any()))
                .thenReturn(true);

        Assertions.assertFalse(editionService.deleteEdition(edition));
    }
}