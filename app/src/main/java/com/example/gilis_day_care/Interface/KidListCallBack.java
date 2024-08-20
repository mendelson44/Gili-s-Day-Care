package com.example.gilis_day_care.Interface;

import com.example.gilis_day_care.Model.Kid;

import java.util.ArrayList;

public interface KidListCallBack {

    void onLoadSucceeded(ArrayList<Kid> kids);
    void onLoadFailed(Exception exception);

}
