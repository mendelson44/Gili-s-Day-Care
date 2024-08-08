package com.example.gilis_day_care.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gilis_day_care.Fragments.Kid;
import com.example.gilis_day_care.Interface.KidListCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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


}
