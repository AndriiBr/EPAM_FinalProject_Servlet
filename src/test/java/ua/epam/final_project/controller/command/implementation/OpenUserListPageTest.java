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
import ua.epam.final_project.entity.Role;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.UnknownRoleException;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IRoleService;
import ua.epam.final_project.service.IUserService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@DisplayName("[Unit] Controller")
@Feature("Controller")
class OpenUserListPageTest {

    private SessionRequestContent content;
    @Mock
    private IUserService userService;
    @Mock
    private IRoleService roleService;

    private final OpenUserListPage openUserListPage;

    public OpenUserListPageTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        openUserListPage = new OpenUserListPage();

        Field userServiceField = openUserListPage.getClass().getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(openUserListPage, userService);

        Field roleServiceField = openUserListPage.getClass().getDeclaredField("roleService");
        roleServiceField.setAccessible(true);
        roleServiceField.set(openUserListPage, roleService);
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
    void execute() throws UnknownUserException, UnknownRoleException {
        Mockito.when(content.getReqParameters().get("recordsPerPage")).thenReturn("10");
        Mockito.when(content.getReqParameters().get("currentPage")).thenReturn("1");
        Mockito.when(userService.findAllUsersFromTo(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        Mockito.when(roleService.findAllRoles()).thenReturn(Arrays.asList(new Role(), new Role()));
        Mockito.when(userService.getNumberOfUsers()).thenReturn(5);

        ExecutionResult result = openUserListPage.execute(content);

        assertEquals(Direction.FORWARD, result.getDirection());
        assertEquals(ResourceConfiguration.getInstance().getPage("admin.user-list"), result.getPage());
    }

    @Test
    @DisplayName("UnsubscribeEditionCommand access settings check")
    @Story("Command")
    void getAccessLevelList() {
        assertTrue(openUserListPage.getAccessLevelList().contains(AccessLevel.ADMIN));
        assertEquals(1, openUserListPage.getAccessLevelList().size());
    }
}