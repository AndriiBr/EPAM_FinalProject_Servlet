package ua.epam.final_project.controller.cabinet.subscription;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.Genre;
import ua.epam.final_project.util.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = USER_SUBSCRIPTION_LIST_URL)
public class SubscriptionListServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(SubscriptionListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login");
        int page = 1;
        int recordsPerPage = 4;
        int noOfRecords;
        String orderBy = "";
        String genreFilter = req.getParameter("genreFilter");
        req.setAttribute("genreFilter", genreFilter);
        List<Edition> editionList;
        List<Genre> genreList;

        //ToDo
        //Put parameter 'page' into form with hidden input value + button
        if(req.getParameter("page") != null) {
            try {
                page = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException e) {
                logger.warn(e);
            }
        }
        //ToDo
        if (req.getParameter("genre") != null) {
            orderBy = req.getParameter("genre");
        }

        IEditionService editionService = ServiceFactory.getEditionService();
        IGenreService genreService = ServiceFactory.getGenreService();
        IUserService userService = ServiceFactory.getUserService();


        try {
            User user = userService.findUserByLogin(login);
            editionList = editionService.findAllEditionsFromTo(user, true, recordsPerPage, page, genreFilter, orderBy);
            noOfRecords = editionService.getNumberOfEditions(user, true, genreFilter);
            genreList = genreService.findAllGenres();
            forwardToPage(req, resp, page, noOfRecords, recordsPerPage, editionList, genreList);
        } catch (UnknownUserException | UnknownEditionException | UnknownGenreException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_PAGE);
        }
    }

    private void forwardToPage(HttpServletRequest req, HttpServletResponse resp,
                               int page, int noOfRecords, int recordsPerPage,
                               List<Edition> editionList, List<Genre> genreList) throws IOException {
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("editionList", editionList);
        req.setAttribute("genresList", genreList);
        try {
            req.getRequestDispatcher(USER_SUBSCRIPTION_LIST_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
