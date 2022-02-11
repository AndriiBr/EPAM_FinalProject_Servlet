package ua.epam.final_project.controller.cabinet_page.subscription;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = USER_SUBSCRIPTION_LIST_URL)
public class SubscriptionListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(USER_SUBSCRIPTION_LIST_PAGE).forward(req, resp);

        System.out.println("SubscriptionListServlet - doGET method: " + LocalTime.now());
    }
}
