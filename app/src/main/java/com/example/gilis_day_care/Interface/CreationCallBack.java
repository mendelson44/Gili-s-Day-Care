package com.example.gilis_day_care.Interface;

public interface CreationCallBack {
    void onCreated(String uid);
    void onCreationFailed(Exception exception);

}