package ua.epam.final_project.controller.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = LOGOUT_URL)
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        final String currentRole = (String)session.getAttribute("role");

        if (currentRole != null) {
            session.removeAttribute("login");
            session.removeAttribute("role");

            //Redirection according to PRG Pattern
            resp.sendRedirect(MAIN_URL);
        }

        System.out.println("LogOutServlet - DoGET method: " + LocalTime.now());
    }
}
