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
import ua.epam.final_project.service.IUserService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class OpenEditionBuyPageCommandTest {

    private SessionRequestContent content;
    @Mock
    private IEditionService editionService;

    private final OpenEditionBuyPageCommand openEditionBuyPageCommand;
    private final Edition edition;
    private final UserDto userDto;

    public OpenEditionBuyPageCommandTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        openEditionBuyPageCommand = new OpenEditionBuyPageCommand();
        edition = new Edition();
        User user = new User();
        userDto = new UserDto(user);

        Field editionServiceField = openEditionBuyPageCommand.getClass().getDeclaredField("editionService");
        editionServiceField.setAccessible(true);
        editionServiceField.set(openEditionBuyPageCommand, editionService);
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
    @DisplayName("OpenEditionBuyPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() throws UnknownEditionException {
        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
        Mockito.when(content.getReqParameters().get("buy_edition_id")).thenReturn("111");
        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);

        ExecutionResult result = openEditionBuyPageCommand.execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getPage("shop.buy"), result.getPage());
    }

    @Test
    @DisplayName("OpenEditionBuyPageCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(openEditionBuyPageCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertTrue(openEditionBuyPageCommand.getAccessLevelList().contains(AccessLevel.USER));
        assertEquals(2, openEditionBuyPageCommand.getAccessLevelList().size());
    }
}