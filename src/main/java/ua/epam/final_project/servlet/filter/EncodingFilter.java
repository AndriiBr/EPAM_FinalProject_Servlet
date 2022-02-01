package ua.epam.final_project.servlet.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String ENCODING_UTF_8 = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("Encoding Filter ----------->> START");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(ENCODING_UTF_8);
        }

        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding(ENCODING_UTF_8);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Encoding Filter ----------->> END");
    }
}
