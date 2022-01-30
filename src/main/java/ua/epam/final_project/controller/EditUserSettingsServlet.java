package ua.epam.final_project.controller;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static ua.epam.final_project.util.JSPPathConstant.*;

@WebServlet(urlPatterns = "/cabinet/user_settings")
public class EditUserSettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager dbManager = DBManager.getInstance();
        final HttpSession session = req.getSession();
        final String login = (String) session.getAttribute("login");

        final User user;

        try {
            user = dbManager.getUserByLogin(login);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        req.getRequestDispatcher(USER_SETTINGS_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
