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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class OpenWalletTopUpPageCommandTest {

    private SessionRequestContent content;
    @Mock
    private IUserService userService;

    private final OpenWalletTopUpPageCommand openWalletTopUpPageCommand;
    private final UserDto userDto;

    public OpenWalletTopUpPageCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        openWalletTopUpPageCommand = new OpenWalletTopUpPageCommand();
        User user = new User();
        userDto = new UserDto(user);

        Field userServiceField = openWalletTopUpPageCommand.getClass().getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(openWalletTopUpPageCommand, userService);
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
    @DisplayName("OpenWalletTopUpPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() throws UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(userService.findUserById(anyInt())).thenReturn(userDto);

        ExecutionResult result = openWalletTopUpPageCommand.execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getPage("user.wallet.top_up"), result.getPage());
    }

    @Test
    @DisplayName("OpenWalletTopUpPageCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(openWalletTopUpPageCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertTrue(openWalletTopUpPageCommand.getAccessLevelList().contains(AccessLevel.USER));
        assertEquals(2, openWalletTopUpPageCommand.getAccessLevelList().size());
    }
}