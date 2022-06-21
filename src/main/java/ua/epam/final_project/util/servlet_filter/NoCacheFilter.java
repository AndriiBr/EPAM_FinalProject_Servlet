package ua.epam.final_project.util.servlet_filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCacheFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(NoCacheFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("The filter: {} has begun its work", NoCacheFilter.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setDateHeader("Expires", 0); // Proxies.

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        logger.info("The filter: {} has finished its work", NoCacheFilter.class);
    }
}
