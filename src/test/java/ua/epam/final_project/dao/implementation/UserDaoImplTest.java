package ua.epam.final_project.dao.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.IncorrectPropertyException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

//    private IUserDao userDao;
//
//    @BeforeEach
//    public void setUp() throws IncorrectPropertyException, DataBaseNotSupportedException {
//        DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.POSTGRES);
//        this.userDao = daoFactory.getUserDao();
//    }
//
//    @Test
//    void getNumberOfUsers() {
//    }
//
//    @Test
//    void findAllUsers() {
//    }
//
//    @Test
//    void findAllUsersFromTo() {
//    }
//
//    @Test
//    void findUserByLoginPassword() {
//    }
//
//    @Test
//    void findUserByLogin() throws DataNotFoundException {
//        User user = userDao.findUserByLogin("Nicolas");
//        assertNotNull(user);
//        assertEquals("Nicolas", user.getLogin());
//    }
//
//    @Test
//    void insertUser() {
//    }
//
//    @Test
//    void updateUser() {
//    }
//
//    @Test
//    void deleteUser() {
//    }
}