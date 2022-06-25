//package ua.epam.final_project.controller.command.implementation;
//
//import io.qameta.allure.Description;
//import io.qameta.allure.Feature;
//import io.qameta.allure.Story;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import ua.epam.final_project.config.ResourceConfiguration;
//import ua.epam.final_project.controller.command.security.AccessLevel;
//import ua.epam.final_project.controller.util.Direction;
//import ua.epam.final_project.controller.util.ExecutionResult;
//import ua.epam.final_project.controller.util.SessionRequestContent;
//import ua.epam.final_project.entity.User;
//import ua.epam.final_project.entity.dto.UserDto;
//import ua.epam.final_project.exception.UnknownUserException;
//import ua.epam.final_project.service.IUserService;
//
//import java.lang.reflect.Field;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
//import static org.mockito.Mockito.mock;
//
//@DisplayName("[Unit] Controller")
//@Feature("Controller")
//class BlockUnblockUserCommandTest {
//
//    private static final SessionRequestContent content = mock(SessionRequestContent.class, RETURNS_DEEP_STUBS);
//    @Mock
//    private IUserService userService;
//
//    private final BlockUnblockUserCommand blockUnblockUserCommand;
//    private final UserDto userDto;
//
//    public BlockUnblockUserCommandTest() throws NoSuchFieldException, IllegalAccessException {
//        MockitoAnnotations.openMocks(this);
//        blockUnblockUserCommand = new BlockUnblockUserCommand();
//        User user = new User();
//        user.setRole("2");
//        userDto = new UserDto(user);
//
//        Field userServiceField = blockUnblockUserCommand.getClass().getDeclaredField("userService");
//        userServiceField.setAccessible(true);
//        userServiceField.set(blockUnblockUserCommand, userService);
//    }
//
//    @BeforeEach
//    public void setUp() {
//
//    }
//
//    @Test
//    @DisplayName("BlockUnblockUserCommand execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute() throws UnknownUserException {
//        Mockito.when(content.getSessionAttributes().get("user_block")).thenReturn(userDto);
//        Mockito.when(userService.findUserById(anyInt())).thenReturn(userDto);
//        Mockito.when(userService.updateUser(any())).thenReturn(true);
//
//        ExecutionResult result = blockUnblockUserCommand.execute(content);
//
//        assertEquals(Direction.REDIRECT, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getUrl("admin.user-list"), result.getRedirectUrl());
//    }
//
//    @Test
//    @DisplayName("[Null user] BlockUnblockUserCommand execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute_null_user() throws UnknownUserException {
//        Mockito.when(content.getSessionAttributes().get("user_block")).thenReturn(userDto);
//        Mockito.when(userService.findUserById(anyInt())).thenReturn(null);
//        Mockito.when(userService.updateUser(any())).thenReturn(true);
//
//        ExecutionResult result = blockUnblockUserCommand.execute(content);
//
//        assertEquals(Direction.REDIRECT, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
//    }
//
//    @Test
//    @DisplayName("[Fail DB update]BlockUnblockUserCommand execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute_fail_update() throws UnknownUserException {
//        Mockito.when(content.getSessionAttributes().get("user_block")).thenReturn(userDto);
//        Mockito.when(userService.findUserById(anyInt())).thenReturn(userDto);
//        Mockito.when(userService.updateUser(any())).thenReturn(false);
//
//        ExecutionResult result = blockUnblockUserCommand.execute(content);
//
//        assertEquals(Direction.REDIRECT, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
//    }
//
//    @Test
//    void getAccessLevelList() {
//        assertTrue(blockUnblockUserCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
//        assertEquals(1, blockUnblockUserCommand.getAccessLevelList().size());
//    }
//}