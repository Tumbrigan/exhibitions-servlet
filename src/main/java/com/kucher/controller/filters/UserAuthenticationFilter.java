package com.kucher.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/exhibitions/user/*")
public class UserAuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session == null || !"user".equals(session.getAttribute("role"))) {
            request.getRequestDispatcher("/exhibitions/login").forward(request, response);
        }
        chain.doFilter(request, response);
    }
}