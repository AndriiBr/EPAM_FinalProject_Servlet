package ua.epam.final_project.util;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.controller.util.SessionRequestContent;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Unit] Utils")
@Feature("Utils")
class InputValidatorTest {

    @Mock
    private SessionRequestContent content;

    private static final Map<String, Object> reqAttributes = new HashMap<>();
    private static final Map<String, String> reqParameters = new HashMap<>();
    private static final Map<String, Object> sessionAttributes = new HashMap<>();

    public InputValidatorTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setUp() {
        User user = new User("admin", "Pass1234", "admin@test.com");
        user.setId(1);
        user.setName("Andrii");
        user.setBalance(1000);
        user.setRole("3");

        sessionAttributes.put("language", "en");
        sessionAttributes.put("user", new UserDto(user));

        reqParameters.put("title", "Test title");
        reqParameters.put("text_param", "Test text param");
        reqParameters.put("orderBy", "title");

        reqAttributes.put("price", "300");
        reqAttributes.put("text_en", "Test text");
    }

    @Test
    @DisplayName("Single String value extraction from request content")
    @Story("InputValidator util")
    void extractValueFromRequest_String() {
        Mockito.when(content.getReqParameters()).thenReturn(reqParameters);
        Mockito.when(content.getReqAttributes()).thenReturn(reqAttributes);
        Mockito.when(content.getSessionAttributes()).thenReturn(sessionAttributes);

        assertEquals("Test title", InputValidator.extractValueFromRequest(content, "title", "default"));
        assertEquals("title_en", InputValidator.extractValueFromRequest(content, "orderBy", "default"));
    }

    @Test
    @DisplayName("Single Int value extraction from request content")
    @Story("InputValidator util")
    void extractValueFromRequest_Int() {
        Mockito.when(content.getReqParameters()).thenReturn(reqParameters);
        Mockito.when(content.getReqAttributes()).thenReturn(reqAttributes);
        Mockito.when(content.getSessionAttributes()).thenReturn(sessionAttributes);

        assertEquals(300, InputValidator.extractValueFromRequest(content, "price", 0));
    }

    @Test
    @DisplayName("[Return default] Single Int value extraction from request content")
    @Story("InputValidator util")
    void extractValueFromRequest_Int_Default() {
        Mockito.when(content.getReqParameters()).thenReturn(reqParameters);
        Mockito.when(content.getReqAttributes()).thenReturn(reqAttributes);
        Mockito.when(content.getSessionAttributes()).thenReturn(sessionAttributes);

        assertEquals(333, InputValidator.extractValueFromRequest(content, "title", 333));
    }

    @Test
    @DisplayName("[Success] Test new user input data")
    @Story("InputValidator util")
    @Description("All correct input must return true")
    void validateLoginPassword_Success() {
        reqParameters.put("price", "300");
        assertTrue(InputValidator
                .validateRegistrationForm("user1", "user1@gmail.com", "Pass1234", "Pass1234"));
    }

    @Test
    @DisplayName("[Fail] Test new user input data - incorrect login")
    @Story("InputValidator util")
    @Description("Incorrect login must return false")
    void validateLoginPassword_LoginFail() {
        assertFalse(InputValidator
                .validateRegistrationForm(null, "user1@gmail.com", "Pass1234", "Pass1234"));
        assertFalse(InputValidator
                .validateRegistrationForm("", "user1@gmail.com", "Pass1234", "Pass1234"));
        assertFalse(InputValidator
                .validateRegistrationForm("us", "user1@gmail.com", "Pass1234", "Pass1234"));
    }

    @Test
    @DisplayName("[Fail] Test new user input data - incorrect email")
    @Story("InputValidator util")
    @Description("Incorrect email must return false")
    void validateLoginPassword_EmailFail() {
        assertFalse(InputValidator
                .validateRegistrationForm("user1", null, "Pass1234", "Pass1234"));
        assertFalse(InputValidator
                .validateRegistrationForm("user1", "", "Pass1234", "Pass1234"));
        assertFalse(InputValidator
                .validateRegistrationForm("user1", "user1@gmail", "Pass1234", "Pass1234"));
    }

    @Test
    @DisplayName("[Fail] Test new user input data - incorrect email")
    @Story("InputValidator util")
    @Description("Incorrect email must return false")
    void validateLoginPassword_PasswordFail() {
        assertFalse(InputValidator
                .validateRegistrationForm("user1", "user1@gmail", "Pass1234", null));
        assertFalse(InputValidator
                .validateRegistrationForm("user1", "user1@gmail", "Pass", "Pass"));
        assertFalse(InputValidator
                .validateRegistrationForm("user1", "user1@gmail", "Pass1234", "Pass5678"));
    }

    @Test
    @DisplayName("[Success] Validate new edition input data")
    @Story("InputValidator util")
    void validateNewEdition_Success() {
        assertTrue(InputValidator
                .validateNewEdition("Title", "Титулка", "Test text", "Тестовий текст", "100", "2"));

    }

    @Test
    @DisplayName("[Fail] Validate new edition input data")
    @Story("InputValidator util")
    void validateNewEdition_Fail() {
        assertFalse(InputValidator
                .validateNewEdition(null, "Титулка", "Test text", "Тестовий текст", "100", "2"));
        assertFalse(InputValidator
                .validateNewEdition("Title", null, "Test text", "Тестовий текст", "100", "2"));
        assertFalse(InputValidator
                .validateNewEdition("Title", "Титулка", null, "Тестовий текст", "100", "2"));
        assertFalse(InputValidator
                .validateNewEdition("Title", "Титулка", "Test text", null, "100", "2"));
        assertFalse(InputValidator
                .validateNewEdition("Title", "Титулка", "Test text", "Тестовий текст", "0", "2"));
        assertFalse(InputValidator
                .validateNewEdition("Title", "Титулка", "Test text", "Тестовий текст", "100", "-1"));
    }
}