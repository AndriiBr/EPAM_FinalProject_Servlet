package ua.epam.final_project.util.servlet_filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocalizationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LocalizationFilter.class);
    private static final String LOCALE = "language";

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("The filter: {} has begun its work", LocalizationFilter.class);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String sessionAttributeLang = (String) session.getAttribute(LOCALE);
        String reqParameterLang = req.getParameter(LOCALE);

        if (reqParameterLang != null) {
            if (reqParameterLang.equals("ua")) {
                session.setAttribute(LOCALE, "ua");
            } else {
                session.setAttribute(LOCALE, "en");
            }
        } else if (sessionAttributeLang != null) {
            if (sessionAttributeLang.equals("ua")) {
                session.setAttribute(LOCALE, "ua");
            } else {
                session.setAttribute(LOCALE, "en");
            }
        } else {
            session.setAttribute(LOCALE, "en");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("The filter: {} has finished its work", LocalizationFilter.class);
    }
}
