package ua.epam.final_project.controller.edition_list.prg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static ua.epam.final_project.util.JSPPathConstant.ADD_NEW_EDITION_FAILURE_PAGE;
import static ua.epam.final_project.util.UrlLayoutConstants.ADD_NEW_EDITION_FAILURE_URL;

@WebServlet(urlPatterns = ADD_NEW_EDITION_FAILURE_URL)
public class AddNewEditionFailureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ADD_NEW_EDITION_FAILURE_PAGE).forward(req, resp);

        System.out.println("AddNewEditionFailureServlet - DoGET method: " + LocalTime.now());
    }
}
