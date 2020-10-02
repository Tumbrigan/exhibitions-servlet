package com.kucher;

import com.kucher.dao.ConnectionManager;
import com.kucher.dao.DBManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TestTime {
    public static void main(String[] args) {
//        Calendar calendar = new GregorianCalendar();
//        calendar.set(Calendar.HOUR_OF_DAY, 20);
//        calendar.set(Calendar.MINUTE, 0);
//        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
//        fmt.setCalendar(calendar);
//        String dateFormatted = fmt.format(calendar.getTime());
//        System.out.println(dateFormatted);

        insertTime();
    }

    public static void getTime() {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM TIME")) {
            while (set.next()) {
                Time time = set.getTime("time");
                long t = time.getTime();

                Calendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(t);
                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                fmt.setCalendar(calendar);
                String dateFormatted = fmt.format(calendar.getTime());
                System.out.println(dateFormatted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertTime() {
        String query = "INSERT INTO TIME (time) values (?)";
        String twoOClock = "17:00";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, twoOClock);
            int resultSet = statement.executeUpdate();
            System.out.println(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
