package com.example.gilis_day_care.model;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.gilis_day_care.Activities.MainActivity;
import com.example.gilis_day_care.Interface.EventListCallBack;
import com.example.gilis_day_care.Interface.KidListCallBack;
import com.example.gilis_day_care.Utilities.MyDbUserManager;
import com.example.gilis_day_care.Utilities.MyFireBase;
import com.google.firebase.auth.FirebaseAuth;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Manager {

    private ArrayList<Kid> kidsList;
    private ArrayList<Event> eventsList;
    private ArrayList<Event> workDayEventList;
    private Map<Integer,ArrayList<Kid>> foodTableList;
    private ArrayList<Kid> workDayKidsList;
    private MyFireBase database;
    private String dateOfToday;


    private static Context context;
    private static volatile Manager instance;

    private static final long CHECK_INTERVAL_MS = 60000; // 1 minute
    private Handler handler = new Handler();


    public static void init(Context context) {
        if (instance == null) {
            instance = new Manager(context);
        }
    }

    public static Manager getInstance() {
        return instance;
    }

    private Manager(Context context) {

        this.context = context;
        this.database = new MyFireBase();
        this.kidsList = new ArrayList<>();
        this.workDayKidsList = new ArrayList<>();
        this.eventsList = new ArrayList<>();
        this.workDayEventList = new ArrayList<>();
        this.foodTableList = new HashMap<>();

        this.dateOfToday = getTodayDate();

        loadKidsListFireBase(context);
        loadEventsListFireBase(context);

        startTimeChecks();

    }


    public ArrayList<Kid> getWorkDayKidsList() {
        return workDayKidsList;
    }

    public void setWorkDayKidsList(ArrayList<Kid> workDayKidsList) {
        this.workDayKidsList = workDayKidsList;
    }

    public void setKidsList(ArrayList<Kid> kidsList) {
        this.kidsList = kidsList;
    }

    public ArrayList<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<Event> eventsList) {
        this.eventsList = eventsList;
    }

    public ArrayList<Kid> getKidsList() {
        return kidsList;
    }

    public ArrayList<Event> getWorkDayEventList() {
        return workDayEventList;
    }

    public void loadKidsListFireBase(Context context) {

        // Load kids list and initialize the RecyclerView when data is loaded
        database.loadKidsList(new KidListCallBack() {
            @Override
            public void onLoadSucceeded(ArrayList<Kid> kids) {
                kidsList = kids;
                UpdateWorkList(getDayOfWeek());
                Log.d("Manager", "Kids list loaded successfully." + kidsList);
            }

            @Override
            public void onLoadFailed(Exception exception) {
                // Handle the failure scenario
                Toast.makeText(context, "Failed to load kids list: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Manager", "Error loading kids list", exception);
            }
        });
    }

    public void loadEventsListFireBase(Context context) {

        // Load kids list and initialize the RecyclerView when data is loaded
        database.loadEventsList(new EventListCallBack() {
        @Override
        public void onLoadSucceeded(ArrayList<Event> events) {
            eventsList = events;
            UpdateEventList(dateOfToday);
            Log.d("Manager", "Events list loaded successfully. ");
        }
        @Override
        public void onLoadFailed(Exception exception) {
            // Handle the failure scenario
            Toast.makeText(context, "Failed to load events list: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Manager", "Error loading events list", exception);
        }
    });
    }

    public void UpdateWorkList(int day) {
        workDayKidsList.clear();
        for (Kid kid : kidsList) {
            for (String dayTime : kid.getDays()) {
                String[] parts = dayTime.split("#");
                if (parts.length > 0 && parts[0].contains(String.valueOf(day))) {
                    workDayKidsList.add(kid);
                }
            }
        }
    }

    public void UpdateEventList(String day) {
        workDayEventList.clear();
        if (eventsList != null) {
            for (Event event : eventsList) {
                if (event.getDate().equals(day)) {
                    workDayEventList.add(event);
                }
            }
        }
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
    }
    private int getDayOfWeek() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        // Adjust day to match DayOfWeek enum
        int adjustedDay = (day == Calendar.SUNDAY) ? 7 : day - 1;

        return day;
    }

    public void saveEvent(Event event) {
        database.saveEvent(event);
    }




    private Runnable timeCheckRunnable = new Runnable() {
        @Override
        public void run() {
            checkForLateKids();
            handler.postDelayed(this, CHECK_INTERVAL_MS);
        }
    };

    private void checkForLateKids() {
        LocalTime currentTime = LocalTime.now();
        Duration lateWindow = Duration.ofMinutes(10); // 10 minutes window

        // Define a formatter for parsing time strings
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Kid kid : kidsList) {
            if(!kid.isLate() && kid.getState() == 0) {

                for (String dayTime : kid.getDays()) {
                    String[] parts = dayTime.split("#");

                    if (parts.length > 1) {
                        String time = parts[1].trim();
                        String day = parts[0];
                        if (day.equals(String.valueOf(getDayOfWeek()))) {
                            // Convert the time to a LocalTime object
                            LocalTime kidTime = LocalTime.parse(time, timeFormatter);

                            if (currentTime.isAfter(kidTime.plus(lateWindow))) {
                                // Kid is late beyond the acceptable window
                                Event event = createEventForLateKid(kid,String.format("%02d:%02d", currentTime.getHour(),currentTime.getMinute()));
                                kid.setLate(true);
                            }
                        }
                    }
                }

            }

        }

    }

    private Event createEventForLateKid(Kid kid,String currentTime) {
        Event event = new Event(kid.getName(),"לא הגיע לצהרון",currentTime,getTodayDate());
        saveEvent(event);

        Log.d("LateKidEvent", kid.getName() + " is late!");
        return event;
    }

    private void startTimeChecks() {
        handler.post(timeCheckRunnable);
    }

    private void stopTimeChecks() {
        handler.removeCallbacks(timeCheckRunnable);
    }

}
