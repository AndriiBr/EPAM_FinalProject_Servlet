package ua.epam.final_project.controller.edition_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IUserEditionService;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.entity.UserEdition;

import static ua.epam.final_project.util.UrlLayoutConstants.*;
import static ua.epam.final_project.util.JSPPathConstant.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = BUY_EDITION_URL)
public class BuyEditionServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(BuyEditionServlet.class);
    private static final String BUY_EDITION_ATTRIBUTE = "buy_edition_id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login");
        int editionId;

        //Check for edition id
        if (req.getParameter(BUY_EDITION_ATTRIBUTE) != null) {
            editionId = Integer.parseInt(req.getParameter(BUY_EDITION_ATTRIBUTE));
            session.setAttribute(BUY_EDITION_ATTRIBUTE, editionId);
        } else if (session.getAttribute(BUY_EDITION_ATTRIBUTE) != null) {
            editionId = (Integer) session.getAttribute(BUY_EDITION_ATTRIBUTE);
        } else {
            logger.warn(new IOException("No id were provided for edition to buy"));
            resp.sendRedirect(UNKNOWN_ERROR_URL);
            return;
        }
        session.setAttribute(BUY_EDITION_ATTRIBUTE, editionId);

        IUserService userService = ServiceFactory.getUserService();
        IEditionService editionService = ServiceFactory.getEditionService();
        IUserEditionService userEditionService = ServiceFactory.getUserEditionService();

        try {
            User user = userService.findUserByLogin(login);
            Edition edition = editionService.findEditionById(editionId);
            List<UserEdition> userEdition = userEditionService.findAllUserEditionByUserIdEditionId(user, edition);

            if (userEdition.isEmpty()) {
                req.setAttribute("user", user);
                req.setAttribute("edition", edition);
                req.getRequestDispatcher(BUY_EDITION_PAGE).forward(req, resp);
            } else {
                req.getRequestDispatcher(USER_ALREADY_HAS_THIS_EDITION_PAGE).forward(req, resp);
            }
        } catch (UnknownUserException | UnknownEditionException | UnknownUserEditionPairException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int editionId = (Integer)session.getAttribute(BUY_EDITION_ATTRIBUTE);
        String login = (String) session.getAttribute("login");

        IUserService userService = ServiceFactory.getUserService();
        IEditionService editionService = ServiceFactory.getEditionService();
        IUserEditionService userEditionService = ServiceFactory.getUserEditionService();

        try {
            User  user = userService.findUserByLogin(login);
            Edition edition = editionService.findEditionById(editionId);
            if (userEditionService.insertUserEdition(user, edition)) {
                resp.sendRedirect(BUY_EDITION_SUCCESS_URL);
            } else {
                resp.sendRedirect(BUY_EDITION_FAILURE_URL);
            }
        } catch (UnknownUserException | UnknownEditionException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
