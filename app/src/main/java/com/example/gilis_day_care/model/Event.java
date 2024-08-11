package com.example.gilis_day_care.model;

import java.util.List;
import java.util.Objects;

public class Event {


    private String name;
    private String text;
    private String time;
    private String date;
;


    public Event() {}

    public Event(String name, String text, String time, String date) {
        this.name = name;
        this.text = text;
        this.time = time;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
