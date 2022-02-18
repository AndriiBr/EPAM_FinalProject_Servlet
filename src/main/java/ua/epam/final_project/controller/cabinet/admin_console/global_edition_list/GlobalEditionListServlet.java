package ua.epam.final_project.controller.cabinet.admin_console.global_edition_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.*;
import ua.epam.final_project.service.IEditionService;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.Genre;

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

@WebServlet(urlPatterns = ADMIN_EDITION_LIST_URL)
public class GlobalEditionListServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GlobalEditionListServlet.class);
    private static final String SORT_FILTER = "sortFilter";
    private static final String GENRE_FILTER = "genreFilter";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        int page = 1;
        int recordsPerPage = 4;
        int noOfRecords;
        String orderBy = req.getParameter(SORT_FILTER);
        String genreFilter = req.getParameter(GENRE_FILTER);
        List<Edition> editionList;
        List<Genre> genreList;

        //Save column filter in session
        if (req.getParameter(SORT_FILTER) != null) {
            session.setAttribute(SORT_FILTER, orderBy);
        } else if (session.getAttribute(SORT_FILTER) != null) {
            orderBy = (String)session.getAttribute(SORT_FILTER);
        }

        //Save genre filter in session
        if (req.getParameter(GENRE_FILTER) != null) {
            session.setAttribute(GENRE_FILTER, genreFilter);
        } else if (session.getAttribute(GENRE_FILTER) != null) {
            genreFilter = (String) session.getAttribute(GENRE_FILTER);
        }

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

        try {
            editionList = editionService.findAllEditionsFromTo(recordsPerPage, page, genreFilter, orderBy);
            genreList = genreService.findAllGenres();
            noOfRecords = editionService.getNumberOfEditions(genreFilter);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.setAttribute("editionList", editionList);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("genresList", genreList);
            req.getRequestDispatcher(ADMIN_EDITION_LIST_PAGE).forward(req, resp);
        } catch (UnknownEditionException | UnknownGenreException | ServletException | IOException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
