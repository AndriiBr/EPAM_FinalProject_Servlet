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
class SignInCommandTest {

    private SessionRequestContent content;
    @Mock
    private IUserService userService;

    private final SignInCommand signInCommand;
    private final UserDto userDto;

    public SignInCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        signInCommand = new SignInCommand();
        User user = new User();
        userDto = new UserDto(user);

        Field userServiceField = signInCommand.getClass().getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(signInCommand, userService);
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
    @DisplayName("[user found] SignInCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() throws UnknownUserException {
        Mockito.when(userService.findUserByLoginPassword(any(), any())).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("login")).thenReturn("TestName");
        Mockito.when(content.getReqParameters().get("password")).thenReturn("Pass1233425234");

        ExecutionResult result = signInCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.login.success"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[user not found] SignInCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct values")
    void execute_fail() throws UnknownUserException {
        Mockito.when(userService.findUserByLoginPassword(any(), any())).thenReturn(null);

        ExecutionResult result = signInCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.login.fail"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("SignInCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {

        assertTrue(signInCommand.getAccessLevelList().contains(AccessLevel.GUEST));
        assertEquals(1, signInCommand.getAccessLevelList().size());
    }
}