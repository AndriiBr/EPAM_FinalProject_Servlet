package ua.epam.final_project.controller.cabinet.wallet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.controller.edition_list.prg.BuyEditionSuccessServlet;
import ua.epam.final_project.dao.DaoFactory;
import ua.epam.final_project.dao.DataBaseSelector;
import ua.epam.final_project.exception.DataBaseNotSupportedException;
import ua.epam.final_project.exception.IncorrectPropertyException;
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
import java.sql.SQLException;
import java.time.LocalTime;

import static ua.epam.final_project.util.JSPPathConstant.*;
import static ua.epam.final_project.util.UrlLayoutConstants.*;

@WebServlet(urlPatterns = WALLET_URL)
public class WalletServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(WalletServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        final String login = (String) session.getAttribute("login");

        IUserService userService = ServiceFactory.getUserService();

        User user = null;
        try {
            user = userService.findUserByLogin(login);
            session.setAttribute("balance", user.getBalance());
            req.getRequestDispatcher(WALLET_PAGE).forward(req, resp);
        } catch (UnknownUserException | ServletException| IOException e) {
            logger.warn(e);
            resp.sendRedirect(UNKNOWN_ERROR_URL);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(FILL_UP_WALLET_URL);
    }
}
