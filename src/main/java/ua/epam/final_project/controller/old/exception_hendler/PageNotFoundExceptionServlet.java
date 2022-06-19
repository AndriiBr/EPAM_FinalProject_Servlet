package ua.epam.final_project.controller.old.exception_hendler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ua.epam.final_project.util.JSPPathConstant.*;

//@WebServlet(urlPatterns = ERROR_404_PAGE)
public class PageNotFoundExceptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect(ERROR_404_PAGE);
    }
}
