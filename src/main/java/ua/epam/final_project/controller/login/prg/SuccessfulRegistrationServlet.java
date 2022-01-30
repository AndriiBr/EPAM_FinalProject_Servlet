package ua.epam.final_project.controller.login.prg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.epam.final_project.util.JSPPathConstant.REGISTRATION_SUCCESS_PAGE;

@WebServlet(urlPatterns = "/successful_registration")
public class SuccessfulRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTRATION_SUCCESS_PAGE).forward(req, resp);
    }
}
