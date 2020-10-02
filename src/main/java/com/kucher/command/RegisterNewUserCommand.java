package com.kucher.command;

import com.kucher.dao.DBManager;
import com.kucher.util.PasswordManager;
import com.kucher.util.PathManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterNewUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.removeAttribute("emailIsPresent");

        String email = request.getParameter("email");

        DBManager dbManager = DBManager.getInstance();
        if (dbManager.isEmailPresent(email)) {
            session.setAttribute("emailIsPresent", email);
            return PathManager.getPath("false.registration");
        }

        String password = request.getParameter("password");
        String hash = PasswordManager.getPasswordHash(password);

        dbManager.createUser(email, hash);
        session.setAttribute("role", "user");
        return PathManager.getPath("user.home.redirect");
    }
}
