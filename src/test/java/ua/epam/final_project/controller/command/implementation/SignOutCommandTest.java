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
class SignOutCommandTest {

    @Mock
    private static SessionRequestContent content;

    private final SignOutCommand signOutCommand;

    public SignOutCommandTest() {
        MockitoAnnotations.openMocks(this);
        this.signOutCommand = new SignOutCommand();
    }

    @Test
    @DisplayName("SignOutCommand execute method check")
    @Story("Command")
    @Description("Should return ExecutionResult entity with correct value")
    void execute() {
        ExecutionResult result = signOutCommand.execute(content);

        assertEquals(Direction.REDIRECT, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getUrl("shop.edition_list"), result.getRedirectUrl());
    }

    @Test
    @DisplayName("SignOutCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(signOutCommand.getAccessLevelList().contains(AccessLevel.USER));
        assertTrue(signOutCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertTrue(signOutCommand.getAccessLevelList().contains(AccessLevel.BLOCKED));
        assertEquals(3, signOutCommand.getAccessLevelList().size());
    }
}