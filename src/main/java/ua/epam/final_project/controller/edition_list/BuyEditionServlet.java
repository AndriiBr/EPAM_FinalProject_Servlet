package ua.epam.final_project.controller.edition_list;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.util.entity.User;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = BUY_EDITION_URL)
public class BuyEditionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String editionTitle = req.getParameter("edition_title");
        String login = (String) session.getAttribute("login");

        DaoFactory daoFactory = null;
        User user = null;


    }
}
