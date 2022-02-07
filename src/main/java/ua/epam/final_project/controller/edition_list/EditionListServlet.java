package ua.epam.final_project.controller.edition_list;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.Edition;
import static ua.epam.final_project.util.UrlLayoutConstants.*;
import static ua.epam.final_project.util.JSPPathConstant.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = MAIN_EDITION_LIST_URL)
public class EditionListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        int noOfRecords = 0;
        String genre = "";
        List<Edition> list = null;
        List<String> genres = null;
        Map<Integer, String> genreMap = null;

        if(req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        if (req.getParameter("genre") != null) {
            genre = req.getParameter("genre");
        }

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            daoFactory.beginTransaction();
            list = daoFactory.getEditionDao().findAllEditionsFromTo(5,page, genre);
            genreMap = daoFactory.getGenreDao().findAllGenres();
            noOfRecords = daoFactory.getEditionDao().getNumberOfEditions();
            daoFactory.commitTransaction();
            genres = new ArrayList<>(genreMap.values());
            genres.add(0, "*");
        } catch (DataBaseNotSupportedException | SQLException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("editionList", list);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("genreMap", genreMap);
        req.setAttribute("genresList", genres);
        req.getRequestDispatcher(EDITION_LIST_PAGE).forward(req, resp);

        System.out.println("EditionListServlet - doGET method: " + LocalTime.now());
    }
}