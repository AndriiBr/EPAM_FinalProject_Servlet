package ua.epam.final_project.servlet.login;

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
        session.removeAttribute("login");
        session.removeAttribute("role");

        req.getRequestDispatcher("WEB-INF/view/mainPage/main_page.jsp").forward(req, resp);

        System.out.println("DoGET from LogOut Servlet: " + LocalTime.now());
    }
}
