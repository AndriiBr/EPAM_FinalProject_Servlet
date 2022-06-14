package ua.epam.final_project.controller.cabinet.admin_console.user_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.entity.User;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = DELETE_USER_URL)
public class DeleteUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeleteUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("user_login");

        IUserService userService = ServiceFactory.getUserService();

        User user = null;
        try {
            user = userService.findUserByLogin(login);
            if (userService.deleteUser(user)) {
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
