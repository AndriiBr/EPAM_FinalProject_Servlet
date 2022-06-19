package ua.epam.final_project.controller.old.cabinet.admin_console.global_edition_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.final_project.util.JSPPathConstant.ADD_NEW_EDITION_FAILURE_PAGE;
import static ua.epam.final_project.util.UrlLayoutConstants.ADD_NEW_EDITION_FAILURE_URL;
import static ua.epam.final_project.util.UrlLayoutConstants.UNKNOWN_ERROR_URL;

@WebServlet(urlPatterns = ADD_NEW_EDITION_FAILURE_URL)
public class AddNewEditionFailureServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddNewEditionFailureServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher(ADD_NEW_EDITION_FAILURE_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
