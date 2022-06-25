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
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownEditionException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IUserEditionService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class UnsubscribeEditionCommandTest {

    private SessionRequestContent content;
    @Mock
    private IEditionService editionService;
    @Mock
    private IUserEditionService userEditionService;

    private final UnsubscribeEditionCommand unsubscribeEditionCommand;
    private final UserDto userDto;
    private final Edition edition;

    public UnsubscribeEditionCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        unsubscribeEditionCommand = new UnsubscribeEditionCommand();
        User user = new User();
        userDto = new UserDto(user);
        edition = new Edition();

        Field editionServiceField = unsubscribeEditionCommand.getClass().getDeclaredField("editionService");
        editionServiceField.setAccessible(true);
        editionServiceField.set(unsubscribeEditionCommand, editionService);

        Field userEditionServiceField = unsubscribeEditionCommand.getClass().getDeclaredField("userEditionService");
        userEditionServiceField.setAccessible(true);
        userEditionServiceField.set(unsubscribeEditionCommand, userEditionService);
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
    @DisplayName("UnsubscribeEditionCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() throws UnknownEditionException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("edition_id")).thenReturn("735");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(userEditionService.deleteUserEdition(any(), any())).thenReturn(true);

        ExecutionResult result = unsubscribeEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("shop.subscriptions"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Null user]UnsubscribeEditionCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_null_user() throws UnknownEditionException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(null);
        Mockito.when(content.getReqParameters().get("edition_id")).thenReturn("735");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(userEditionService.deleteUserEdition(any(), any())).thenReturn(true);

        ExecutionResult result = unsubscribeEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Null edition]UnsubscribeEditionCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_null_edition() throws UnknownEditionException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("edition_id")).thenReturn("735");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(null);
        Mockito.when(userEditionService.deleteUserEdition(any(), any())).thenReturn(true);

        ExecutionResult result = unsubscribeEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Fail delete operation]UnsubscribeEditionCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_fail_delete() throws UnknownEditionException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("edition_id")).thenReturn("735");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(userEditionService.deleteUserEdition(any(), any())).thenReturn(false);

        ExecutionResult result = unsubscribeEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("UnsubscribeEditionCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(unsubscribeEditionCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertTrue(unsubscribeEditionCommand.getAccessLevelList().contains(AccessLevel.USER));
        assertTrue(unsubscribeEditionCommand.getAccessLevelList().contains(AccessLevel.BLOCKED));
        assertEquals(3, unsubscribeEditionCommand.getAccessLevelList().size());
    }
}