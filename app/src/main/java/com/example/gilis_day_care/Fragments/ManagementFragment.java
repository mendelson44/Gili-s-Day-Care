package com.example.gilis_day_care.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gilis_day_care.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Locale;


public class ManagementFragment extends Fragment {



    public static ManagementFragment newInstance(String param1, String param2) {
        ManagementFragment fragment = new ManagementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_management, container, false);
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
