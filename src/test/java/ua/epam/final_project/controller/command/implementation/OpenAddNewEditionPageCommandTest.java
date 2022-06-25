package ua.epam.final_project.controller.command.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.IUserService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class OpenAddNewEditionPageCommandTest {

    @Mock
    private static SessionRequestContent content;
    @Mock
    private IGenreService genreService;

    private final OpenAddNewEditionPageCommand openAddNewEditionPageCommand;

    public OpenAddNewEditionPageCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        openAddNewEditionPageCommand = new OpenAddNewEditionPageCommand();

        Field userServiceField = openAddNewEditionPageCommand.getClass().getDeclaredField("genreService");
        userServiceField.setAccessible(true);
        userServiceField.set(openAddNewEditionPageCommand, genreService);
    }

    @Test
    @DisplayName("OpenAddNewEditionPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct values")
    void execute() throws UnknownGenreException {
        Mockito.when(genreService.findAllGenres()).thenReturn(null);

        ExecutionResult result = openAddNewEditionPageCommand.execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getPage("admin.new-edition"), result.getPage());
    }

    @Test
    @DisplayName("OpenAddNewEditionPageCommandTest access settings check")
    @Story("Command")
    void getAccessLevelList() {
        OpenAddNewEditionPageCommand command = new OpenAddNewEditionPageCommand();

        assertTrue(command.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertEquals(1, command.getAccessLevelList().size());
    }
}