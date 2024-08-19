package com.example.gilis_day_care.Interface;

public interface LoginCallBack {

    void onLoginSuccess(String uid);
    void onLoginFailure(Exception exception);

}