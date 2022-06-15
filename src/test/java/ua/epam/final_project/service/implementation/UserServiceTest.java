package ua.epam.final_project.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private IUserService userService;

    @BeforeEach
    public void setUp() {
        userService = ServiceFactory.getUserService();
    }

    @Test
    void getNumberOfUsers() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void findAllUsersFromTo() {
    }

    @Test
    void findUserByLoginPassword() {
    }

    @Test
    @DisplayName("Find Bober in Poland database")
    void findUserByLogin_return_true() {
        assertTrue(true);
    }

    @Test
    void insertUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}