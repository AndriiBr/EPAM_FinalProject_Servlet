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

        int page = 1;
        int recordsPerPage = 5;
        int noOfRecords = 0;

        List<User> userList;

        if(req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        //access rights check.
        //only admin can access this page
        //other users will see a 404 error page
        if (currentRole.equals("1")) {
            try {
                DaoFactory daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
                userList = daoFactory.getUserDao().findAllUsersFromTo(recordsPerPage, page);
                noOfRecords = daoFactory.getUserDao().getNumberOfUsers();
                req.setAttribute("userList", userList);
            } catch (SQLException | DataBaseNotSupportedException e) {
                e.printStackTrace();
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);

            req.getRequestDispatcher(USER_LIST_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_404_PAGE).forward(req, resp);
        }

        System.out.println("UsersListServlet - doGET method: " + LocalTime.now());
    }
}