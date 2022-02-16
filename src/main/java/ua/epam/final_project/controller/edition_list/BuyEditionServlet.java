package ua.epam.final_project.controller.edition_list;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.Edition;
import ua.epam.final_project.util.entity.User;
import ua.epam.final_project.util.entity.UserEdition;

import static ua.epam.final_project.util.UrlLayoutConstants.*;
import static ua.epam.final_project.util.JSPPathConstant.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@WebServlet(urlPatterns = BUY_EDITION_URL)
public class BuyEditionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int editionId = Integer.parseInt(req.getParameter("buy_edition_id"));
        String login = (String) session.getAttribute("login");

        DaoFactory daoFactory = null;
        User user = null;
        Edition edition = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            user = daoFactory.getUserDao().findUserByLogin(login);
            edition = daoFactory.getEditionDao().getEditionByTitle(editionId);
        } catch (SQLException | DataBaseNotSupportedException e) {
            e.printStackTrace();
        }

        if (daoFactory != null && user != null && edition != null) {
            List<UserEdition> userEdition;

            try {
                //Check if user already has this edition
                //Redirect to message page if yes
                userEdition = daoFactory.getUserEditionDao()
                        .findAllUserEditionByUserIdEditionId(user.getId(), edition.getId());
                if (!userEdition.isEmpty()) {
                    req.getRequestDispatcher(USER_ALREADY_HAS_THIS_EDITION_PAGE).forward(req, resp);
                } else {
                    req.setAttribute("user", user);
                    req.setAttribute("edition", edition);
                    req.getRequestDispatcher(BUY_EDITION_PAGE).forward(req, resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("BuyEditionServlet - DoGET method: " + LocalTime.now());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int editionId = Integer.parseInt(req.getParameter("buy_edition_id"));
        String login = (String) session.getAttribute("login");

        User user = (User) req.getAttribute("user");
        Edition edition = (Edition) req.getAttribute("edition");
        DaoFactory daoFactory = null;
        boolean insertUserEdition = false;
        boolean payment = false;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            daoFactory.beginTransaction();
            user = daoFactory.getUserDao().findUserByLogin(login);
            edition = daoFactory.getEditionDao().getEditionByTitle(editionId);
            daoFactory.commitTransaction();
        } catch (SQLException | DataBaseConnectionException | DataBaseNotSupportedException e) {
            e.printStackTrace();
        }

        if (daoFactory != null && user != null && edition != null) {
            try {
                daoFactory.beginTransaction();
                insertUserEdition = daoFactory.getUserEditionDao().insertUserEdition(user, edition);
                payment = daoFactory.getUserDao().updateUserBalance(user, (edition.getPrice() * -1));
                daoFactory.commitTransaction();
            } catch (SQLException | DataBaseConnectionException e) {
                e.printStackTrace();
            }
        }

        if (insertUserEdition && payment) {
            resp.sendRedirect(BUY_EDITION_SUCCESS_URL);
        } else {
            resp.sendRedirect(BUY_EDITION_FAILURE_URL);
        }

        System.out.println("BuyEditionServlet - DoPOST method: " + LocalTime.now());
    }
}
