package ua.epam.final_project.util.servlet_filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.epam.final_project.util.JSPPathConstant.*;

@WebFilter(urlPatterns = "/cabinet/admin_console/*")
public class AdminAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AdminAccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info( "The filter: {} has begun its work", LocalizationFilter.class);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");

        if (role != null && role.equals("1")) {
            filterChain.doFilter(request, response);
        } else {
            resp.sendRedirect(ERROR_404_PAGE);
        }
    }

    @Override
    public void destroy() {
        logger.info( "The filter: {} has finished its work", AdminAccessFilter.class);
    }
}
