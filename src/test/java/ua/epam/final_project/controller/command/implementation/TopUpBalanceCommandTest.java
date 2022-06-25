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
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class TopUpBalanceCommandTest {

    private SessionRequestContent content;
    @Mock
    private IUserService userService;

    private final TopUpBalanceCommand topUpBalanceCommand;
    private final UserDto userDto;

    public TopUpBalanceCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        topUpBalanceCommand = new TopUpBalanceCommand();
        User user = new User();
        userDto = new UserDto(user);

        Field userServiceField = topUpBalanceCommand.getClass().getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(topUpBalanceCommand, userService);
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
    @DisplayName("TopUpBalanceCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() throws UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("money")).thenReturn("3333");
        Mockito.when(userService.updateUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = topUpBalanceCommand.execute(content);
        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("user.wallet"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Null user] TopUpBalanceCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_null_user() throws UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(null);
        Mockito.when(content.getReqParameters().get("money")).thenReturn("3333");
        Mockito.when(userService.updateUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = topUpBalanceCommand.execute(content);
        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Wrong price format] TopUpBalanceCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_wrong_price() throws UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("money")).thenReturn("3rtb45gr");
        Mockito.when(userService.updateUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = topUpBalanceCommand.execute(content);
        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Fail DB update] TopUpBalanceCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_fail_update_user() throws UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("money")).thenReturn("3333");
        Mockito.when(userService.updateUser(any())).thenReturn(false);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = topUpBalanceCommand.execute(content);
        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("TopUpBalanceCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(topUpBalanceCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertTrue(topUpBalanceCommand.getAccessLevelList().contains(AccessLevel.USER));
        assertEquals(2, topUpBalanceCommand.getAccessLevelList().size());
    }
}