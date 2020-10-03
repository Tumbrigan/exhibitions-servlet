package com.kucher;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.add(Calendar.DAY_OF_MONTH, 26);
        System.out.println(calendar.getTime());
    }

}
