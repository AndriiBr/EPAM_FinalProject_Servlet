package ua.epam.final_project.controller.edition_list.prg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ua.epam.final_project.util.UrlLayoutConstants.*;
import static ua.epam.final_project.util.JSPPathConstant.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = BUY_EDITION_SUCCESS_URL)
public class BuyEditionSuccessServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(BuyEditionSuccessServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher(BUY_EDITION_SUCCESS_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}
