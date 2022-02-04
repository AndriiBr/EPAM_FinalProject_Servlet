package ua.epam.final_project.controller.login;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.MySQLDaoFactory;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
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
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = LOGIN_URL)
public class LoginInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);

        System.out.println("LogInServlet - DoGET method: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        final HttpSession session = req.getSession();

        User user = null;

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            user = daoFactory.getUserDao().findUserByLoginPassword(login, password);
        } catch (SQLException | DataBaseNotSupportedException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        if (user != null) {
            session.setAttribute("login", login);
            session.setAttribute("role", user.getRole());
            //Redirection according to PRG Pattern
            resp.sendRedirect(LOGIN_SUCCESS_URL);
        } else {
            //Redirection according to PRG Pattern
            resp.sendRedirect(LOGIN_FAILURE_URL);
        }
        System.out.println("LogInServlet - DoPOST method: " + LocalTime.now());
    }
}
