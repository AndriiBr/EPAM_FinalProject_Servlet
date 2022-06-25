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
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.IUserEditionService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class OpenEditEditionPageCommandTest {

    private SessionRequestContent content;
    @Mock
    private IEditionService editionService;
    @Mock
    private IGenreService genreService;

    private final OpenEditEditionPageCommand openEditEditionPageCommand;
    private final Edition edition;

    public OpenEditEditionPageCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        openEditEditionPageCommand = new OpenEditEditionPageCommand();
        edition = new Edition();

        Field editionServiceField = openEditEditionPageCommand.getClass().getDeclaredField("editionService");
        editionServiceField.setAccessible(true);
        editionServiceField.set(openEditEditionPageCommand, editionService);

        Field genreServiceField = openEditEditionPageCommand.getClass().getDeclaredField("genreService");
        genreServiceField.setAccessible(true);
        genreServiceField.set(openEditEditionPageCommand, genreService);
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
    @DisplayName("OpenEditEditionPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() throws UnknownEditionException, UnknownGenreException {
        Mockito.when(content.getReqParameters().get("edit_edition_id")).thenReturn("55");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(genreService.findAllGenres())
                .thenReturn(Arrays.asList(new Genre(), new Genre()));

        ExecutionResult result = openEditEditionPageCommand.execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getPage("admin.edit-edition"), result.getPage());
    }

    @Test
    @DisplayName("[Null edition] OpenEditEditionPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_null_edition() throws UnknownEditionException, UnknownGenreException {
        Mockito.when(content.getReqParameters().get("edit_edition_id")).thenReturn("55");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(null);
        Mockito.when(genreService.findAllGenres())
                .thenReturn(Arrays.asList(new Genre(), new Genre()));

        ExecutionResult result = openEditEditionPageCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Empty genre list] OpenEditEditionPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_empty_genres() throws UnknownEditionException, UnknownGenreException {
        Mockito.when(content.getReqParameters().get("edit_edition_id")).thenReturn("55");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(genreService.findAllGenres())
                .thenReturn(new ArrayList<>());

        ExecutionResult result = openEditEditionPageCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }
    @DisplayName("UnsubscribeEditionCommand access settings check")
    @Story("Command")
    @Test
    void getAccessLevelList() {
        assertTrue(openEditEditionPageCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertEquals(1, openEditEditionPageCommand.getAccessLevelList().size());
    }
}