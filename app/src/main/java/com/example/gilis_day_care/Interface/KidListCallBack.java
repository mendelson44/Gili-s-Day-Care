package com.example.gilis_day_care.Interface;

import com.example.gilis_day_care.Fragments.Kid;

import java.util.ArrayList;
import java.util.List;

public interface KidListCallBack {

    void onLoadSucceeded(ArrayList<Kid> kids);
    void onLoadFailed(Exception exception);

}
