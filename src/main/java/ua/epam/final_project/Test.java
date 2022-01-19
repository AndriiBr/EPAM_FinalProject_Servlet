package ua.epam.final_project;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.User;

import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws SQLException {
        DBManager dbManager = DBManager.getInstance();
        List<User> userList = dbManager.findAllUsers();

        for (User user: userList) {
            System.out.print("id: " + user.getId() + " ");
            System.out.print("login: " + user.getLogin() + " ");
            System.out.print("password: " + user.getPassword() + " ");
            System.out.print("email: " + user.getEmail() + " ");
            System.out.print("name: " + user.getName() + " ");
            System.out.print("name: " + user.getRole() + " ");
            System.out.println();
        }
    }
}
