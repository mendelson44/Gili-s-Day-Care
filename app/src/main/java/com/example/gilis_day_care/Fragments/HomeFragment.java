package com.example.gilis_day_care.Fragments;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.animation.ObjectAnimator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.gilis_day_care.Interface.KidListCallBack;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.Utilities.MyFireBase;
import com.example.gilis_day_care.adapters.PresenceKidAdapter;
import com.example.gilis_day_care.model.Manager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;


public class HomeFragment extends Fragment {

    private CardView DayCare_home_CV_background;
    private MaterialButtonToggleGroup DayCare_home_TBG_newDayWork;
    private MaterialButton DayCare_home_BTN_presence;
    private LinearLayoutCompat DayCare_presence_LAY;

    private PresenceKidAdapter adapter;
    private RecyclerView DayCare_presence_RV_kidsPresenceList;
    private ArrayList<Kid> kidsList;
    private Manager manager;



    public HomeFragment (ArrayList<Kid> kidsList) {
       this.kidsList = kidsList;
       this.manager = new Manager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initHomeFragmentUI();
        initRecyclerView();  // Initialize RecyclerView


        // Set up click listener for slide button to start slide animation
        DayCare_home_BTN_presence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Load slide animation and start it on the ImageView
                Animation animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.slide_out_left);
                DayCare_home_CV_background.startAnimation(animation);

                if(DayCare_home_BTN_presence.isChecked())
                    DayCare_presence_LAY.setVisibility(View.VISIBLE);
                else
                    DayCare_presence_LAY.setVisibility(View.INVISIBLE);


                animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.slide_in_right);
                DayCare_home_CV_background.startAnimation(animation);
            }

        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initCurrentUser();
        initHomeFragmentUI();

    }


    private void initCurrentUser() {
    }

    private void findViews(View view) {

        DayCare_home_CV_background = view.findViewById(R.id.DayCare_home_CV_background);
        DayCare_home_TBG_newDayWork = view.findViewById(R.id.DayCare_home_TBG_newDayWork);
        DayCare_home_BTN_presence = view.findViewById(R.id.DayCare_home_BTN_presence);
        DayCare_presence_RV_kidsPresenceList = view.findViewById(R.id.DayCare_presence_RV_kidsPresenceList);
        DayCare_presence_LAY = view.findViewById(R.id.DayCare_presence_LAY);
    }



    private void initHomeFragmentUI() {

    }

    private void initRecyclerView() {
        adapter = new PresenceKidAdapter(getActivity().getApplicationContext(), kidsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_presence_RV_kidsPresenceList.setLayoutManager(linearLayoutManager);
        DayCare_presence_RV_kidsPresenceList.setAdapter(adapter);
        adapter.setKidCallback((kid, position) -> {
            kid.setPresent(!kid.isPresent());
            adapter.notifyItemChanged(position);
            makeSound();
            });
        Log.d("PresenceFragment", "RecyclerView initialized with adapter.");
    }

    private void makeSound() {
    }


    private void animateCardView() {
        ArrayList<ObjectAnimator> animators = new ArrayList<>();

        // Create an ObjectAnimator for width
        ObjectAnimator widthAnimator = ObjectAnimator.ofInt(DayCare_home_CV_background, "width", DayCare_home_CV_background.getWidth(), DayCare_home_CV_background.getWidth() + 200);
        widthAnimator.setDuration(1000);
        widthAnimator.addUpdateListener(animation -> {
            int newValue = (Integer) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = DayCare_home_CV_background.getLayoutParams();
            layoutParams.width = newValue;
            DayCare_home_CV_background.setLayoutParams(layoutParams);
        });

        // Create an ObjectAnimator for height
        ObjectAnimator heightAnimator = ObjectAnimator.ofInt(DayCare_home_CV_background, "height", DayCare_home_CV_background.getHeight(), DayCare_home_CV_background.getHeight() + 200);
        heightAnimator.setDuration(1000);
        heightAnimator.addUpdateListener(animation -> {
            int newValue = (Integer) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = DayCare_home_CV_background.getLayoutParams();
            layoutParams.height = newValue;
            DayCare_home_CV_background.setLayoutParams(layoutParams);
        });

        animators.add(widthAnimator);
        animators.add(heightAnimator);

        animators.forEach(ObjectAnimator::start);
    }

}
