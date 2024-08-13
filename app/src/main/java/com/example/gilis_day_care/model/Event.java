package com.example.gilis_day_care.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Event implements Cloneable {

    private String id;  // Unique identifier
    private String name;
    private String text;
    private String time;
    private String date;

    public Event() {}

    public Event(String name, String text, String time, String date) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID when creating a new Event
        this.name = name;
        this.text = text;
        this.time = time;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public Event clone() {
        try {
            Event clone = (Event) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
