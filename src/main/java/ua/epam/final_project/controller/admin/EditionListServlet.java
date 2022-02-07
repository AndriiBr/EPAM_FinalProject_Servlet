package ua.epam.final_project.controller.admin;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = EDITION_LIST_URL)
public class EditionListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        String currentRole = (String)session.getAttribute("role");

        List<Edition> editionList;

        //access rights check.
        //only admin can access this page
        //other users will see a 404 error
        if (currentRole.equals("1")) {
            try {
                DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
                editionList = daoFactory.getEditionDao().findAllEditions();
                req.setAttribute("editionList", editionList);
            } catch (SQLException | DataBaseNotSupportedException e) {
                e.printStackTrace();
            }

            req.getRequestDispatcher(EDITION_LIST_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_404_PAGE).forward(req, resp);
        }

        System.out.println("EditionListServlet - doGET method: " + LocalTime.now());
    }
}
