package ua.epam.final_project.controller.command.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.config.ResourceConfiguration;
import ua.epam.final_project.controller.command.security.AccessLevel;
import ua.epam.final_project.controller.util.Direction;
import ua.epam.final_project.controller.util.ExecutionResult;
import ua.epam.final_project.controller.util.SessionRequestContent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class OpenLoginSuccessPageCommandTest {

    @Mock
    private static SessionRequestContent content;

    public OpenLoginSuccessPageCommandTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("OpenLoginSuccessPageCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with direction:Forward and not null page")
    void execute() {
        ExecutionResult result = new OpenLoginSuccessPageCommand().execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getPage("auth.login.success"), result.getPage());

    }

    @Test
    @DisplayName("OpenLoginSuccessPageCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        OpenLoginSuccessPageCommand command = new OpenLoginSuccessPageCommand();

        assertTrue(command.getAccessLevelList().contains(AccessLevel.BLOCKED));
        assertTrue(command.getAccessLevelList().contains(AccessLevel.USER));
        assertTrue(command.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertEquals(3, command.getAccessLevelList().size());
    }
}