package ua.epam.final_project.service.implementation;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.dao.IUserEditionDao;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.ServiceFactory;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Service layer")
@Feature("Service layer")
class EditionServiceTest {

    @Mock
    private IEditionDao editionDao;

    @Mock
    private IUserEditionDao userEditionDao;

    private final IEditionService editionService;

    public EditionServiceTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        editionService = ServiceFactory.getEditionService();

        Field editionDaoField = editionService.getClass().getDeclaredField("editionDao");
        editionDaoField.setAccessible(true);
        editionDaoField.set(editionService, editionDao);

        Field userEditionDaoField = editionService.getClass().getDeclaredField("userEditionDao");
        userEditionDaoField.setAccessible(true);
        userEditionDaoField.set(editionService, userEditionDao);
    }

    @Test
    @DisplayName("Get number of editions from DB")
    @Story("Edition service")
    void getNumberOfEditions() throws DataNotFoundException, UnknownEditionException {
        Mockito.when(editionDao.getNumberOfEditions(any())).thenReturn(99);

        assertEquals(99, editionService.getNumberOfEditions("science"));
    }

    @Test
    void testGetNumberOfEditions() {
    }

    @Test
    void findAllEditions() {
    }

    @Test
    void findAllEditionsFromTo() {
    }

    @Test
    void testFindAllEditionsFromTo() {
    }

    @Test
    void findEditionById() {
    }

    @Test
    void insertNewEdition() {
    }

    @Test
    void updateEdition() {
    }

    @Test
    void deleteEdition() {
    }
}