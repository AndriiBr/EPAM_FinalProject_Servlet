package ua.epam.final_project.servlet.login;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@WebServlet(urlPatterns = "/new_account")
public class UserRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/view/loginPage/user_registration_form.jsp").forward(req, resp);
        System.out.println("DoGET from UserRegistration Servlet: " + LocalTime.now());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager dbManager = DBManager.getInstance();
        List<User> userList = null;
        boolean newUserAdded = false;

        try {
            userList = dbManager.findAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("password_confirm");

        if (userList != null && password.equals(passwordConfirm)) {
            User user = new User();
            user.setLogin(req.getParameter("username"));
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            user.setRole("2");

            try {
                newUserAdded = dbManager.insertUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (newUserAdded) {
            req.getRequestDispatcher("/WEB-INF/view/loginPage/success.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/view/loginPage/failure.jsp").forward(req, resp);
        }

        System.out.println("DoPOST from UserRegistration Servlet: " + LocalTime.now());

    }
}
