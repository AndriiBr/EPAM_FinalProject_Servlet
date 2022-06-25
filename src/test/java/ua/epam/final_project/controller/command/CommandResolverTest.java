package ua.epam.final_project.controller.command;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.controller.command.implementation.OpenErrorUnknownCommand;
import ua.epam.final_project.controller.command.implementation.OpenLoginPageCommand;
import ua.epam.final_project.controller.command.implementation.SignInCommand;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class CommandResolverTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private final CommandResolver commandResolver = CommandResolver.getInstance();
    private static UserDto userDto;

    CommandResolverTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setUp() {
        User user = new User("Rose", "Pass1234", "rose@gmail.com");
        user.setRole("3");
        userDto = new UserDto(user);
    }

    @Test
    @DisplayName("[GET] Get correct Command entity by requested command")
    @Story("Command resolver")
    void initGetCommand() {
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/auth/login"));
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute(any())).thenReturn(null);
        Mockito.when(session.getAttribute("user")).thenReturn(userDto);

        ICommand command = commandResolver.initGetCommand(request);
        assertEquals(OpenLoginPageCommand.class, command.getClass());
    }

    @Test
    @DisplayName("[POST]Get correct Command entity by requested command")
    @Story("Command resolver")
    void initPostCommand() {
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/auth/login"));
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute(any())).thenReturn(null);

        ICommand command = commandResolver.initPostCommand(request);
        assertEquals(SignInCommand.class, command.getClass());
    }

    @Test
    @DisplayName("[POST]Get correct Command entity by requested command")
    @Story("Command resolver")
    void initPostCommand_Error() {
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/auth/login"));
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute(any())).thenReturn(null);
        Mockito.when(session.getAttribute("user")).thenReturn(userDto);

        ICommand command = commandResolver.initPostCommand(request);
        assertEquals(OpenErrorUnknownCommand.class, command.getClass());
    }

    @Test
    void getInstance() {
        assertNotNull(CommandResolver.getInstance());
    }
}