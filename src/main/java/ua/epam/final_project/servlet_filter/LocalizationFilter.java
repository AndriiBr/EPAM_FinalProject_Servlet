package ua.epam.final_project.servlet_filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.final_project.util.localization.LocalizationFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "localization filter", urlPatterns = "/*")
public class LocalizationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LocalizationFilter.class);

    private static final String LOCALIZATION = "localization";
    private static final String LANGUAGE = "language";

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info( "The filter: {} has begun its work", LocalizationFilter.class);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String sessionAttributeLang = (String) session.getAttribute(LANGUAGE);
        String reqParameterLang = req.getParameter(LANGUAGE);

        if (reqParameterLang != null) {
            if (reqParameterLang.equals("en")) {
                req.setAttribute(LOCALIZATION, LocalizationFactory.getLanguageResourceBundle("en"));
            } else {
                req.setAttribute(LOCALIZATION, LocalizationFactory.getLanguageResourceBundle("ua"));
            }
        } else if (sessionAttributeLang != null) {
            if (sessionAttributeLang.equals("en")) {
                req.setAttribute(LOCALIZATION, LocalizationFactory.getLanguageResourceBundle("en"));
            } else {
                req.setAttribute(LOCALIZATION, LocalizationFactory.getLanguageResourceBundle("ua"));
            }
        } else {
            session.setAttribute(LANGUAGE, "ua");
            req.setAttribute(LOCALIZATION, LocalizationFactory.getLanguageResourceBundle("ua"));
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info( "The filter: {} has finished its work", LocalizationFilter.class);
    }
}
