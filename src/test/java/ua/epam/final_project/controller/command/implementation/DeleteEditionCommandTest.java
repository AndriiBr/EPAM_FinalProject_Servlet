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
//import ua.epam.final_project.exception.UnknownEditionException;
//import ua.epam.final_project.service.IEditionService;
//
//import java.lang.reflect.Field;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
//import static org.mockito.Mockito.mock;
//
//@DisplayName("[Unit] Controller")
//@Feature("Controller")
//class DeleteEditionCommandTest {
//
//    private static final SessionRequestContent content = mock(SessionRequestContent.class, RETURNS_DEEP_STUBS);
//    @Mock
//    private IEditionService editionService;
//
//    private final DeleteEditionCommand deleteEditionCommand;
//    private final Edition edition;
//
//    public DeleteEditionCommandTest() throws NoSuchFieldException, IllegalAccessException {
//        MockitoAnnotations.openMocks(this);
//        deleteEditionCommand = new DeleteEditionCommand();
//        edition = new Edition();
//
//        Field editionServiceField = deleteEditionCommand.getClass().getDeclaredField("editionService");
//        editionServiceField.setAccessible(true);
//        editionServiceField.set(deleteEditionCommand, editionService);
//    }
//
//    @Test
//    @DisplayName("DeleteEditionCommand execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute() throws UnknownEditionException {
//        Mockito.when(content.getSessionAttributes().get("edition_id")).thenReturn("377");
//        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
//        Mockito.when(editionService.deleteEdition(any())).thenReturn(true);
//
//        ExecutionResult result = deleteEditionCommand.execute(content);
//
//        assertEquals(Direction.REDIRECT, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getUrl("admin.editions"), result.getRedirectUrl());
//    }
//
//    @Test
//    @DisplayName("[Null edition] DeleteEditionCommand execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute_null_edition() throws UnknownEditionException {
//        Mockito.when(content.getSessionAttributes().get("edition_id")).thenReturn("377");
//        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(null);
//        Mockito.when(editionService.deleteEdition(any())).thenReturn(true);
//
//        ExecutionResult result = deleteEditionCommand.execute(content);
//
//        assertEquals(Direction.REDIRECT, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
//    }
//
//    @Test
//    @DisplayName("[Fail delete from DB] DeleteEditionCommand execute method check")
//    @Story("Command")
//    @Description("Should return ExecutionResult entity with correct value")
//    void execute_fail_delete() throws UnknownEditionException {
//        Mockito.when(content.getSessionAttributes().get("edition_id")).thenReturn("377");
//        Mockito.when(editionService.findEditionById(anyInt())).thenReturn(edition);
//        Mockito.when(editionService.deleteEdition(any())).thenReturn(false);
//
//        ExecutionResult result = deleteEditionCommand.execute(content);
//
//        assertEquals(Direction.REDIRECT, result.getDirection());
//        assertEquals(ResourceConfiguration.getInstance().getUrl("error.unknown"), result.getRedirectUrl());
//    }
//
//    @Test
//    @DisplayName("DeleteEditionCommand access settings check")
//    @Story("Command")
//    void getAccessLevelList() {
//        assertTrue(deleteEditionCommand.getAccessLevelList().contains(AccessLevel.ADMIN));
//        assertEquals(1, deleteEditionCommand.getAccessLevelList().size());
//    }
//}