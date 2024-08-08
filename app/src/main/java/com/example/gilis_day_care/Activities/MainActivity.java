package com.example.gilis_day_care.Activities;

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
import com.example.gilis_day_care.Fragments.ManagementFragment;
import com.example.gilis_day_care.databinding.ActivityMainBinding;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.model.Manager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ArrayList<Kid> kidsList = new ArrayList<>();
    private FloatingActionButton DayCare_main_BTN_addKid;
    private Manager manager;

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

                // Initialize RecyclerView with the kidsList here if needed
            }

            @Override
            public void onFailure(Exception exception) {
                // Handle error if needed
            }
        });

        findViews();
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
                selectedFragment = new HomeFragment(kidsList);
                toLeft = true;
            }
            else if (item.getItemId() == R.id.management) {
                selectedFragment = new ManagementFragment();
                toLeft = false;
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
                    R.anim.slide_out_right,  // Exit animation
                    R.anim.slide_in_left,  // Pop enter animation (optional)
                    R.anim.slide_out_right   // Pop exit animation (optional)
            );
        } else {
            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_right,  // Enter animation
                    R.anim.slide_out_left,  // Exit animation
                    R.anim.slide_in_right,  // Pop enter animation (optional)
                    R.anim.slide_out_left   // Pop exit animation (optional)
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
}

