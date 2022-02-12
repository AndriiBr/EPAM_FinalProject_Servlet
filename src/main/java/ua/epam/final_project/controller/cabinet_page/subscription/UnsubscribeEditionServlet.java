package ua.epam.final_project.controller.cabinet_page.subscription;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.Edition;
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

@WebServlet(urlPatterns = UNSUBSCRIBE_EDITION_LIST_URL)
public class UnsubscribeEditionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String editionTitle = req.getParameter("edition_title");
        String login = (String) session.getAttribute("login");

        boolean deleteOperation = false;

        DaoFactory daoFactory = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            daoFactory.beginTransaction();
            User user = daoFactory.getUserDao().findUserByLogin(login);
            Edition edition = daoFactory.getEditionDao().getEditionByTitle(editionTitle);
            deleteOperation = daoFactory.getUserEditionDao().deleteUserEdition(user, edition);

            daoFactory.commitTransaction();
        } catch (DataBaseNotSupportedException | SQLException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        if (deleteOperation) {
            resp.sendRedirect(USER_SUBSCRIPTION_LIST_URL);
        } else {
            resp.sendRedirect("/");
        }


        System.out.println("UnsubscribeEditionServlet - doPOST method: " + LocalTime.now());
    }
}
