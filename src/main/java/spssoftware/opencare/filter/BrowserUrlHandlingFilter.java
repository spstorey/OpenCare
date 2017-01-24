package spssoftware.opencare.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BrowserUrlHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI().replaceAll("%7B", "{");
        if (request.getQueryString() != null && !"".equals(request.getQueryString())) {
            uri = uri + "?" + request.getQueryString().replaceAll("%7B", "{");
        }

        if (uri.contains("{")) {
            response.sendRedirect(uri.substring(0, uri.indexOf("{")));
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig arg0) throws ServletException {}
}