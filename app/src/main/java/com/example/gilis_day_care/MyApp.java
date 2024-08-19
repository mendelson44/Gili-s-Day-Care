package com.example.gilis_day_care;

import android.app.Application;

import com.example.gilis_day_care.Activities.FullScreenManager;
import com.example.gilis_day_care.Utilities.MyDbUserManager;
import com.example.gilis_day_care.model.Manager;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FullScreenManager.init(this);
        MyDbUserManager.init(this);
        Manager.init(this);

    }
}