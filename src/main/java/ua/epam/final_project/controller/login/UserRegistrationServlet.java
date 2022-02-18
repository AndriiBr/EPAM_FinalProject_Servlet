package ua.epam.final_project.controller.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.exception.UnknownUserException;
import ua.epam.final_project.service.IUserService;
import ua.epam.final_project.service.ServiceFactory;
import ua.epam.final_project.util.entity.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(name = "user_registration", urlPatterns = USER_REGISTRATION_URL)
public class UserRegistrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserRegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher(USER_REGISTRATION_PAGE).forward(req, resp);
        } catch (IOException | ServletException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<User> userList;
        User user;
        IUserService userService = ServiceFactory.getUserService();
        try {
            userList = userService.findAllUsers();
        } catch (UnknownUserException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
            return;
        }

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("password_confirm");

        //Error flag to catch different errors in the user input data:
        //         * 0 - everything correct
        //         * 1 - (deprecated) password does not match - replaced by JS Validator
        //         * 2 - login is already exist
        //         * 3 - email is already exist
        String errorFlag = validateInputData(session, userList, login, email, password, confirmPassword);
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

            if (userService.insertUser(user)) {
                //Automatically log-in after successful registration
                logInUser(user, session);
                resp.sendRedirect(REGISTRATION_SUCCESS_URL);
            }
        } else {
            resp.sendRedirect(REGISTRATION_FAILURE_URL);
        }
    }


    //Autologin after registration
    private void logInUser(User user, HttpSession session) {
        session.setAttribute("login", user.getLogin());
        session.setAttribute("role", user.getRole());
    }

    private String validateInputData(HttpSession session,
                                     List<User> userList,
                                     String login,
                                     String email,
                                     String password,
                                     String confirmPassword) {
        String validatingResult;

        if (!validatePassword(password, confirmPassword)) {
            validatingResult = "1";
        } else if (validateLogin(login, userList)) {
            validatingResult = "2";
            session.setAttribute("formLogin", login);
        } else if (validateEmail(email, userList)) {
            validatingResult = "3";
            session.setAttribute("formEmail", email);
        } else {
            validatingResult = "0";
        }
        return validatingResult;
    }

    private boolean validatePassword(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

    private boolean validateLogin(String login, List<User> userList) {
        return userList.stream()
                .anyMatch(x -> x.getLogin().equals(login));
    }

    private boolean validateEmail(String email, List<User> userList) {
        return userList.stream()
                .anyMatch(x -> x.getEmail().equals(email));
    }
}
