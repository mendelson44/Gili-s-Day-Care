package com.example.gilis_day_care.model;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gilis_day_care.Fragments.Kid;
import com.example.gilis_day_care.Interface.KidListCallBack;
import com.example.gilis_day_care.Utilities.MyFireBase;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import java.util.concurrent.CountDownLatch;

public class Manager {

    private ArrayList<Kid> kidsList = new ArrayList<>();
    private MyFireBase database;

    public Manager() {
        this.database = new MyFireBase();
    }

    public void loadKidsListFireBase(Context context, KidsListCallback callback) {

        // Load kids list and initialize the RecyclerView when data is loaded
        database.loadKidsList(new KidListCallBack() {
            @Override
            public void onLoadSucceeded(ArrayList<Kid> kids) {
                kidsList = kids;
                Log.d("Manager", "Kids list loaded successfully. Initializing RecyclerView." + kidsList);
                callback.onSuccess(kidsList);  // Notify the caller that the data is loaded
            }

            @Override
            public void onLoadFailed(Exception exception) {
                // Handle the failure scenario
                Toast.makeText(context, "Failed to load kids list: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PresenceFragment", "Error loading kids list", exception);
                callback.onFailure(exception);  // Notify the caller that the loading failed
            }
        });
    }

    // Callback interface for handling the data once it's loaded
    public interface KidsListCallback {
        void onSuccess(ArrayList<Kid> kidsList);
        void onFailure(Exception exception);
    }
}
