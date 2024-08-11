package com.example.gilis_day_care.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gilis_day_care.model.Event;
import com.example.gilis_day_care.model.Kid;
import com.example.gilis_day_care.Interface.KidListCallBack;
import com.example.gilis_day_care.Interface.EventListCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFireBase {

    private FirebaseDatabase database;

    public MyFireBase() {
        this.database = FirebaseDatabase.getInstance();
        Log.d("firebase", "Creatde: " + database);
    }


    public void saveKid (Kid kid) {
        Log.d("kid", "save kid: " + kid.toString());
        database.getReference("Kids").child(kid.getPhone1()).setValue(kid);

    }

    public void saveEvent (Event event) {
        Log.d("saveEvent-FireBase", "save event: " + event.toString());
        database.getReference("Events").child(event.getTime()).setValue(event);

    }


    public void loadKidsList(KidListCallBack callBack) {
        Query eventsQuery = database.getReference().child("Kids");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Kid> kidsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Kid kid = snapshot.getValue(Kid.class);
                    if (kid != null) {
                        kidsList.add(kid);
                    } else {
                        Log.e("FirebaseDataManager", "Failed to load kids List from Firebase.");
                    }
                }
                if (!kidsList.isEmpty()) {
                    callBack.onLoadSucceeded(kidsList);
                } else {
                    callBack.onLoadFailed(new Exception("No events found for kids: "));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadEventsList(EventListCallBack callBack) {
        Query eventsQuery = database.getReference().child("Events");
        eventsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Event> eventsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        eventsList.add(event);
                    } else {
                        Log.e("FirebaseDataManager", "Failed to load events List from Firebase.");
                    }
                }
                if (!eventsList.isEmpty()) {
                    callBack.onLoadSucceeded(eventsList);
                } else {
                    callBack.onLoadFailed(new Exception("No events found for events: "));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
