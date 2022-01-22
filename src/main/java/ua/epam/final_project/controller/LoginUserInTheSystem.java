package ua.epam.final_project.controller;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.User;

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
public class LoginUserInTheSystem extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/loginPage/login_page.jsp").forward(req, resp);
        System.out.println("DoGET from LoginUserInTheSystem Servlet: " + LocalTime.now());
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
            session.setAttribute("username", login);
            session.setAttribute("role", user.getRole());
            req.getRequestDispatcher("/WEB-INF/view/loginPage/LoggedIn.jsp").forward(req, resp);
        } else {
              req.getRequestDispatcher("/WEB-INF/view/loginPage/wrongLP.jsp").forward(req, resp);
        }
        System.out.println("DoPOST from LoginUserInTheSystem Servlet: " + LocalTime.now());
    }
}
