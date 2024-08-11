package com.example.gilis_day_care.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.example.gilis_day_care.R;
import com.example.gilis_day_care.adapters.PresenceKidAdapter;
import com.example.gilis_day_care.model.Kid;
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


    private PresenceKidAdapter adapter;
    private RecyclerView DayCare_presence_RV_kidsPresenceList;
    private ArrayList<Kid> kidsList;
    private ArrayList<Kid> presentList;
    private Map<Integer,ArrayList<Kid>> foodTableList;
    private ArrayList<Kid> workFinalList;
    private Manager manager;
    private int countFoodGroups = 0;


    public HomeFragment (ArrayList<Kid> kidsList) {
       this.kidsList = kidsList;
       this.manager = new Manager();
       this.presentList = new ArrayList<>();
       this.foodTableList = new HashMap<>();
       this.workFinalList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initHomeFragmentUI();
        initRecyclerView();  // Initialize RecyclerView
        generateFoodList();

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

                if(DayCare_home_BTN_presence.isChecked()) {
                    DayCare_presence_LAY.setVisibility(View.VISIBLE);
                }
                else {
                    DayCare_presence_LAY.setVisibility(View.INVISIBLE);
                }

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

                if(DayCare_home_BTN_food.isChecked()) {
                    DayCare_foodTable.setVisibility(View.VISIBLE);
                }
                else {
                    DayCare_foodTable.setVisibility(View.INVISIBLE);
                }



                animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.scale_and_fade);
                DayCare_home_CV_background.startAnimation(animation);
            }

        });

        DayCare_home_BTN_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countFoodGroups++;
                if (foodTableList.get(countFoodGroups) != null)
                    updateFoodTable_UI(foodTableList.get(countFoodGroups));
                else
                    countFoodGroups--;

            }

        });

        DayCare_home_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countFoodGroups--;
                if (foodTableList.get(countFoodGroups) != null)
                    updateFoodTable_UI(foodTableList.get(countFoodGroups));
                else
                    countFoodGroups++;

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

        Stack<Kid> tempStack = new Stack<>();
        if (kidsList.isEmpty())
            return ;

        for (int i = 0; i < kidsList.size(); i++) {
            tempStack.push(kidsList.get(i));
        }

        int counter = 1;
        while (!tempStack.isEmpty()) {
            ArrayList<Kid> foodList = new ArrayList<>();

            for (int i = 0; i < 6; i++) {
                if (!tempStack.isEmpty())
                    foodList.add(i, tempStack.pop());

            }
            foodTableList.put(counter, foodList);
            counter++;

        }

        Log.d("FOOD table progress", "create Map Food table " + foodTableList);
    }

    private void updateFoodTable_UI(ArrayList<Kid> foodList) {

        DayCare_home_progressBar_food.setProgress((((countFoodGroups*6)*100)/kidsList.size()));
        DayCare_home_progressBar_LBL_countFinishedFood.setText(String.valueOf((countFoodGroups*6) + "/" + String.valueOf(kidsList.size())));
        DayCare_foodTable_LBL_groupNumber.setText(" קבוצה " + "-" + countFoodGroups);
        Log.d("food progress", "Update food List" + workFinalList);

        DayCare_foodTable_LBL_name1.setText("----");
        DayCare_foodTable_LBL_ALR1.setText("----");
        DayCare_foodTable_LBL_name2.setText("----");
        DayCare_foodTable_LBL_ALR2.setText("----");
        DayCare_foodTable_LBL_name3.setText("----");
        DayCare_foodTable_LBL_ALR3.setText("----");
        DayCare_foodTable_LBL_name4.setText("----");
        DayCare_foodTable_LBL_ALR4.setText("----");
        DayCare_foodTable_LBL_name5.setText("----");
        DayCare_foodTable_LBL_ALR5.setText("----");
        DayCare_foodTable_LBL_name6.setText("----");
        DayCare_foodTable_LBL_ALR6.setText("----");


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
