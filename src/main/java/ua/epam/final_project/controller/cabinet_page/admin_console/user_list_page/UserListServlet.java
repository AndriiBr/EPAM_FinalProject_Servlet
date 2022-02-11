package ua.epam.final_project.controller.cabinet_page.admin_console.user_list_page;

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
import java.util.List;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = USER_LIST_URL)
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final HttpSession session = req.getSession();
        final String currentRole = (String)session.getAttribute("role");

        List<User> userList;

        //access rights check.
        //only admin can access this page
        //other users will see a 404 error page
        if (currentRole.equals("1")) {
            try {
                DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
                userList = daoFactory.getUserDao().findAllUsers();
                req.setAttribute("userList", userList);
            } catch (SQLException | DataBaseNotSupportedException e) {
                e.printStackTrace();
            }

            req.getRequestDispatcher(USER_LIST_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_404_PAGE).forward(req, resp);
        }

        System.out.println("UsersListServlet - doGET method: " + LocalTime.now());
    }
}
