package com.example.gilis_day_care.Interface;

import com.example.gilis_day_care.Model.Event;

import java.util.ArrayList;

public interface EventListCallBack {

    void onLoadSucceeded(ArrayList<Event> events);
    void onLoadFailed(Exception exception);

}
