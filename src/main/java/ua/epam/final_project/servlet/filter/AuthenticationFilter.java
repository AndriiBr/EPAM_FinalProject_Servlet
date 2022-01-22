package ua.epam.final_project.servlet.filter;

import ua.epam.final_project.database.DBManager;
import ua.epam.final_project.util.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;

        final String login = request.getParameter("username");
        final String password = request.getParameter("password");

        final HttpSession session = request.getSession();

        //Logged user
//        if (nonNull(session) &&
//                nonNull(session.getAttribute("username")) &&
//                nonNull(session.getAttribute("password"))) {
//
//            try {
//                User user = DBManager.getInstance().getUser(
//                        (String) session.getAttribute("username"),
//                        (String) session.getAttribute("password"));
//                session.setAttribute("username", user.getLogin());
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        } else if (dao.get().userIsExist(login, password)) {
//            final User user = DBManager.getInstance().getUser();
//            request.getSession().setAttribute("login", login);
//            request.getSession().setAttribute("password", password);
//            request.getSession().setAttribute("role", role);
//
//            moveToMenu(request, response, role);
//
//        } else {
//            moveToMenu(request, response, User.ROLE.UNKNOWN);
//        }
    }

    @Override
    public void destroy() {}


    private void moveToMenu(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final User user) throws ServletException, IOException {
        if (user.getRole().equals("1")) {
            request.getRequestDispatcher("/WEB-INF/view/admin_menu.jsp").forward(request, response);
        } else if (user.getRole().equals("2")) {
            request.getRequestDispatcher("/WEB-INF/view/user_menu.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}
