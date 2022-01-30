package ua.epam.final_project.controller.login;

import static ua.epam.final_project.util.JSPPathConstant.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;

@WebServlet(urlPatterns = "/logout")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        final String currentRole = (String)session.getAttribute("role");

        if (currentRole != null) {
            session.removeAttribute("login");
            session.removeAttribute("role");

            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_404_PAGE).forward(req, resp);
        }

        System.out.println("LogOutServlet - DoGET method: " + LocalTime.now());
    }
}
