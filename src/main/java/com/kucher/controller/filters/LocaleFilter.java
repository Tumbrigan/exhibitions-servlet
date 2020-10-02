package com.kucher.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String path = request.getRequestURI();
        String page = request.getHeader("referer");

        if (path.contains("/language/")) {
            String language = path.replaceAll(".*/language/", "");
            session.setAttribute("language", new Locale(language));
            response.sendRedirect(request.getContextPath() + page);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
