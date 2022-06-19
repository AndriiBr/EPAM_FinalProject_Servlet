package ua.epam.final_project.controller.old.cabinet.subscription;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.entity.dto.UserDto;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IUserEditionService;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = UNSUBSCRIBE_EDITION_LIST_URL)
public class UnsubscribeEditionServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UnsubscribeEditionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        int editionId = Integer.parseInt(req.getParameter("edition_id"));
        String login = (String) session.getAttribute("login");

        IUserService userService = ServiceFactory.getUserService();
        IEditionService editionService = ServiceFactory.getEditionService();
        IUserEditionService userEditionService = ServiceFactory.getUserEditionService();

        try {
            UserDto userDto = userService.findUserByLogin(login);
            Edition edition = editionService.findEditionById(editionId);
            if (userEditionService.deleteUserEdition(userDto, edition)) {
                resp.sendRedirect(USER_SUBSCRIPTION_LIST_URL);
            } else {
                resp.sendRedirect(UNKNOWN_ERROR_URL);
            }
        } catch (UnknownUserException | UnknownEditionException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
