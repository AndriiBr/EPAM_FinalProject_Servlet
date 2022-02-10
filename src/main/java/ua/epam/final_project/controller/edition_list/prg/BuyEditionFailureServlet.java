package ua.epam.final_project.controller.edition_list.prg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static ua.epam.final_project.util.JSPPathConstant.BUY_EDITION_FAILURE_PAGE;
import static ua.epam.final_project.util.UrlLayoutConstants.BUY_EDITION_FAILURE_URL;

@WebServlet(urlPatterns = BUY_EDITION_FAILURE_URL)
public class BuyEditionFailureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BUY_EDITION_FAILURE_PAGE).forward(req, resp);

        System.out.println("BuyEditionFailureServlet - DoGET method: " + LocalTime.now());
    }
}
