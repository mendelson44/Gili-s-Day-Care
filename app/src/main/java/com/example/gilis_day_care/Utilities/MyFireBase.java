package com.example.gilis_day_care.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gilis_day_care.Model.Event;
import com.example.gilis_day_care.Model.Kid;
import com.example.gilis_day_care.Interface.KidListCallBack;
import com.example.gilis_day_care.Interface.EventListCallBack;
import com.example.gilis_day_care.Model.User;
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
        database.getReference("Kids").child(kid.getId()).setValue(kid);

    }

    public void saveEvent (Event event) {
        Log.d("saveEvent-FireBase", "save event: " + event.toString());
        database.getReference("Events").child(event.getId()).setValue(event);

    }

    public void saveUser (User user) {
        Log.d("saveUser-FireBase", "save user: " + user.toString());
        database.getReference("Users").child(user.getUid()).setValue(user);

    }

    public void deleteEvent (String eventId) {

        Log.d("delete-Event-FireBase", "delete event: " + eventId);
        database.getReference("Events").child(eventId).removeValue();
    }
    public void deleteKid (String kidId) {

        Log.d("delete-Kid-FireBase", "delete kid: " + kidId);
        database.getReference("Kids").child(kidId).removeValue();
    }


    public void loadKidsList(KidListCallBack callBack) {
        Query eventsQuery = database.getReference().child("Kids");
        eventsQuery.addValueEventListener(new ValueEventListener() {
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
        eventsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Event> eventsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        eventsList.add(event);
                    } else {
                        Log.e("FirebaseDataManager", "Failed to load event from Firebase.");
                    }
                }
                if (!eventsList.isEmpty()) {
                    callBack.onLoadSucceeded(eventsList);
                } else {
                    callBack.onLoadFailed(new Exception("No events found."));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDataManager", "Error loading events: " + error.getMessage());
                callBack.onLoadFailed(error.toException());
            }
        });
    }


    public void onDataChangedEventsList(EventListCallBack callBack) {
        Query eventsQuery = database.getReference().child("Events");
        eventsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Event> eventsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        eventsList.add(event);
                    } else {
                        Log.e("FirebaseDataManager", "Failed to load event from Firebase.");
                    }
                }
                if (!eventsList.isEmpty()) {
                    callBack.onLoadSucceeded(eventsList);
                } else {
                    callBack.onLoadFailed(new Exception("No events found."));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDataManager", "Error loading events: " + error.getMessage());
                callBack.onLoadFailed(error.toException());
            }
        });
    }

}
