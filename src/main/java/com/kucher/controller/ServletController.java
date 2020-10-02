package com.kucher.controller;

import com.kucher.command.Command;
import com.kucher.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");
        if (command == null) {
            command = cleanPath(request.getRequestURI());
            System.out.println("Command: " + command);
        }
        String page = CommandFactory.getCommand(command).execute(request, response);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:",""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    private String cleanPath(String path) {
        return  path.replaceAll(".*/exhibitions/", "");
    }
}