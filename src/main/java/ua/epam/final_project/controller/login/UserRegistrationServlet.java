package ua.epam.final_project.controller.login;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(name = "user_registration", urlPatterns = USER_REGISTRATION_URL)
public class UserRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(USER_REGISTRATION_PAGE).forward(req, resp);

        System.out.println("UserRegistrationServlet - DoGET method: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        List<User> userList = null;
        User user = null;
        DaoFactory daoFactory = null;

        try {
            daoFactory = DaoFactory.getDaoFactory(DataBaseSelector.MY_SQL);
        } catch (DataBaseNotSupportedException e) {
            e.printStackTrace();
        }

        final HttpSession session = req.getSession();

        //Error flag to catch different errors in the user input data:
        //         * 0 - everything correct
        //         * 1 - (deprecated) password does not match - replaced by JS Validator
        //         * 2 - login is already exist
        //         * 3 - email is already exist
        String errorFlag = "0";

        try {
            userList = daoFactory.getUserDao().findAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (userList != null && userList.stream().anyMatch(x -> x.getLogin().equals(login))) {
            errorFlag = "2";
            session.setAttribute("formLogin", login);
        } else if (userList != null && userList.stream().anyMatch(x -> x.getEmail().equals(email))) {
            errorFlag = "3";
            session.setAttribute("formEmail", email);
        }

        session.setAttribute("errorFlag", errorFlag);

        //Create new user entity if all input data is correct.
        //And if user and email are not exist in database.
        if (errorFlag.equals("0")) {
            user = new User();
            user.setLogin(login);
            user.setEmail(email);
            user.setPassword(password);
            user.setBalance(0);
            user.setRole("2");
        }

        //Put new user into DB
        if (user != null) {
            try {
                daoFactory.getUserDao().insertUser(user);
                //Automatically log-in after successful registration
                logInUser(user, req);
                //Redirection according to PRG Pattern
                resp.sendRedirect(REGISTRATION_SUCCESS_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //Redirection according to PRG Pattern
            resp.sendRedirect(REGISTRATION_FAILURE_URL);
        }


        System.out.println("UserRegistrationServlet - DoGET method: " + LocalTime.now());
    }

    //Autologin after registration
    private void logInUser (User user, HttpServletRequest req) {
        final HttpSession session = req.getSession();
        session.setAttribute("login", user.getLogin());
        session.setAttribute("role", user.getRole());
    }
}
