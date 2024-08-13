package com.example.gilis_day_care.Activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.example.gilis_day_care.Utilities.MyFireBase;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private MaterialButton DayCare_main_BTN_START;
    private MaterialTextView DayCare_home_LBL_date;
    private RelativeLayout DayCare_main_LAY;
    private ArrayList<Kid> kidsList = new ArrayList<>();
    private ArrayList<Kid> workDayList = new ArrayList<>();
    private ArrayList<Event> workDayEventList = new ArrayList<>();
    private FloatingActionButton DayCare_main_BTN_addKid;
    private FloatingActionButton DayCare_main_BTN_addEvent;
    private MaterialTextView DayCare_main_LBL_dayOfWeek;
    private MaterialTextView DayCare_main_LBL_title;
    private int dayOfWeek;
    private String dateOfToday;

    private EventAdapter adapter;
    private KidAdapter kidAdapter;
    private RecyclerView DayCare_notificationCard_RV_EventsList;
    private ArrayList<Event> eventsList;
    private RecyclerView DayCare_main_kids_RV;
    private LinearLayoutCompat DayCare_main_kidsRV_LAY;
    private CardView DayCare_main_kidsRV_CARD;
    private ManagementFragment managementFragment;
    private Manager manager;
    private MyFireBase fireBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);


        this.manager = new Manager();
        this.fireBase = new MyFireBase();

        manager.loadKidsListFireBase(this, new Manager.KidsListCallback() {
            @Override
            public void onSuccess(ArrayList<Kid> loadedKidsList) {
                // Assign the loaded data to the MainActivity's kidsList field
                MainActivity.this.kidsList = loadedKidsList;
                MainActivity.this.initKidsRecycleView(loadedKidsList);
                Log.d("MainActivity", "kids list." + MainActivity.this.kidsList);
                UpdateWorkList(dayOfWeek);
                Log.d("MainActivity", "work day kids list." + MainActivity.this.workDayList);
                HomeFragment.init(workDayList);

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
                Log.d("MainActivity", "events list." + MainActivity.this.eventsList);
                UpdateEventList(dateOfToday);
                Log.d("MainActivity", "events day list." + MainActivity.this.workDayEventList);
                MainActivity.this.initRecyclerView();
                MainActivity.this.managementFragment = new ManagementFragment(eventsList);

                managementFragment.setOnItemRemovedCallback((event) -> {
                    int position = MainActivity.this.adapter.getItemPosition(event);
                    // Get the ViewHolder for the given position

                    if (position >= 0) {
                        Log.d("MainActivity", "event remove" + event.toString() + " , position : " +  position );
                        RecyclerView.ViewHolder viewHolder = DayCare_notificationCard_RV_EventsList.findViewHolderForAdapterPosition(position);

                        if (viewHolder != null) {
                            View view = viewHolder.itemView;

                            // Load and set the animation
                            Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.slide_right_delete);
                            animator.setTarget(view);
                            animator.start();

                            // Add a listener to remove the item after the animation ends
                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    // notify the item from the adapter's data source
                                    MainActivity.this.adapter.removeItem(position);
                                }
                            });
                        } else {
                            // Handle the case where the ViewHolder is null
                            //adapter.removeItem(position);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception exception) {
                // Handle error if needed
            }
        });

        findViews();
        this.dayOfWeek = getDayOfWeek();
        this.dateOfToday = getTodayDate();
        DayCare_home_LBL_date.setText(dateOfToday);
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
                selectedFragment = HomeFragment.getInstance();
                DayCare_main_LBL_title.setText("דף הבית");
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
                selectedFragment = managementFragment;
                DayCare_main_LBL_title.setText("הנהלה");
                DayCare_main_kidsRV_LAY.setVisibility(View.INVISIBLE);
                toLeft = true;
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment, currentFragment, true, toLeft);
            }

            return true;
        });


        DayCare_main_BTN_START.setOnClickListener(view -> startWorkDay());
        DayCare_main_BTN_addKid.setOnClickListener(view -> addKidActivity());
        DayCare_main_BTN_addEvent.setOnClickListener(view -> addEventActivity());
    }

    private void startWorkDay() {

        if(workDayList == null || workDayList.isEmpty()) {
            Toast.makeText(this, "NO KIDS TODAY: " , Toast.LENGTH_SHORT).show();
        }
        else {
            DayCare_main_LAY.setVisibility(View.VISIBLE);
            DayCare_main_BTN_START.setVisibility(View.GONE);
            replaceFragment(HomeFragment.getInstance(), new KidsFragment(), true, true);
        }
    }

    private void findViews() {

        DayCare_main_LAY = findViewById(R.id.DayCare_main_LAY);
        DayCare_main_BTN_START = findViewById(R.id.DayCare_main_BTN_START);
        DayCare_main_LBL_title = findViewById(R.id.DayCare_main_LBL_title);
        DayCare_main_BTN_addKid = findViewById(R.id.DayCare_main_BTN_addKid);
        DayCare_main_BTN_addEvent = findViewById(R.id.DayCare_main_BTN_addEvent);
        DayCare_main_LBL_dayOfWeek = findViewById(R.id.DayCare_main_LBL_dayOfWeek);
        DayCare_notificationCard_RV_EventsList = findViewById(R.id.DayCare_notificationCard_RV_EventsList);
        DayCare_main_kids_RV = findViewById(R.id.DayCare_main_kids_RV);
        DayCare_main_kidsRV_LAY = findViewById(R.id.DayCare_main_kidsRV_LAY);
        DayCare_main_kidsRV_CARD = findViewById(R.id.DayCare_main_kidsRV_CARD);
        DayCare_home_LBL_date = findViewById(R.id.DayCare_home_LBL_date);
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

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
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

        for (Kid kid : kidsList) {
            for (String dayTime : kid.getDays()) {
                // Split the day string by the delimiter
                String[] parts = dayTime.split("#");

                // Check if the first part of the split contains the day value
                if (parts.length > 0) {
                    String firstPart = parts[0];
                    if (firstPart.contains( "" + day ))    // TODO: JUST FOR TEST
                        workDayList.add(kid);
                }
            }
        }
    }

    public void UpdateEventList(String day) {

        for (Event event: eventsList) {
            if (event.getDate().equals(day))    // TODO: JUST FOR TEST
                workDayEventList.add(event);
        }
    }


    private void initRecyclerView() {

        adapter = new EventAdapter(this, workDayEventList, position -> {
            // Trigger view data on item click
            RecyclerView.ViewHolder viewHolder = DayCare_notificationCard_RV_EventsList.findViewHolderForAdapterPosition(position);
            if (viewHolder != null) {
                View view = viewHolder.itemView;

                // TODO: MAYBE IN THE FUTURE DO SOMETHING WHEN CLICK
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_notificationCard_RV_EventsList.setLayoutManager(linearLayoutManager);
        DayCare_notificationCard_RV_EventsList.setAdapter(adapter);
//        adapter.setEventDeleteCallBack((event, position) -> {
//            // Get the ViewHolder for the given position
//            RecyclerView.ViewHolder viewHolder = DayCare_notificationCard_RV_EventsList.findViewHolderForAdapterPosition(position);
//
//            if (viewHolder != null) {
//                View view = viewHolder.itemView;
//
//                // Load and set the animation
//                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.slide_right_delete);
//                animator.setTarget(view);
//                animator.start();
//
//                // Add a listener to remove the item after the animation ends
//                animator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        // Remove the item from the adapter's data source
//                        adapter.removeItem(position);
//                    }
//                });
//            } else {
//                // Handle the case where the ViewHolder is null
//                adapter.removeItem(position);
//            }
//        });

        Log.d("mainActivity", "RecyclerView EVENTS initialized with adapter.");
    }


    private void initKidsRecycleView(ArrayList<Kid> kidsList) {

        kidAdapter = new KidAdapter(this, kidsList, position -> {
            // Trigger swipe animation on item click
            RecyclerView.ViewHolder viewHolder = DayCare_main_kids_RV.findViewHolderForAdapterPosition(position);
            if (viewHolder != null) {
                View view = viewHolder.itemView;
                Animator animator;
                if(view.getTranslationX() == 0) {
                    animator = AnimatorInflater.loadAnimator(this, R.animator.slide_left);
                    view.findViewById(R.id.DayCare_kidData_IMG_delete).setVisibility(View.VISIBLE);
                }

                else {
                    animator = AnimatorInflater.loadAnimator(this, R.animator.slide_right);
                    view.findViewById(R.id.DayCare_kidData_IMG_delete).setVisibility(View.INVISIBLE);
                }
                animator.setTarget(view);  // Set the target view for the animation
                animator.start();  // Start the animation

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_main_kids_RV.setLayoutManager(linearLayoutManager);
        DayCare_main_kids_RV.setAdapter(kidAdapter);
        kidAdapter.setKidDeleteCallBack((kid, position) -> {
            // Get the ViewHolder for the given position
            RecyclerView.ViewHolder viewHolder = DayCare_main_kids_RV.findViewHolderForAdapterPosition(position);

            if (viewHolder != null) {
                View view = viewHolder.itemView;

                // Load and set the animation
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.slide_right_delete);
                animator.setTarget(view);
                animator.start();

                // Add a listener to remove the item after the animation ends
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Remove the item from the adapter's data source
                        Log.d("mainActivity", "kid remove" + kid.toString() + " , position : " +  position );
                        fireBase.deleteKid(kidsList.get(position).getId());
                        kidAdapter.removeItem(position);
                    }
                });
            } else {
                // Handle the case where the ViewHolder is null
                kidAdapter.removeItem(position);
            }
        });

        Log.d("mainActivity", "RecyclerView initialized with adapter.");
    }

}

