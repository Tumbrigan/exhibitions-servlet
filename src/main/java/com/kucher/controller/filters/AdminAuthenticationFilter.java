package com.kucher.controller.filters;

import com.kucher.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/exhibitions/admin/*")
public class AdminAuthenticationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AdminAuthenticationFilter.class.getName());

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Inside admin filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            LOGGER.info("there is no one admin role attribute");
            request.getRequestDispatcher("/exhibitions/login").forward(request, response);
        } else {
            User.ROLE role = (User.ROLE) session.getAttribute("role");
            LOGGER.info("role is " + role.toString());
            if (role != User.ROLE.ADMIN) {
                request.getRequestDispatcher("/exhibitions/login").forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }
}