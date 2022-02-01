package ua.epam.final_project.controller.wallet;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = WALLET_URL)
public class WalletServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        final String login = (String) session.getAttribute("login");

        User user = null;
        DBManager dbManager = DBManager.getInstance();

        try {
            user = dbManager.getUserByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null) {
            session.setAttribute("balance", user.getBalance());
        }

        req.getRequestDispatcher(WALLET_PAGE).forward(req, resp);

        System.out.println("WalletServlet - doGet method: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(FILL_UP_WALLET_PAGE).forward(req, resp);

        System.out.println("WalletServlet - doPOST method: " + LocalTime.now());
    }
}
