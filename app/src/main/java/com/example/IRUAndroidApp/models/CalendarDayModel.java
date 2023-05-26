package com.example.IRUAndroidApp.models;

import java.io.Serializable;

public class CalendarDayModel implements Serializable {

    public int day;
    public boolean isCurrentMonth;
    public String dayName;

    public CalendarDayModel(int day, boolean isCurrentMonth, String dayName) {
        this.day = day;
        this.isCurrentMonth = isCurrentMonth;
        this.dayName = dayName;
    }
}

