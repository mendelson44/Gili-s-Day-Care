package com.example.gilis_day_care.model;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.gilis_day_care.Activities.MainActivity;
import com.example.gilis_day_care.Interface.EventListCallBack;
import com.example.gilis_day_care.Interface.KidListCallBack;
import com.example.gilis_day_care.Utilities.MyFireBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager {

    private ArrayList<Kid> kidsList;
    private ArrayList<Event> eventsList;
    private ArrayList<Kid> presentList;
    private Map<Integer,ArrayList<Kid>> foodTableList;
    private ArrayList<Kid> workFinalList;
    private MyFireBase database;



    public Manager() {

        this.database = new MyFireBase();
        this.kidsList = new ArrayList<>();
        this.eventsList = new ArrayList<>();
        this.presentList = new ArrayList<>();
        this.workFinalList = new ArrayList<>();
        this.foodTableList = new HashMap<>();
    }

    public ArrayList<Kid> getKidsList() {
        return kidsList;
    }

    public ArrayList<Kid> getPresentList() {
        return presentList;
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
                Log.e("Manager", "Error loading kids list", exception);
                callback.onFailure(exception);  // Notify the caller that the loading failed
            }
        });
    }

    public void loadEventsListFireBase(Context context, EventsListCallback callback) {

        // Load kids list and initialize the RecyclerView when data is loaded
        database.loadEventsList(new EventListCallBack() {
        @Override
        public void onLoadSucceeded(ArrayList<Event> events) {
            eventsList = events;
            Log.d("Manager", "Events list loaded successfully. ");
            callback.onSuccess(eventsList);  // Notify the caller that the data is loaded

        }
        @Override
        public void onLoadFailed(Exception exception) {
            // Handle the failure scenario
            Toast.makeText(context, "Failed to load events list: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Manager", "Error loading events list", exception);
            callback.onFailure(exception);  // Notify the caller that the loading failed
        }
    });
    }

    // Callback interface for handling the data once it's loaded
    public interface KidsListCallback {
        void onSuccess(ArrayList<Kid> kidsList);
        void onFailure(Exception exception);
    }

    public interface EventsListCallback {
        void onSuccess(ArrayList<Event> eventsList);
        void onFailure(Exception exception);
    }
}
