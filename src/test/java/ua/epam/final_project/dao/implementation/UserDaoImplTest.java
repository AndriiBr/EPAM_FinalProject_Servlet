package ua.epam.final_project.dao.implementation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.dao.IUserDao;
import ua.epam.final_project.dao.TestSQLConnection;
import ua.epam.final_project.exception.DataNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoImplTest {

    private static Connection connection;
    private static IUserDao userDao;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = TestSQLConnection.getConnectionToTestDB();
        userDao = new UserDaoImpl(connection);
//        Statement statement = connection.createStatement();
//        statement.execute("create table user_role\n" +
//                "(\n" +
//                "    id      SERIAL,\n" +
//                "    role_En varchar(25),\n" +
//                "    role_Ua varchar(25),\n" +
//                "    primary key (id)\n" +
//                ")");
//        statement.execute("create table usr\n" +
//                "(\n" +
//                "    id          SERIAL,\n" +
//                "    username    varchar(255),\n" +
//                "    pass        varchar(64),\n" +
//                "    email       varchar(128),\n" +
//                "    first_name  varchar(64),\n" +
//                "    user_image  varchar(2048),\n" +
//                "    balance     int,\n" +
//                "    user_role_id int references user_role (id),\n" +
//                "    primary key (id)\n" +
//                ")");
    }

    @AfterAll
    static void resetAll() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP ALL OBJECTS DELETE FILES");
        connection.close();
    }

    @Test
    void getNumberOfUsers() throws DataNotFoundException {
        assertEquals(1, userDao.getNumberOfUsers());

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
    void findUserByLogin() {
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