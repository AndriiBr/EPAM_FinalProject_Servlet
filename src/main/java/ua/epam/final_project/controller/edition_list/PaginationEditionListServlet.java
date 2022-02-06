package ua.epam.final_project.controller.edition_list;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.Edition;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@WebServlet(urlPatterns = "/main_edition_list")
public class PaginationEditionListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        int noOfRecords = 0;
        List<Edition> list = null;

        if(req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        DaoFactory daoFactory = null;
        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            assert daoFactory != null;
            list = daoFactory.getEditionDao().findAllEditionsFromTo(5,page);
            noOfRecords = daoFactory.getEditionDao().getNumberOfEditions();
        } catch (DataBaseNotSupportedException | DataBaseConnectionException | SQLException e) {
            e.printStackTrace();
        }

        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("editionList", list);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("WEB-INF/view/edition_page/main_edition_page.jsp").forward(req, resp);

        System.out.println("PaginationEditionListServlet - doGET method: " + LocalTime.now());
    }
}
