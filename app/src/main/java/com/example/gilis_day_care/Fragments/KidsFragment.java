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

    private ArrayList<Kid> kidsList;


    public KidsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kids, container, false);
        findViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void findViews(View view) {

    }


}
