package ua.epam.final_project.controller;

import ua.epam.final_project.controller.util.CommandHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Admin controller", urlPatterns = "/admin/*")
@MultipartConfig(location = "E:/Programming/Projects/EPAM_FinalProject_Servlet/static_content/img/")
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandHandler.handleGetRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandHandler.handlePostRequest(req, resp);
    }
}
