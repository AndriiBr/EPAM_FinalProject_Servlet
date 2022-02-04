package ua.epam.final_project.controller.wallet;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.dao.MySQLDaoFactory;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.DataNotFoundException;
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

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            user = daoFactory.getUserDao().findUserByLogin(login);
        } catch (SQLException | DataBaseNotSupportedException | DataBaseConnectionException e) {
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
        resp.sendRedirect(FILL_UP_WALLET_URL);

        System.out.println("WalletServlet - doPOST method: " + LocalTime.now());
    }
}
