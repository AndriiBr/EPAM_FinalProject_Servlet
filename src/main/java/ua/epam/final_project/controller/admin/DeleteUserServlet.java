package ua.epam.final_project.controller.admin;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.User;

import static ua.epam.final_project.util.UrlLayoutConstants.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

@WebServlet(urlPatterns = DELETE_USER_URL)
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userLogin = req.getParameter("user_login");

        DaoFactory daoFactory = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        } catch (DataBaseNotSupportedException e) {
            e.printStackTrace();
        }

        User user = null;

        try {
            assert daoFactory != null;
            user = daoFactory.getUserDao().findUserByLogin(userLogin);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null) {
            //delete user from DB
            try {
                daoFactory.getUserDao().deleteUserByLogin(userLogin);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect(USER_LIST_URL);

        System.out.println("DeleteUserServlet - doPOST method: " + LocalTime.now());
    }
}
