package ua.epam.final_project.controller.cabinet_page.wallet;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.User;

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

@WebServlet(urlPatterns = FILL_UP_WALLET_URL)
public class FillUpWalletServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(FILL_UP_WALLET_PAGE).forward(req, resp);

        System.out.println("FillUpWalletServlet - doGet method: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DaoFactory daoFactory = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        } catch (DataBaseNotSupportedException  e) {
            e.printStackTrace();
        }

        final HttpSession session = req.getSession();

        final String login = (String) session.getAttribute("login");
        final int money = Integer.parseInt(req.getParameter("money"));

        User user = null;

        try {
            user = daoFactory.getUserDao().findUserByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null) {
            try {
                daoFactory.getUserDao().updateUserBalance(user, money);
                resp.sendRedirect(WALLET_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("FillUpWalletServlet - doPOST method: " + LocalTime.now());
    }
}
