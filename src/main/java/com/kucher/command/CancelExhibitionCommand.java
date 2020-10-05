package com.kucher.command;

import com.kucher.dao.DBManager;
import com.kucher.util.PathManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CancelExhibitionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DBManager dbManager = DBManager.getInstance();
        dbManager.cancelExhibition(request.getParameter("id"));
        return PathManager.getPath("admin.exhibitions.redirect");
    }
}