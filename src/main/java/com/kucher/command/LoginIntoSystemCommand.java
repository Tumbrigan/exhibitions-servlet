package com.kucher.command;

import com.kucher.dao.DBManager;
import com.kucher.model.User;
import com.kucher.util.PasswordManager;
import com.kucher.util.PathManager;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class LoginIntoSystemCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if (nonNull(email) && nonNull(password)) {
            User user = DBManager.getInstance().getUserByEmail(email);

            if (user != null) {
                String hash = PasswordManager.getPasswordHash(password);
                if (hash.equals(user.getPassword())) {
                    User.ROLE role = user.getRole();

                    if (role == User.ROLE.ADMIN) {
                        session.setAttribute("role", "admin");
                        return PathManager.getPath("admin.home.redirect");

                    } else if (user.getRole() == User.ROLE.USER) {
                        session.setAttribute("role", "user");
                        return PathManager.getPath("user.home.redirect");
                    }
                }
            }
        }
        session.setAttribute("incorrectInput", "inputIsIncorrect");
        return PathManager.getPath("login");
    }

    @Test
    public void getUserTest() {
        User user = DBManager.getInstance().getUserByEmail("ihor.kucher.94@gmail.com");
        System.out.println(user);
    }
}