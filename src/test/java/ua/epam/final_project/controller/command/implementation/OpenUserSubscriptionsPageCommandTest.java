//package ua.epam.final_project.controller.command.implementation;
//
//import io.qameta.allure.Description;
//import io.qameta.allure.Feature;
//import io.qameta.allure.Story;
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
//import ua.epam.final_project.entity.Genre;
//import ua.epam.final_project.entity.User;
//import ua.epam.final_project.entity.dto.UserDto;
//import ua.epam.final_project.exception.UnknownEditionException;
//import ua.epam.final_project.exception.UnknownGenreException;
//import ua.epam.final_project.service.IEditionService;
//import ua.epam.final_project.service.IGenreService;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
//import static org.mockito.Mockito.mock;
//
//@DisplayName("[Unit] Controller")
//@Feature("Controller")
//class OpenUserSubscriptionsPageCommandTest {
//
//    private static final SessionRequestContent content = mock(SessionRequestContent.class, RETURNS_DEEP_STUBS);
//    @Mock
//    private IEditionService editionService;
//    @Mock
//    private IGenreService genreService;
//
//    private final OpenUserSubscriptionsPageCommand openUserSubscriptionsPageCommand;
//    private final UserDto userDto;
//
//    public OpenUserSubscriptionsPageCommandTest() throws NoSuchFieldException, IllegalAccessException {
//        MockitoAnnotations.openMocks(this);
//        openUserSubscriptionsPageCommand = new OpenUserSubscriptionsPageCommand();
//        User user = new User();
//        userDto = new UserDto(user);
//
//        Field editionServiceField = openUserSubscriptionsPageCommand.getClass().getDeclaredField("editionService");
//        editionServiceField.setAccessible(true);
//        editionServiceField.set(openUserSubscriptionsPageCommand, editionService);
//
//        Field genreServiceField = openUserSubscriptionsPageCommand.getClass().getDeclaredField("genreService");
//        genreServiceField.setAccessible(true);
//        genreServiceField.set(openUserSubscriptionsPageCommand, genreService);
//    }
//
//    @Test
//    @DisplayName("OpenUserSubscriptionsPageCommandTest execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute() throws UnknownEditionException, UnknownGenreException {
//        Mockito.when(content.getSessionAttributes().get("user")).thenReturn(userDto);
//        Mockito.when(content.getReqParameters().get("recordsPerPage")).thenReturn("5");
//        Mockito.when(content.getReqParameters().get("currentPage")).thenReturn("1");
//        Mockito.when(content.getReqParameters().get("genreFilter")).thenReturn("2");
//        Mockito.when(content.getReqParameters().get("orderBy")).thenReturn("title");
//        Mockito.when(content.getSessionAttributes().get("language")).thenReturn("en");
//        Mockito.when(editionService.getNumberOfEditions(any(), anyBoolean(), anyInt())).thenReturn(10);
//        Mockito.when(genreService.findAllGenres()).thenReturn(Arrays.asList(new Genre(), new Genre()));
//        Mockito.when(editionService
//                .findAllEditionsFromTo(any(), anyBoolean(), anyInt(), anyInt(), anyInt(), any()))
//                .thenReturn(new ArrayList<>());
//
//        ExecutionResult result = openUserSubscriptionsPageCommand.execute(content);
//
//        assertEquals(Direction.FORWARD, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getPage("shop.subscriptions"), result.getPage());
//    }
//
//    @Test
//    @DisplayName("OpenUserSubscriptionsPageCommandTest access settings check")
//    @Story("Command")
//    void getAccessLevelList() {
//        assertTrue(openUserSubscriptionsPageCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
//        assertTrue(openUserSubscriptionsPageCommand.getAccessLevelList().contains(AccessLevel.USER));
//        assertTrue(openUserSubscriptionsPageCommand.getAccessLevelList().contains(AccessLevel.BLOCKED));
//        assertEquals(3, openUserSubscriptionsPageCommand.getAccessLevelList().size());
//    }
//}