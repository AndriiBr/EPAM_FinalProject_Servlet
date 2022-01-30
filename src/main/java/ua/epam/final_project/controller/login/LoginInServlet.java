package ua.epam.final_project.controller.login;

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
import java.time.LocalTime;

import static ua.epam.final_project.util.JSPPathConstant.*;

@WebServlet(urlPatterns = "/login")
public class LoginInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);

        System.out.println("LogInServlet - DoGET method: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        final HttpSession session = req.getSession();

        User user = null;

        try {
            user = DBManager.getInstance().getUser(login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null) {
            session.setAttribute("login", login);
            session.setAttribute("role", user.getRole());
            //Redirection according to PRG Pattern
            resp.sendRedirect("/successful_login");
        } else {
            //Redirection according to PRG Pattern
            resp.sendRedirect("/unsuccessful_login");
        }
        System.out.println("LogInServlet - DoPOST method: " + LocalTime.now());
    }
}
