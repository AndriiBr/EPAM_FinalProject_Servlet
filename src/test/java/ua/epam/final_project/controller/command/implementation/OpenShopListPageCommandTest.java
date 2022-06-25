package ua.epam.final_project.controller.command.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class OpenShopListPageCommandTest {


    private SessionRequestContent content;
    @Mock
    private IEditionService editionService;
    @Mock
    private IGenreService genreService;

    private final OpenShopListPageCommand openShopListPageCommand;
    private final UserDto userDto;

    public OpenShopListPageCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        openShopListPageCommand = new OpenShopListPageCommand();
        User user = new User();
        userDto = new UserDto(user);

        Field editionServiceField = openShopListPageCommand.getClass().getDeclaredField("editionService");
        editionServiceField.setAccessible(true);
        editionServiceField.set(openShopListPageCommand, editionService);

        Field genreServiceField = openShopListPageCommand.getClass().getDeclaredField("genreService");
        genreServiceField.setAccessible(true);
        genreServiceField.set(openShopListPageCommand, genreService);
    }

    @BeforeEach
    public void setUp() {
        content = mock(SessionRequestContent.class, RETURNS_DEEP_STUBS);
    }

    @AfterEach
    public void finish() {
        content = null;
    }

    @Test
    @DisplayName("OpenShopListPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with direction:Forward and not null page")
    void execute() throws UnknownEditionException, UnknownGenreException {
        Mockito.when(editionService.getNumberOfEditions(any(), anyBoolean(), anyInt())).thenReturn(4);
        Mockito.when(editionService.getNumberOfEditions(anyInt())).thenReturn(3);
        Mockito.when(genreService.findAllGenres()).thenReturn(new ArrayList<>());
        ExecutionResult result = openShopListPageCommand.execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertNotNull(result.getPage());
        assertEquals(ResourceConfiguration.getInstance().getPage("shop.edition_list"), result.getPage());
    }

    @Test
    @DisplayName("OpenShopListPageCommand execute method check with user")
    @Story("Command")
    @Description("Should return ExecutionResult entity with direction:Forward and not null page")
    void execute_with_user() throws UnknownEditionException, UnknownGenreException {
        Mockito.when(editionService.getNumberOfEditions(any(), anyBoolean(), anyInt())).thenReturn(4);
        Mockito.when(editionService.getNumberOfEditions(anyInt())).thenReturn(3);
        Mockito.when(genreService.findAllGenres()).thenReturn(new ArrayList<>());
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        ExecutionResult result = openShopListPageCommand.execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getPage("shop.edition_list"), result.getPage());
    }

    @Test
    @DisplayName("OpenShopListPageCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertNull(new OpenLoginPageCommand().getAccessLevelList());
    }
}