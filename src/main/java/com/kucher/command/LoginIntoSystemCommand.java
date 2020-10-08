package com.kucher.command;

import com.kucher.dao.DBManager;
import com.kucher.model.User;
import com.kucher.util.PasswordManager;
import com.kucher.util.PathManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class LoginIntoSystemCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginIntoSystemCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        session.removeAttribute("incorrectInput");

        LOGGER.info("E-mail: " + email);

        if (nonNull(email) && nonNull(password)) {
            User user = DBManager.getInstance().getUserByEmail(email);

            if (user != null) {
                String hash = PasswordManager.getPasswordHash(password);
                if (hash.equals(user.getPassword())) {
                    User.ROLE role = user.getRole();
                    LOGGER.info("role: " + role);
                    if (role == User.ROLE.ADMIN) {
                        session.setAttribute("role", role);
                        return PathManager.getPath("admin.home.redirect");
                    } else if (role == User.ROLE.USER) {
                        session.setAttribute("role", role);
                        return PathManager.getPath("user.home.redirect");
                    }
                }
            }
        }
        LOGGER.info("Input is incorrect");
        session.setAttribute("incorrectInput", "inputIsIncorrect");
        return PathManager.getPath("login.redirect");
    }
}