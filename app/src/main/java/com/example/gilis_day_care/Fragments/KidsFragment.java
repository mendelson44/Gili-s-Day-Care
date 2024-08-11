package com.example.gilis_day_care.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Activities.MainActivity;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.adapters.KidAdapter;
import com.example.gilis_day_care.model.Kid;
import com.example.gilis_day_care.model.Manager;

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
        // Get the RecyclerView and adapter from the MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            adapter = mainActivity.getAdapter();
        }
        initRecyclerView();

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_kids_RV_kidsList.setLayoutManager(linearLayoutManager);
        DayCare_kids_RV_kidsList.setAdapter(adapter);
        Log.d("PresenceFragment", "RecyclerView initialized with adapter.");
    }


}
