package ua.epam.final_project.controller.cabinet.admin_console.user_list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = USER_LIST_URL)
public class UserListServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int page = 1;
        int recordsPerPage = 5;
        int noOfRecords = 0;

        List<User> userList;

        //ToDo
        //Put parameter 'page' into form with hidden input value + button
        if (req.getParameter("page") != null) {
            try {
                page = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException e) {
                logger.warn(e);
            }
        }

        IUserService userService = ServiceFactory.getUserService();

        try {
            userList = userService.findAllUsersFromTo(recordsPerPage, page);
            noOfRecords = userService.getNumberOfUsers();
            req.setAttribute("userList", userList);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.getRequestDispatcher(USER_LIST_PAGE).forward(req, resp);
        } catch (UnknownUserException | ServletException | IOException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }
}