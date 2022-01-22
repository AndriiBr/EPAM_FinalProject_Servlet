package ua.epam.final_project.servlet.login;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.User;

import static ua.epam.final_project.util.JSPPathConstant.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

@WebServlet(urlPatterns = "/login")
public class LoginInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        System.out.println("DoGET from LogIn Servlet: " + LocalTime.now());
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
            req.getRequestDispatcher(LOGGED_IN_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(WRONG_LOG_PASS_PAGE).forward(req, resp);
        }
        System.out.println("DoPOST from LogIn Servlet: " + LocalTime.now());
    }
}
