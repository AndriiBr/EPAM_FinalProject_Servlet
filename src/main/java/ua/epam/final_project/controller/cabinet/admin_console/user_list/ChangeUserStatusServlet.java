package ua.epam.final_project.controller.cabinet.admin_console.user_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.controller.edition_list.EditionListServlet;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.IncorrectPropertyException;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = CHANGE_USER_STATUS_URL)
public class ChangeUserStatusServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ChangeUserStatusServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newRole = req.getParameter("status");
        String login = req.getParameter("userName");

        IUserService userService = ServiceFactory.getUserService();

        try {
            User user = userService.findUserByLogin(login);
            user.setRole(newRole);
            if (userService.updateUser(user)) {
                resp.sendRedirect(USER_LIST_URL);
            } else {
                resp.sendRedirect(UNKNOWN_ERROR_URL);
            }
        } catch (UnknownUserException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
