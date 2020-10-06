package com.kucher.controller.listeners;

import com.kucher.dao.DBManager;
import com.kucher.model.Exhibition;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

//@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        DBManager dbManager = DBManager.getInstance();
        List<Exhibition> exhibitions = dbManager.getAllExhibitions();
        se.getSession().setAttribute("exhibitions", exhibitions);
    }
}
