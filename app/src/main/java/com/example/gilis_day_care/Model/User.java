package com.example.gilis_day_care.Model;


public class User {

    private String uid;
    private String name;
    private boolean isRegistered;

    public User() {}

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }


    public boolean isRegistered() {
        return isRegistered;
    }

    public User setRegistered(boolean registered) {
        isRegistered = registered;
        return this;
    }


    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", isRegistered=" + isRegistered +
                '}';
    }
}


