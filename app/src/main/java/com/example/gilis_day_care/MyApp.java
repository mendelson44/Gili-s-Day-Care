package com.example.gilis_day_care;

import android.app.Application;

import com.example.gilis_day_care.Activities.FullScreenManager;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FullScreenManager.init(this);

    }
}