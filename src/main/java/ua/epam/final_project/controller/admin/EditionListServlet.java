package ua.epam.final_project.controller.admin;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.edition.Edition;

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

@WebServlet(urlPatterns = "/edition_list")
public class EditionListServlet extends HttpServlet {

    List<Edition> editionList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();

        String currentRole = (String)session.getAttribute("role");

        //access rights check.
        //only admin can access this page
        //other users will see a 404 error
        if (currentRole.equals("1")) {
            DBManager dbManager = DBManager.getInstance();
            try {
                editionList = dbManager.findAllEditions();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            req.setAttribute("editionList", editionList);

            req.getRequestDispatcher(EDITION_LIST_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_404_PAGE).forward(req, resp);
        }

        System.out.println("EditionListServlet - doGET method: " + LocalTime.now());
    }
}
