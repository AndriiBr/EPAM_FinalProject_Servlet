package ua.epam.final_project.controller.old.edition_list.prg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = BUY_EDITION_FAILURE_URL)
public class BuyEditionFailureServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(BuyEditionFailureServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher(BUY_EDITION_FAILURE_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
