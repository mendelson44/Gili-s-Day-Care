package com.example.gilis_day_care.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gilis_day_care.Fragments.HomeFragment;
import com.example.gilis_day_care.Fragments.Kid;
import com.example.gilis_day_care.Fragments.KidsFragment;
import com.example.gilis_day_care.Fragments.ManagementFragment;
import com.example.gilis_day_care.databinding.ActivityMainBinding;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.model.Manager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ArrayList<Kid> kidsList = new ArrayList<>();
    private ArrayList<Kid> workDayList = new ArrayList<>();
    private FloatingActionButton DayCare_main_BTN_addKid;
    private Manager manager;
    private MaterialTextView DayCare_main_LBL_dayOfWeek;
    private int dayOfWeek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);


        Manager manager = new Manager();
        manager.loadKidsListFireBase(this, new Manager.KidsListCallback() {
            @Override
            public void onSuccess(ArrayList<Kid> loadedKidsList) {
                // Assign the loaded data to the MainActivity's kidsList field
                MainActivity.this.kidsList = loadedKidsList;
                Log.d("MainActivity", "kids list." + MainActivity.this.kidsList);
                UpdateWorkList(dayOfWeek);
                Log.d("MainActivity", "work day kids list." + MainActivity.this.workDayList);


                // Initialize RecyclerView with the kidsList here if needed
            }

            @Override
            public void onFailure(Exception exception) {
                // Handle error if needed
            }
        });

        findViews();
        this.dayOfWeek = getDayOfWeek();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNavigationView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            boolean toLeft = false;
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.Main_frame_layout);

            if (item.getItemId() == R.id.home) {
                selectedFragment = new HomeFragment(workDayList);
                toLeft = false;
            }
            else if (item.getItemId() == R.id.kids) {
                selectedFragment = new KidsFragment(kidsList);
                toLeft = true;
            }
            else if (item.getItemId() == R.id.management) {
                selectedFragment = new ManagementFragment();
                toLeft = true;
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment, true,toLeft);
            }
            return true;
        });


        DayCare_main_BTN_addKid.setOnClickListener(view -> addKidActivity());
    }

    private void findViews() {

        DayCare_main_BTN_addKid = findViewById(R.id.DayCare_main_BTN_addKid);
        DayCare_main_LBL_dayOfWeek = findViewById(R.id.DayCare_main_LBL_dayOfWeek);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreenManager.getInstance().fullScreen(getWindow());
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, boolean toLeft) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Set custom animations
        if (toLeft) {
            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_left,  // Enter animation
                    R.anim.slide_out_right  // Exit animation
            );
        } else {
            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_right,  // Enter animation
                    R.anim.slide_out_left  // Exit animation
            );
        }

        fragmentTransaction.replace(R.id.Main_frame_layout, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }


    private void addKidActivity(){

        Intent intent = new Intent(this, AddKid.class);
        startActivity(intent);
        finish();
    }

    private int getDayOfWeek() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        // Adjust day to match DayOfWeek enum
        int adjustedDay = (day == Calendar.SUNDAY) ? 7 : day - 1;

        // Log the adjusted day of the week
        Log.d("MainActivity", "Day Of Week: " + DayOfWeek.of(adjustedDay));
        updateDate_UI(day);

        return day;
    }

    private void updateDate_UI(int day) {
        switch (day) {
            case Calendar.SUNDAY:
                DayCare_main_LBL_dayOfWeek.setText("יום ראשון");
                break;
            case Calendar.MONDAY:
                DayCare_main_LBL_dayOfWeek.setText("יום שני");
                break;
            case Calendar.TUESDAY:
                DayCare_main_LBL_dayOfWeek.setText("יום שלישי");
                break;
            case Calendar.WEDNESDAY:
                DayCare_main_LBL_dayOfWeek.setText("יום רביעי");
                break;
            case Calendar.THURSDAY:
                DayCare_main_LBL_dayOfWeek.setText("יום חמישי");
                break;
            case Calendar.FRIDAY:
                DayCare_main_LBL_dayOfWeek.setText("יום שישי");
                break;
            case Calendar.SATURDAY:
                DayCare_main_LBL_dayOfWeek.setText("יום שבת");
                break;
            default:
                // Handle unexpected value (though it should never happen)
                break;
        }
    }

    public void UpdateWorkList(int day) {

        for (Kid kid: kidsList) {
            //if (kid.getDays().contains(day))    // TODO: JUST FOR TEST
                workDayList.add(kid);
        }
    }


}

