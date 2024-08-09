package com.example.gilis_day_care.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.R;
import com.example.gilis_day_care.adapters.KidAdapter;
import com.example.gilis_day_care.adapters.PresenceKidAdapter;
import com.example.gilis_day_care.model.Manager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class KidsFragment extends Fragment {

    private KidAdapter adapter;
    private RecyclerView DayCare_kids_RV_kidsList;
    private ArrayList<Kid> kidsList;
    private Manager manager;



    public KidsFragment(ArrayList<Kid> kidsList) {
       this.kidsList = kidsList;
       this.manager = new Manager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kids, container, false);
        findViews(view);
        initRecyclerView();  // Initialize RecyclerView

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void findViews(View view) {

        DayCare_kids_RV_kidsList = view.findViewById(R.id.DayCare_kids_RV_kidsList);
    }

    private void initRecyclerView() {
        adapter = new KidAdapter(getActivity().getApplicationContext(), kidsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_kids_RV_kidsList.setLayoutManager(linearLayoutManager);
        DayCare_kids_RV_kidsList.setAdapter(adapter);
        Log.d("PresenceFragment", "RecyclerView initialized with adapter.");
    }


}
