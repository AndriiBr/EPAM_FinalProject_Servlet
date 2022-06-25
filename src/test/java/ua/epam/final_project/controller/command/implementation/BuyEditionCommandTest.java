package ua.epam.final_project.controller.command.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
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
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IUserEditionService;
import ua.epam.final_project.service.IUserService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class BuyEditionCommandTest {

    private SessionRequestContent content;
    @Mock
    private IUserService userService;
    @Mock
    private IEditionService editionService;
    @Mock
    private IUserEditionService userEditionService;

    private final BuyEditionCommand buyEditionCommand;
    private final UserDto userDto;
    private final Edition edition;

    public BuyEditionCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        buyEditionCommand = new BuyEditionCommand();
        User user = new User();
        userDto = new UserDto(user);
        edition = new Edition();

        Field userServiceField = buyEditionCommand.getClass().getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(buyEditionCommand, userService);

        Field editionServiceField = buyEditionCommand.getClass().getDeclaredField("editionService");
        editionServiceField.setAccessible(true);
        editionServiceField.set(buyEditionCommand, editionService);

        Field userEditionServiceField = buyEditionCommand.getClass().getDeclaredField("userEditionService");
        userEditionServiceField.setAccessible(true);
        userEditionServiceField.set(buyEditionCommand, userEditionService);
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
    void execute() throws UnknownEditionException, UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("buy_edition_id")).thenReturn("333");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(userEditionService.insertUserEdition(any(), any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = buyEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("shop.edition_list"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Null user] UnsubscribeEditionCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_null_user() throws UnknownEditionException, UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(null);
        Mockito.when(content.getReqParameters().get("buy_edition_id")).thenReturn("333");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(userEditionService.insertUserEdition(any(), any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(null);

        ExecutionResult result = buyEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Null edition] UnsubscribeEditionCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_null_edition() throws UnknownEditionException, UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("buy_edition_id")).thenReturn("333");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(null);
        Mockito.when(userEditionService.insertUserEdition(any(), any())).thenReturn(true);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = buyEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("[Fail insert in DB] UnsubscribeEditionCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute_fail_insert() throws UnknownEditionException, UnknownUserException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("buy_edition_id")).thenReturn("333");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
        Mockito.when(userEditionService.insertUserEdition(any(), any())).thenReturn(false);
        Mockito.when(userService.findUserByLogin(any())).thenReturn(userDto);

        ExecutionResult result = buyEditionCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("UnsubscribeEditionCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(buyEditionCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertTrue(buyEditionCommand.getAccessLevelList().contains(AccessLevel.USER));
        assertEquals(2, buyEditionCommand.getAccessLevelList().size());
    }
}