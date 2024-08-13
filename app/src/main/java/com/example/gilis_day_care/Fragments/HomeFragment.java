package com.example.gilis_day_care.Fragments;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;


public class HomeFragment extends Fragment {

    private CardView DayCare_home_CV_background;
    private MaterialButton DayCare_home_BTN_presence;
    private MaterialButton DayCare_home_BTN_food;
    private LinearLayoutCompat DayCare_presence_LAY;
    private TableLayout DayCare_foodTable;
    private TableLayout DayCare_KidsTable;

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

    private ShapeableImageView DayCare_foodTable_IMG_check1;
    private ShapeableImageView DayCare_foodTable_IMG_check2;
    private ShapeableImageView DayCare_foodTable_IMG_check3;
    private ShapeableImageView DayCare_foodTable_IMG_check4;
    private ShapeableImageView DayCare_foodTable_IMG_check5;
    private ShapeableImageView DayCare_foodTable_IMG_check6;

    private FloatingActionButton DayCare_home_BTN_back;
    private FloatingActionButton DayCare_home_BTN_forward;


    private PresenceKidAdapter adapter;
    private RecyclerView DayCare_presence_RV_kidsPresenceList;
    private ArrayList<Kid> kidsList;
    private Map<Integer,ArrayList<Kid>> foodTableList;
    private Manager manager;
    private int countFoodGroups;
    private SparseArray<ShapeableImageView> ShapeableImageViewMap;


    private static volatile HomeFragment instance;


    public static HomeFragment init(ArrayList<Kid> kidsList){
        if (instance == null){
            synchronized (HomeFragment.class){
                if (instance == null){
                    instance = new HomeFragment(kidsList);
                }
            }
        }
        return getInstance();
    }

    public static HomeFragment getInstance() {
        return instance;
    }


    public HomeFragment (ArrayList<Kid> kidsList) {
       this.kidsList = kidsList;
       this.manager = new Manager();
       this.foodTableList = new HashMap<>();
       this.countFoodGroups = 0;
       generateFoodList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initRecyclerView();  // Initialize RecyclerView
        UpdatePresentList();

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
                    DayCare_foodTable.setVisibility(View.INVISIBLE);
                    DayCare_KidsTable.setVisibility(View.GONE);
                }
                else {
                    DayCare_presence_LAY.setVisibility(View.INVISIBLE);
                    DayCare_KidsTable.setVisibility(View.VISIBLE);
                }

                animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.scale_and_fade);
                DayCare_home_CV_background.startAnimation(animation);


                UpdatePresentList();
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
                    DayCare_presence_LAY.setVisibility(View.INVISIBLE);
                    DayCare_KidsTable.setVisibility(View.GONE);
                }
                else {
                    DayCare_foodTable.setVisibility(View.INVISIBLE);
                    DayCare_KidsTable.setVisibility(View.VISIBLE);
                }

                animation = AnimationUtils.loadAnimation(v.getContext()
                        , R.anim.scale_and_fade);
                DayCare_home_CV_background.startAnimation(animation);


                if (countFoodGroups == 0) {
                    countFoodGroups++;
                    updateFoodTable_UI(foodTableList.get(countFoodGroups));
                }
                else
                    updateFoodTable_UI(foodTableList.get(countFoodGroups));
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
        DayCare_foodTable_IMG_check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0 < foodTableList.get(countFoodGroups).size()) {
                    if (foodTableList.get(countFoodGroups).get(0).getState() != 0)
                        UpdateFoodTable_kidCheck_UI(0);
                }
            }
        });
        DayCare_foodTable_IMG_check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 < foodTableList.get(countFoodGroups).size()) {
                    if (foodTableList.get(countFoodGroups).get(1).getState() != 0)
                        UpdateFoodTable_kidCheck_UI(1);
                }
            }
        });
        DayCare_foodTable_IMG_check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (2 < foodTableList.get(countFoodGroups).size()) {
                    if (foodTableList.get(countFoodGroups).get(2).getState() != 0)
                        UpdateFoodTable_kidCheck_UI(2);
                }
            }
        });
        DayCare_foodTable_IMG_check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (3 < foodTableList.get(countFoodGroups).size()) {
                    if (foodTableList.get(countFoodGroups).get(3).getState() != 0)
                        UpdateFoodTable_kidCheck_UI(3);
                }
            }
        });
        DayCare_foodTable_IMG_check5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (4 < foodTableList.get(countFoodGroups).size()) {
                    if (foodTableList.get(countFoodGroups).get(4).getState() != 0)
                        UpdateFoodTable_kidCheck_UI(4);
                }
            }
        });
        DayCare_foodTable_IMG_check6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (5 < foodTableList.get(countFoodGroups).size()) {
                    if (foodTableList.get(countFoodGroups).get(5).getState() != 0)
                        UpdateFoodTable_kidCheck_UI(5);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void findViews(View view) {

        DayCare_home_CV_background = view.findViewById(R.id.DayCare_home_CV_background);
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
        DayCare_foodTable_IMG_check1 = view.findViewById(R.id.DayCare_foodTable_IMG_check1);
        DayCare_foodTable_LBL_name2 = view.findViewById(R.id.DayCare_foodTable_LBL_name2);
        DayCare_foodTable_LBL_ALR2 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR2);
        DayCare_foodTable_IMG_check2 = view.findViewById(R.id.DayCare_foodTable_IMG_check2);
        DayCare_foodTable_LBL_name3 = view.findViewById(R.id.DayCare_foodTable_LBL_name3);
        DayCare_foodTable_LBL_ALR3 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR3);
        DayCare_foodTable_IMG_check3 = view.findViewById(R.id.DayCare_foodTable_IMG_check3);
        DayCare_foodTable_LBL_name4 = view.findViewById(R.id.DayCare_foodTable_LBL_name4);
        DayCare_foodTable_LBL_ALR4 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR4);
        DayCare_foodTable_IMG_check4 = view.findViewById(R.id.DayCare_foodTable_IMG_check4);
        DayCare_foodTable_LBL_name5 = view.findViewById(R.id.DayCare_foodTable_LBL_name5);
        DayCare_foodTable_LBL_ALR5 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR5);
        DayCare_foodTable_IMG_check5 = view.findViewById(R.id.DayCare_foodTable_IMG_check5);
        DayCare_foodTable_LBL_name6 = view.findViewById(R.id.DayCare_foodTable_LBL_name6);
        DayCare_foodTable_LBL_ALR6 = view.findViewById(R.id.DayCare_foodTable_LBL_ALR6);
        DayCare_foodTable_IMG_check6 = view.findViewById(R.id.DayCare_foodTable_IMG_check6);

        DayCare_home_BTN_back = view.findViewById(R.id.DayCare_home_BTN_back);
        DayCare_home_BTN_forward = view.findViewById(R.id.DayCare_home_BTN_forward);


        ShapeableImageViewMap = new SparseArray<>();
        ShapeableImageViewMap.put(0, DayCare_foodTable_IMG_check1);
        ShapeableImageViewMap.put(1, DayCare_foodTable_IMG_check2);
        ShapeableImageViewMap.put(2, DayCare_foodTable_IMG_check3);
        ShapeableImageViewMap.put(3, DayCare_foodTable_IMG_check4);
        ShapeableImageViewMap.put(4, DayCare_foodTable_IMG_check5);
        ShapeableImageViewMap.put(5, DayCare_foodTable_IMG_check6);


        DayCare_KidsTable = view.findViewById(R.id.DayCare_KidsTable);

    }


    private void initRecyclerView() {
        adapter = new PresenceKidAdapter(getActivity().getApplicationContext(), kidsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_presence_RV_kidsPresenceList.setLayoutManager(linearLayoutManager);
        DayCare_presence_RV_kidsPresenceList.setAdapter(adapter);
        adapter.setKidCallback((kid, position) -> {
            kid.setState(kid.getState() == 0 ? 1 : 0);
            UpdatePresentList();
            adapter.notifyItemChanged(position);
            makeSound();
            });
        Log.d("PresenceFragment", "RecyclerView initialized with adapter.");
    }

    private void UpdatePresentList() {

        int count  = getCountOfKidsInByState(1);
        int kidsInState2  = getCountOfKidsInByState(2);
        int finalCount = count + kidsInState2;
        DayCare_home_progressBar_presence.setProgress((finalCount*100)/kidsList.size());
        DayCare_home_progressBar_LBL_countPresentKids.setText(finalCount + "/" + kidsList.size());
        Log.d("Presence progress", "Update Present List count : " + finalCount);
    }

    private int getCountOfKidsInByState(int state) {
        int count = 0 ;
        for (Kid kid:kidsList) {
            if (kid.getState() == state)
                count++;

        }
        return count;
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

        int kidsInState2  = getCountOfKidsInByState(2);
        int kidsInState1  = getCountOfKidsInByState(1);
        int allKidsAfterState1 = (kidsInState1 + kidsInState2);
        int progress = 0;
        if(allKidsAfterState1 != 0)
            progress = kidsInState2 * 100 / allKidsAfterState1;
        DayCare_home_progressBar_food.setProgress(progress);
        DayCare_home_progressBar_LBL_countFinishedFood.setText(kidsInState2 + "/" + allKidsAfterState1);
        DayCare_foodTable_LBL_groupNumber.setText(" קבוצה " + "-" + countFoodGroups);

        for (int i = 0; i < foodList.size(); i++) {
            if (foodList.get(i).getState() == 2)
                ShapeableImageViewMap.get(i).setImageResource(R.drawable.daycare_green_check);
            else if (foodList.get(i).getState() == 1)
                ShapeableImageViewMap.get(i).setImageResource(R.drawable.daycare_empty_check);
            else
                ShapeableImageViewMap.get(i).setImageResource(R.drawable.daycare_checkbox);
        }
        for (int i = foodList.size(); i < 6; i++) {
            ShapeableImageViewMap.get(i).setImageResource(R.drawable.daycare_checkbox);
        }

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

    private void UpdateFoodTable_kidCheck_UI(int row) {

        if (foodTableList != null && !foodTableList.isEmpty()) {

            if (foodTableList.get(countFoodGroups).get(row) != null) {
                Kid kid = foodTableList.get(countFoodGroups).get(row);
                Log.d("Food progress", "checked on kid :" + kid.toString());
                ShapeableImageView checkImageView = ShapeableImageViewMap.get(row);
                if (checkImageView != null) {
                    if (kid.getState() == 2) {
                        checkImageView.setImageResource(R.drawable.daycare_empty_check);
                        kid.setState(1);
                    } else {
                        checkImageView.setImageResource(R.drawable.daycare_green_check);
                        kid.setState(2);
                    }
                }
            } else {
                Log.e("Food progress", "check mark on a null kid");
            }
        } else {
            Log.e("Food progress", "Invalid foodTableList");
        }

        // Update progress
        if (kidsList != null && !kidsList.isEmpty()) {
            int kidsInState2  = getCountOfKidsInByState(2);
            int kidsInState1  = getCountOfKidsInByState(1);
            int allKidsAfterState1 = (kidsInState1 + kidsInState2);
            int progress = 0;
            if(allKidsAfterState1 != 0)
                progress = kidsInState2 * 100 / allKidsAfterState1;
            DayCare_home_progressBar_food.setProgress(progress);
            DayCare_home_progressBar_LBL_countFinishedFood.setText(kidsInState2 + "/" + allKidsAfterState1);
        } else {
            Log.e("Food progress", "kidsList is null or empty");
        }
    }



}
