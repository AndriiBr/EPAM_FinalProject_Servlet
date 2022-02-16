package ua.epam.final_project.controller.cabinet_page.admin_console.user_list_page;

import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.util.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = CHANGE_USER_STATUS_URL)
public class ChangeUserStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newRole = req.getParameter("status");
        String userLogin = req.getParameter("userName");
        User user;

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
            user = daoFactory.getUserDao().findUserByLogin(userLogin);
            user.setRole(newRole);
            daoFactory.beginTransaction();
            daoFactory.getUserDao().updateUser(user);
            daoFactory.commitTransaction();
        } catch (DataBaseNotSupportedException | SQLException | DataBaseConnectionException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(USER_LIST_URL);
        System.out.println("ChangeUserStatusServlet - DoPOST method: " + LocalTime.now());
    }
}
