package com.example.gilis_day_care.Activities;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gilis_day_care.R;
import com.example.gilis_day_care.Utilities.MyFireBase;
import com.example.gilis_day_care.model.Event;
import com.example.gilis_day_care.model.Kid;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddEvent extends AppCompatActivity {

    private TextInputEditText DayCare_addEvent_INP_name;
    private TextInputEditText DayCare_addEvent_INP_text;
    private TextInputLayout DayCare_addEvent_LAY_name;
    private TextInputLayout DayCare_addEvent_LAY_text;
    private MaterialButton DayCare_addEvent_BTN_add;
    private FloatingActionButton DayCare_addEvent_BTN_exit;
    private FloatingActionButton DayCare_addEvent_BTN_btnSelectTime;
    private MaterialTextView DayCare_addEvent_LBL_txtSelectedTime;
    private FloatingActionButton DayCare_addEvent_BTN_btnSelectDate;
    private MaterialTextView DayCare_addEvent_LBL_txtSelectedDate;

    private MyFireBase database;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);
        findViews();
        setListeners();
        this.database = new MyFireBase();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setListeners() {

        DayCare_addEvent_BTN_btnSelectTime.setOnClickListener(v -> showTimePickerDialog());
        DayCare_addEvent_BTN_btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        DayCare_addEvent_BTN_exit.setOnClickListener(v -> exitToMainActivity());

        DayCare_addEvent_BTN_add.setOnClickListener(v -> {

            if (validateFields()) {

                String name = DayCare_addEvent_INP_name.getText().toString();
                String text = DayCare_addEvent_INP_text.getText().toString();
                String time = DayCare_addEvent_LBL_txtSelectedTime.getText().toString();
                String date = DayCare_addEvent_LBL_txtSelectedDate.getText().toString();

                Event event = new Event(name,text,time,date);

                database.saveEvent(event);
                Toast.makeText(this, "Successful event creation : " + event.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void resetLayout() {
        DayCare_addEvent_LAY_name.setError(null);
        DayCare_addEvent_LAY_text.setError(null);
    }


    private void findViews() {

        DayCare_addEvent_INP_name = findViewById(R.id.DayCare_addEvent_INP_name);
        DayCare_addEvent_INP_text = findViewById(R.id.DayCare_addEvent_INP_text);
        DayCare_addEvent_LAY_name = findViewById(R.id.DayCare_addEvent_LAY_name);
        DayCare_addEvent_LAY_text = findViewById(R.id.DayCare_addEvent_LAY_text);
        DayCare_addEvent_BTN_add = findViewById(R.id.DayCare_addEvent_BTN_add);
        DayCare_addEvent_BTN_btnSelectTime = findViewById(R.id.DayCare_addEvent_BTN_btnSelectTime);
        DayCare_addEvent_LBL_txtSelectedTime = findViewById(R.id.DayCare_addEvent_LBL_txtSelectedTime);
        DayCare_addEvent_BTN_btnSelectDate = findViewById(R.id.DayCare_addEvent_BTN_btnSelectDate);
        DayCare_addEvent_LBL_txtSelectedDate = findViewById(R.id.DayCare_addEvent_LBL_txtSelectedDate);
        DayCare_addEvent_BTN_exit = findViewById(R.id.DayCare_addEvent_BTN_exit);
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Validate name
        if (TextUtils.isEmpty(DayCare_addEvent_INP_name.getText())) {
            DayCare_addEvent_LAY_name.setError("name is required");
            isValid = false;
        } else {
            DayCare_addEvent_LAY_name.setError(null);
        }

        // Validate event text
        if (TextUtils.isEmpty(DayCare_addEvent_INP_text.getText())) {
            DayCare_addEvent_LAY_text.setError("TEXT is required");
            isValid = false;
        } else {
            DayCare_addEvent_LAY_text.setError(null);
        }

        return isValid;
    }

    private void showTimePickerDialog() {
        // Get the current time as default
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    String selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                    DayCare_addEvent_LBL_txtSelectedTime.setText(selectedTime);
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    // Display the selected date in the TextView
                    String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month1 + 1, year1);
                    DayCare_addEvent_LBL_txtSelectedDate.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void exitToMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
