package org.whatisme.studentqa.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"/*", "/admin/*"})
public class LoginFilter implements Filter {
    private String redirectUrl;
    private String uncheckedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redirectUrl = "/login.html";
        uncheckedUrls = "/login.html,/Login,/sign-up.html,/Sign-up,/index.html,/cur-user";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String servletPath = httpRequest.getServletPath();
        List<String> urls = Arrays.asList(uncheckedUrls.split(","));
        if (urls.contains(servletPath)) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }
        Object user = httpRequest.getSession().getAttribute("user");
        if ((user == null)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + redirectUrl);
            return;
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
