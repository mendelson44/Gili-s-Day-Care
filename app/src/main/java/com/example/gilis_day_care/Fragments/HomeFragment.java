package com.example.gilis_day_care.Fragments;

import android.os.Bundle;

import android.animation.ObjectAnimator;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;


import java.util.ArrayList;

import com.example.gilis_day_care.R;
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
    private MaterialButton DayCare_home_BTN_food;
    private LinearLayoutCompat DayCare_presence_LAY;
    private TableLayout DayCare_foodTable;

    private MaterialTextView DayCare_home_progressBar_LBL_countPresentKids;
    private ProgressBar DayCare_home_progressBar_presence;
    private MaterialTextView DayCare_home_progressBar_LBL_countFinishedFood;
    private ProgressBar DayCare_home_progressBar_food;

    private MaterialTextView DayCare_foodTable_LBL_groupNumber;
    private TextView DayCare_foodTable_LBL_name1;
    private TextView DayCare_foodTable_LBL_ALR1;
    private TextView DayCare_foodTable_LBL_name2;
    private TextView DayCare_foodTable_LBL_ALR2;
    private TextView DayCare_foodTable_LBL_name3;
    private TextView DayCare_foodTable_LBL_ALR3;
    private TextView DayCare_foodTable_LBL_name4;
    private TextView DayCare_foodTable_LBL_ALR4;
    private TextView DayCare_foodTable_LBL_name5;
    private TextView DayCare_foodTable_LBL_ALR5;
    private TextView DayCare_foodTable_LBL_name6;
    private TextView DayCare_foodTable_LBL_ALR6;

    private FloatingActionButton DayCare_home_BTN_back;
    private FloatingActionButton DayCare_home_BTN_forward;
    private SwitchCompat DayCare_home_SWB_presence;


    private PresenceKidAdapter adapter;
    private RecyclerView DayCare_presence_RV_kidsPresenceList;
    private ArrayList<Kid> kidsList;
    private ArrayList<Kid> presentList;
    private ArrayList<Kid> foodList;
    private ArrayList<Kid> workFinalList;
    private Manager manager;
    private int countFoodGroups = 0;



    public HomeFragment (ArrayList<Kid> kidsList) {
       this.kidsList = kidsList;
       this.manager = new Manager();
       this.presentList = new ArrayList<>();
       this.foodList = new ArrayList<>();
        this.workFinalList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initHomeFragmentUI();
        initRecyclerView();  // Initialize RecyclerView

        // Simulate progress
        simulateProgress();


        // Set up click listener for slide button to presence layout
        DayCare_home_BTN_presence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Load slide animation and start it on the ImageView
                Animation animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.scale_and_fade);
                DayCare_home_CV_background.startAnimation(animation);

                if(DayCare_home_BTN_presence.isChecked())
                    DayCare_presence_LAY.setVisibility(View.VISIBLE);
                else
                    DayCare_presence_LAY.setVisibility(View.INVISIBLE);


                animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.scale_and_fade);
                DayCare_home_CV_background.startAnimation(animation);
            }

        });

        // Set up click listener for slide button food layout
        DayCare_home_BTN_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Load slide animation and start it on the ImageView
                Animation animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.scale_and_fade);
                DayCare_home_CV_background.startAnimation(animation);

                if(DayCare_home_BTN_food.isChecked())
                    DayCare_foodTable.setVisibility(View.VISIBLE);
                else
                    DayCare_foodTable.setVisibility(View.INVISIBLE);


                animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.scale_and_fade);
                DayCare_home_CV_background.startAnimation(animation);
            }

        });

        // Set up click listener for button lock presence
        DayCare_home_SWB_presence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(DayCare_home_SWB_presence.isChecked())
                    generateFoodList();

            }

        });

        DayCare_home_BTN_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(DayCare_home_SWB_presence.isChecked())
                    generateFoodList();

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
        DayCare_home_BTN_food = view.findViewById(R.id.DayCare_home_BTN_food);
        DayCare_presence_RV_kidsPresenceList = view.findViewById(R.id.DayCare_presence_RV_kidsPresenceList);
        DayCare_presence_LAY = view.findViewById(R.id.DayCare_presence_LAY);
        DayCare_foodTable = view.findViewById(R.id.DayCare_foodTable);

        DayCare_home_progressBar_LBL_countPresentKids = view.findViewById(R.id.DayCare_home_progressBar_LBL_countPresentKids);
        DayCare_home_progressBar_presence = view.findViewById(R.id.DayCare_home_progressBar_presence);
        DayCare_home_progressBar_LBL_countFinishedFood = view.findViewById(R.id.DayCare_home_progressBar_LBL_countFinishedFood);
        DayCare_home_progressBar_food = view.findViewById(R.id.DayCare_home_progressBar_food);

        DayCare_foodTable_LBL_groupNumber = view.findViewById(R.id.DayCare_foodTable_LBL_groupNumber);
        DayCare_foodTable_LBL_name1 = view.findViewById(R.id.DayCare_foodTable_LBL_name1);
        DayCare_foodTable_LBL_ALR1 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR1);
        DayCare_foodTable_LBL_name2 = view.findViewById(R.id.DayCare_foodTable_LBL_name2);
        DayCare_foodTable_LBL_ALR2 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR2);
        DayCare_foodTable_LBL_name3 = view.findViewById(R.id.DayCare_foodTable_LBL_name3);
        DayCare_foodTable_LBL_ALR3 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR3);
        DayCare_foodTable_LBL_name4 = view.findViewById(R.id.DayCare_foodTable_LBL_name4);
        DayCare_foodTable_LBL_ALR4 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR4);
        DayCare_foodTable_LBL_name5 = view.findViewById(R.id.DayCare_foodTable_LBL_name5);
        DayCare_foodTable_LBL_ALR5 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR5);
        DayCare_foodTable_LBL_name6 = view.findViewById(R.id.DayCare_foodTable_LBL_name6);
        DayCare_foodTable_LBL_ALR6 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR6);

        DayCare_home_BTN_back = view.findViewById(R.id.DayCare_home_BTN_back);
        DayCare_home_BTN_forward = view.findViewById(R.id.DayCare_home_BTN_forward);
        DayCare_home_SWB_presence = view.findViewById(R.id.DayCare_home_SWB_presence);

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
            UpdatePresentList();
            adapter.notifyItemChanged(position);
            makeSound();
            });
        Log.d("PresenceFragment", "RecyclerView initialized with adapter.");
    }

    private void UpdatePresentList() {
        for (Kid kid: kidsList) {
            if (kid.isPresent() && !(presentList.contains(kid)))
                presentList.add(kid);
            else if (!kid.isPresent() && presentList.contains(kid))
                presentList.remove(kid);

        }
        DayCare_home_progressBar_presence.setProgress(((presentList.size()*100)/kidsList.size()));
        DayCare_home_progressBar_LBL_countPresentKids.setText(String.valueOf(presentList.size()) + "/" + String.valueOf(kidsList.size()));
        Log.d("Presence progress", "Update Present List" + presentList);
    }

    private void generateFoodList() {

        this.countFoodGroups++;
        int counter = 0;
        if (!foodList.isEmpty()) {

            for (Kid kid: foodList) {
                if (!(workFinalList.contains(kid)))
                    workFinalList.add(kid);
            }

        }

        foodList.clear();
        for (Kid kid: presentList) {
            if (!(workFinalList.contains(kid))) {
                foodList.add(kid);
                counter++;
            }
            if (counter == 6)
                break;
        }
        updateFoodTable_UI(foodList);
        DayCare_home_progressBar_food.setProgress(((workFinalList.size()*100)/presentList.size()));
        DayCare_home_progressBar_LBL_countFinishedFood.setText(String.valueOf(workFinalList.size()) + "/" + String.valueOf(presentList.size()));
        DayCare_foodTable_LBL_groupNumber.setText(" קבוצה " + "-" + countFoodGroups);
        Log.d("food progress", "Update food List" + workFinalList);

    }

    private void updateFoodTable_UI(ArrayList<Kid> foodList) {

        int size = foodList.size();
        if(size >= 1 ) {
            DayCare_foodTable_LBL_name1.setText(foodList.get(0).getName());
            DayCare_foodTable_LBL_ALR1.setText(foodList.get(0).getAllergies());
        }
        if(size >= 2 ) {
            DayCare_foodTable_LBL_name2.setText(foodList.get(1).getName());
            DayCare_foodTable_LBL_ALR2.setText(foodList.get(1).getAllergies());
        }
        if(size >= 3 ) {
            DayCare_foodTable_LBL_name3.setText(foodList.get(2).getName());
            DayCare_foodTable_LBL_ALR3.setText(foodList.get(2).getAllergies());
        }
        if(size >= 4 ) {
            DayCare_foodTable_LBL_name4.setText(foodList.get(3).getName());
            DayCare_foodTable_LBL_ALR4.setText(foodList.get(3).getAllergies());
        }
        if(size >= 5 ) {
            DayCare_foodTable_LBL_name5.setText(foodList.get(4).getName());
            DayCare_foodTable_LBL_ALR5.setText(foodList.get(4).getAllergies());
        }
        if(size >= 6 ) {
            DayCare_foodTable_LBL_name6.setText(foodList.get(5).getName());
            DayCare_foodTable_LBL_ALR6.setText(foodList.get(5).getAllergies());
        }
    }

    private void makeSound() {
    }

    private void simulateProgress() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                    // Update UI
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // Update the percentage text

                        }
                    });

                    try {
                        Thread.sleep(1000); // Simulate time delay
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }

}
