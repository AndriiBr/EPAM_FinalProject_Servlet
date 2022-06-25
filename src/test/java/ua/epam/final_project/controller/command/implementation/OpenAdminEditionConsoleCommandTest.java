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
//import ua.epam.final_project.entity.Edition;
//import ua.epam.final_project.entity.User;
//import ua.epam.final_project.entity.dto.UserDto;
//import ua.epam.final_project.exception.UnknownEditionException;
//import ua.epam.final_project.exception.UnknownGenreException;
//import ua.epam.final_project.service.IEditionService;
//import ua.epam.final_project.service.IGenreService;
//import ua.epam.final_project.service.IUserEditionService;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
//import static org.mockito.Mockito.mock;
//
//@DisplayName("[Unit] Controller")
//@Feature("Controller")
//class OpenAdminEditionConsoleCommandTest {
//
//    private static final SessionRequestContent content = mock(SessionRequestContent.class, RETURNS_DEEP_STUBS);
//    @Mock
//    private IEditionService editionService;
//    @Mock
//    private IGenreService genreService;
//
//    private final OpenAdminEditionConsoleCommand openAdminEditionConsoleCommand;
//
//    public OpenAdminEditionConsoleCommandTest() throws NoSuchFieldException, IllegalAccessException {
//        MockitoAnnotations.openMocks(this);
//        openAdminEditionConsoleCommand = new OpenAdminEditionConsoleCommand();
//
//        Field editionServiceField = openAdminEditionConsoleCommand.getClass().getDeclaredField("editionService");
//        editionServiceField.setAccessible(true);
//        editionServiceField.set(openAdminEditionConsoleCommand, editionService);
//
//        Field genreServiceField = openAdminEditionConsoleCommand.getClass().getDeclaredField("genreService");
//        genreServiceField.setAccessible(true);
//        genreServiceField.set(openAdminEditionConsoleCommand, genreService);
//    }
//
//    @Test
//    @DisplayName("OpenAdminEditionConsoleCommand execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute() throws UnknownEditionException, UnknownGenreException {
//        Mockito.when(content.getReqParameters().get("genreFilter")).thenReturn("333");
//        Mockito.when(content.getReqParameters().get("orderBy")).thenReturn("title");
//        Mockito.when(content.getSessionAttributes().get("language")).thenReturn("en");
//        Mockito.when(content.getReqParameters().get("recordsPerPage")).thenReturn("5");
//        Mockito.when(content.getReqParameters().get("currentPage")).thenReturn("1");
//        Mockito.when(editionService
//                .findAllEditionsFromTo(anyInt(), anyInt(), anyInt(), any())).thenReturn(new ArrayList<>());
//        Mockito.when(editionService.getNumberOfEditions(anyInt())).thenReturn(3);
//        Mockito.when(genreService.findAllGenres()).thenReturn(new ArrayList<>());
//
//        ExecutionResult result = openAdminEditionConsoleCommand.execute(content);
//
//        assertEquals(Direction.FORWARD, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getPage("admin.editions"), result.getPage());
//    }
//
//    @Test
//    @DisplayName("OpenAdminEditionConsoleCommand access settings check")
//    @Story("Command")
//    void getAccessLevelList() {
//        assertTrue(openAdminEditionConsoleCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
//        assertEquals(1, openAdminEditionConsoleCommand.getAccessLevelList().size());
//    }
//}