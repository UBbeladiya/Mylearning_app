package com.example.learning_app.Model;

public class WeekData {

    String weekday;
    Float hours;

    public WeekData(String weekday, float hours) {
        this.weekday = weekday;
        this.hours = hours;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }
}
