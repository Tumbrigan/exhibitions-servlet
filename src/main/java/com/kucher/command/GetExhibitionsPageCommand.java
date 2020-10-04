package com.kucher.command;

import com.kucher.dao.DBManager;
import com.kucher.model.Exhibition;
import com.kucher.util.PathManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetExhibitionsPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DBManager dbManager = DBManager.getInstance();
        List<Exhibition> exhibitions = dbManager.getAllExhibitions();
        List<Exhibition.Category> categories = dbManager.getCategoryList();
        List<Exhibition.Hall> halls = dbManager.getHallList();
        int maxSeatAmount = DBManager.getInstance().getMaxSeatAmount();
        request.setAttribute("exhibitions", exhibitions);
        request.setAttribute("categories", categories);
        request.setAttribute("maxSeatAmount", maxSeatAmount);
        request.setAttribute("halls", halls);
        return PathManager.getPath("admin.exhibitions");
    }
}