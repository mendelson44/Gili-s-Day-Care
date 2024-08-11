package com.example.gilis_day_care.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Fragments.HomeFragment;
import com.example.gilis_day_care.adapters.EventAdapter;
import com.example.gilis_day_care.adapters.KidAdapter;
import com.example.gilis_day_care.adapters.PresenceKidAdapter;
import com.example.gilis_day_care.model.Event;
import com.example.gilis_day_care.model.Kid;
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
    private FloatingActionButton DayCare_main_BTN_addEvent;
    private Manager manager;
    private MaterialTextView DayCare_main_LBL_dayOfWeek;
    private MaterialTextView DayCare_main_LBL_title;
    private int dayOfWeek;

    private EventAdapter adapter;
    private RecyclerView DayCare_notificationCard_RV_EventsList;
    private ArrayList<Event> eventsList;
    private RecyclerView DayCare_main_kids_RV;
    private LinearLayoutCompat DayCare_main_kidsRV_LAY;
    private CardView DayCare_main_kidsRV_CARD;
    private final Object lock = new Object();

    private Handler handler = new Handler(Looper.getMainLooper());


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
                MainActivity.this.loadKidsRecycleView(loadedKidsList);
                Log.d("MainActivity", "kids list." + MainActivity.this.kidsList);
                UpdateWorkList(dayOfWeek);
                Log.d("MainActivity", "work day kids list." + MainActivity.this.workDayList);

            }

            @Override
            public void onFailure(Exception exception) {
                // Handle error if needed
            }
        });

        manager.loadEventsListFireBase(this, new Manager.EventsListCallback() {
            @Override
            public void onSuccess(ArrayList<Event> loadedEventList) {
                // Assign the loaded data to the MainActivity's eventList field
                MainActivity.this.eventsList = loadedEventList;
                MainActivity.this.initRecyclerView();
                Log.d("MainActivity", "events list." + MainActivity.this.eventsList);
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
                DayCare_main_LBL_title.setText("דף הבית");
                //DayCare_main_kidsRV_CARD.setVisibility(View.GONE);
                DayCare_main_kidsRV_LAY.setVisibility(View.INVISIBLE);
                toLeft = false;
            }
            else if (item.getItemId() == R.id.kids) {
                selectedFragment = new KidsFragment();
                DayCare_main_LBL_title.setText("רשימת ילדים");
                DayCare_main_kidsRV_CARD.setVisibility(View.VISIBLE);
                DayCare_main_kidsRV_LAY.setVisibility(View.VISIBLE);
                if(currentFragment instanceof HomeFragment)
                    toLeft = true;
                else
                    toLeft = false;
            }
            else if (item.getItemId() == R.id.management) {
                selectedFragment = new ManagementFragment();
                DayCare_main_LBL_title.setText("הנהלה");
                //DayCare_main_kidsRV_CARD.setVisibility(View.GONE);
                DayCare_main_kidsRV_LAY.setVisibility(View.INVISIBLE);
                toLeft = true;
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment, currentFragment, true, toLeft);
            }

            return true;
        });


        DayCare_main_BTN_addKid.setOnClickListener(view -> addKidActivity());
        DayCare_main_BTN_addEvent.setOnClickListener(view -> addEventActivity());
    }

    private void findViews() {

        DayCare_main_LBL_title = findViewById(R.id.DayCare_main_LBL_title);
        DayCare_main_BTN_addKid = findViewById(R.id.DayCare_main_BTN_addKid);
        DayCare_main_BTN_addEvent = findViewById(R.id.DayCare_main_BTN_addEvent);
        DayCare_main_LBL_dayOfWeek = findViewById(R.id.DayCare_main_LBL_dayOfWeek);
        DayCare_notificationCard_RV_EventsList = findViewById(R.id.DayCare_notificationCard_RV_EventsList);
        DayCare_main_kids_RV = findViewById(R.id.DayCare_main_kids_RV);
        DayCare_main_kidsRV_LAY = findViewById(R.id.DayCare_main_kidsRV_LAY);
        DayCare_main_kidsRV_CARD = findViewById(R.id.DayCare_main_kidsRV_CARD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreenManager.getInstance().fullScreen(getWindow());
    }

    private void replaceFragment(Fragment fragment,Fragment currentFragment, boolean addToBackStack, boolean toLeft) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_and_fade);

        if (fragment instanceof KidsFragment && currentFragment instanceof KidsFragment) {
            animation = AnimationUtils.loadAnimation(this, R.anim.scale_and_fade);
        } else {  // Set custom animations

            if (toLeft) {
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_left,  // Enter animation
                        R.anim.slide_out_right  // Exit animation
                );
                if (fragment instanceof KidsFragment)
                    animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_lay);

                else if (currentFragment instanceof KidsFragment)
                    animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right_lay);

            } else {
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_right,  // Enter animation
                        R.anim.slide_out_left  // Exit animation
                );
                if (fragment instanceof KidsFragment)
                    animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right_lay);

                else if (currentFragment instanceof KidsFragment)
                    animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left_lay);
            }

        }

        if (fragment instanceof KidsFragment || currentFragment instanceof KidsFragment) {

            DayCare_main_kidsRV_LAY.startAnimation(animation);

            // Set an AnimationListener to check when the animation ends
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // This is called when the animation starts
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    // Perform the action after the animation ends
                    if (currentFragment instanceof KidsFragment && !(fragment instanceof KidsFragment))
                        DayCare_main_kidsRV_CARD.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // This is called if the animation repeats
                }
            });
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

    private void addEventActivity(){

        Intent intent = new Intent(this, AddEvent.class);
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


    private void initRecyclerView() {
        adapter = new EventAdapter(this, eventsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_notificationCard_RV_EventsList.setLayoutManager(linearLayoutManager);
        DayCare_notificationCard_RV_EventsList.setAdapter(adapter);

        Log.d("mainActivity", "RecyclerView EVENTS initialized with adapter.");
    }

    private void loadKidsRecycleView(ArrayList<Kid> kidsList) {

        // Initialize the RecyclerView
        KidAdapter adapterKid = new KidAdapter(this, kidsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_main_kids_RV.setLayoutManager(linearLayoutManager);
        DayCare_main_kids_RV.setAdapter(adapterKid);

        Log.d("mainActivity", "RecyclerView Kids initialized with adapter.");
    }



}

