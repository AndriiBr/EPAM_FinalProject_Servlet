package ua.epam.final_project.controller.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(name = "login", urlPatterns = LOGIN_URL)
public class LogInServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LogInServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        } catch (IOException | ServletException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final HttpSession session = req.getSession();

        IUserService userService = ServiceFactory.getUserService();

        try {
            User user = userService.findUserByLoginPassword(login, password);
            //Save user login/role into session
            if (user != null) {
                session.setAttribute("login", login);
                session.setAttribute("role", user.getRole());
                resp.sendRedirect(LOGIN_SUCCESS_URL);
            } else {
                logger.warn(new UnknownUserException());
                resp.sendRedirect(LOGIN_FAILURE_URL);
            }
        } catch (UnknownUserException e) {
            logger.warn(e);
            resp.sendRedirect(LOGIN_FAILURE_URL);
        }
    }
}
