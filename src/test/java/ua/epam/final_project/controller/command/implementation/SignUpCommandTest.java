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
class SignUpCommandTest {

    private SessionRequestContent content;
    @Mock
    private IUserService userService;

    private final SignUpCommand signUpCommand;
    private final UserDto userDto;

    public SignUpCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        signUpCommand = new SignUpCommand();
        User user = new User();
        userDto = new UserDto(user);

        Field userServiceField = signUpCommand.getClass().getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(signUpCommand, userService);
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
    @DisplayName("SignUpCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() throws UnknownUserException {
        Mockito.when(content.getReqParameters().get("login")).thenReturn("user99");
        Mockito.when(content.getReqParameters().get("email")).thenReturn("user99@gmail.com");
        Mockito.when(content.getReqParameters().get("password")).thenReturn("Pass55555");
        Mockito.when(content.getReqParameters().get("password_confirm")).thenReturn("Pass55555");
        Mockito.when(userService.insertUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = signUpCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.registration.success"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Wrong login]SignUpCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_wrong_login() throws UnknownUserException {
        Mockito.when(content.getReqParameters().get("login")).thenReturn("us");
        Mockito.when(content.getReqParameters().get("email")).thenReturn("user99@gmail.com");
        Mockito.when(content.getReqParameters().get("password")).thenReturn("Pass55555");
        Mockito.when(content.getReqParameters().get("password_confirm")).thenReturn("Pass55555");
        Mockito.when(userService.insertUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = signUpCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.registration.fail"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Wrong email]SignUpCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_wrong_email() throws UnknownUserException {
        Mockito.when(content.getReqParameters().get("login")).thenReturn("user99");
        Mockito.when(content.getReqParameters().get("email")).thenReturn("user99@gmai");
        Mockito.when(content.getReqParameters().get("password")).thenReturn("Pass55555");
        Mockito.when(content.getReqParameters().get("password_confirm")).thenReturn("Pass55555");
        Mockito.when(userService.insertUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = signUpCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.registration.fail"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Wrong password]SignUpCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_wrong_password() throws UnknownUserException {
        Mockito.when(content.getReqParameters().get("login")).thenReturn("user99");
        Mockito.when(content.getReqParameters().get("email")).thenReturn("user99@gmail.com");
        Mockito.when(content.getReqParameters().get("password")).thenReturn("3Pass5");
        Mockito.when(content.getReqParameters().get("password_confirm")).thenReturn("3Pass5");
        Mockito.when(userService.insertUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = signUpCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.registration.fail"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Wrong password confiramtion]SignUpCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_wrong_password_confirmation() throws UnknownUserException {
        Mockito.when(content.getReqParameters().get("login")).thenReturn("user99");
        Mockito.when(content.getReqParameters().get("email")).thenReturn("user99@gmail.com");
        Mockito.when(content.getReqParameters().get("password")).thenReturn("Pass55555");
        Mockito.when(content.getReqParameters().get("password_confirm")).thenReturn("Pass34678");
        Mockito.when(userService.insertUser(any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = signUpCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.registration.fail"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Fail insert in DB]SignUpCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_fail_insert() throws UnknownUserException {
        Mockito.when(content.getReqParameters().get("login")).thenReturn("user99");
        Mockito.when(content.getReqParameters().get("email")).thenReturn("user99@gmail.com");
        Mockito.when(content.getReqParameters().get("password")).thenReturn("Pass55555");
        Mockito.when(content.getReqParameters().get("password_confirm")).thenReturn("Pass55555");
        Mockito.when(userService.insertUser(any())).thenReturn(false);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = signUpCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("auth.registration.fail"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("SignUpCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(signUpCommand.getAccessLevelList().contains(AccessLevel.GUEST));
        assertEquals(1, signUpCommand.getAccessLevelList().size());
    }
}