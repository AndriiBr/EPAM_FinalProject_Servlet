package ua.epam.final_project.controller.edition_list.prg;

import static ua.epam.final_project.util.UrlLayoutConstants.*;
import static ua.epam.final_project.util.JSPPathConstant.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

@WebServlet(urlPatterns = BUY_EDITION_SUCCESS_URL)
public class BuyEditionSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BUY_EDITION_SUCCESS_PAGE).forward(req, resp);

        System.out.println("BuyEditionSuccessServlet - DoGET method: " + LocalTime.now());
    }
}
