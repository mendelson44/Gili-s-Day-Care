package com.example.gilis_day_care.Activities;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gilis_day_care.model.Kid;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.Utilities.MyFireBase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddKid extends AppCompatActivity {

    private MaterialTextView DayCare_addKid_MTV_date;
    private TextInputEditText DayCare_addKid_INP_name;
    private TextInputEditText DayCare_addKid_INP_class;
    private TextInputEditText DayCare_addKid_INP_email;
    private TextInputEditText DayCare_addKid_INP_address;
    private TextInputEditText DayCare_addKid_INP_parent1;
    private TextInputEditText DayCare_addKid_INP_parent2;
    private TextInputEditText DayCare_addKid_INP_phone1;
    private TextInputEditText DayCare_addKid_INP_phone2;
    private TextInputEditText DayCare_addKid_INP_SOS;
    private TextInputEditText DayCare_addKid_INP_allergies;


    private TextInputLayout DayCare_addKid_LAY_name;
    private TextInputLayout DayCare_addKid_LAY_class;
    private TextInputLayout DayCare_addKid_LAY_email;
    private TextInputLayout DayCare_addKid_LAY_address;
    private TextInputLayout DayCare_addKid_LAY_parent1;
    private TextInputLayout DayCare_addKid_LAY_parent2;
    private TextInputLayout DayCare_addKid_LAY_phone1;
    private TextInputLayout DayCare_addKid_LAY_phone2;
    private TextInputLayout DayCare_addKid_LAY_SOS;
    private TextInputLayout DayCare_addKid_LAY_allergies;

    private MaterialButton DayCare_addKid_BTN_add;

    private MyFireBase database;
    private MaterialButtonToggleGroup DayCare_kidData_TBG_daysGroup;
    private FloatingActionButton DayCare_addKid_BTN_btnSelectDate;
    private FloatingActionButton DayCare_addKid_BTN_exit;
    private MaterialTextView DayCare_addKid_LBL_txtSelectedDate;

    private MaterialButton DayCare_addKid_BTN_boy;
    private MaterialButton DayCare_addKid_BTN_girl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_kid);
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

        DayCare_addKid_BTN_btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        DayCare_addKid_BTN_exit.setOnClickListener(v -> exitToMainActivity());

        DayCare_addKid_BTN_add.setOnClickListener(v -> {

            if (validateFields()) {

                String name = DayCare_addKid_INP_name.getText().toString();
                String kidClass = DayCare_addKid_INP_class.getText().toString();
                String birthYear = DayCare_addKid_LBL_txtSelectedDate.getText().toString();
                String email = DayCare_addKid_INP_email.getText().toString();
                String address = DayCare_addKid_INP_address.getText().toString();
                String parent1 = DayCare_addKid_INP_parent1.getText().toString();
                String parent2 = DayCare_addKid_INP_parent2.getText().toString();
                String phone1 = DayCare_addKid_INP_phone1.getText().toString();
                String phone2 = DayCare_addKid_INP_phone2.getText().toString();
                String sos = DayCare_addKid_INP_SOS.getText().toString();
                String allergies = DayCare_addKid_INP_allergies.getText().toString();
                List<Integer> days = getDaysArray(DayCare_kidData_TBG_daysGroup.getCheckedButtonIds());
                boolean isGirl = DayCare_addKid_BTN_girl.isChecked();

                Log.d("addKid", "create kid days array: " + days);

                Kid kid = new Kid(name,kidClass,birthYear,email,address,parent1,parent2,phone1,phone2,sos,allergies,days,isGirl);

                database.saveKid(kid);

                Toast.makeText(this, "Successful kid creation : " + kid.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Integer> getDaysArray(List<Integer> array) {
        List<Integer> daysArray = new ArrayList<>(); // Assuming we are dealing with Sunday to Thursday

        for (Integer integer : array) {

            if (integer.equals(R.id.DayCare_kidData_BTN_day1)) {
                daysArray.add(1); // Sunday
            }

            if (integer.equals(R.id.DayCare_kidData_BTN_day2)) {
                daysArray.add(2);
            }

            if (integer.equals(R.id.DayCare_kidData_BTN_day3)) {
                daysArray.add(3);
            }

            if (integer.equals(R.id.DayCare_kidData_BTN_day4)) {
                daysArray.add(4);
            }

            if (integer.equals(R.id.DayCare_kidData_BTN_day5)) {
                daysArray.add(5);
            }
        }
        return daysArray;
    }

    public void resetLayout() {
        DayCare_addKid_LAY_name.setError(null);
        DayCare_addKid_LAY_class.setError(null);
        DayCare_addKid_LAY_email.setError(null);
        DayCare_addKid_LAY_address.setError(null);
        DayCare_addKid_LAY_parent1.setError(null);
        DayCare_addKid_LAY_parent2.setError(null);
        DayCare_addKid_LAY_phone1.setError(null);
        DayCare_addKid_LAY_phone2.setError(null);
        DayCare_addKid_LAY_SOS.setError(null);
    }


    private void findViews() {

        DayCare_addKid_MTV_date = findViewById(R.id.DayCare_addKid_MTV_date);
        DayCare_addKid_INP_name = findViewById(R.id.DayCare_addKid_INP_name);
        DayCare_addKid_INP_class = findViewById(R.id.DayCare_addKid_INP_class);
        DayCare_addKid_INP_email = findViewById(R.id.DayCare_addKid_INP_email);
        DayCare_addKid_INP_address = findViewById(R.id.DayCare_addKid_INP_address);
        DayCare_addKid_INP_parent1 = findViewById(R.id.DayCare_addKid_INP_parent1);
        DayCare_addKid_INP_parent2 = findViewById(R.id.DayCare_addKid_INP_parent2);
        DayCare_addKid_INP_phone1 = findViewById(R.id.DayCare_addKid_INP_phone1);
        DayCare_addKid_INP_phone2 = findViewById(R.id.DayCare_addKid_INP_phone2);
        DayCare_addKid_INP_SOS = findViewById(R.id.DayCare_addKid_INP_SOS);
        DayCare_addKid_INP_allergies = findViewById(R.id.DayCare_addKid_INP_allergies);

        DayCare_addKid_LAY_name = findViewById(R.id.DayCare_addKid_LAY_name);
        DayCare_addKid_LAY_class = findViewById(R.id.DayCare_addKid_LAY_class);
        DayCare_addKid_LAY_email = findViewById(R.id.DayCare_addKid_LAY_email);
        DayCare_addKid_LAY_address = findViewById(R.id.DayCare_addKid_LAY_address);
        DayCare_addKid_LAY_parent1 = findViewById(R.id.DayCare_addKid_LAY_parent1);
        DayCare_addKid_LAY_parent2 = findViewById(R.id.DayCare_addKid_LAY_parent2);
        DayCare_addKid_LAY_phone1 = findViewById(R.id.DayCare_addKid_LAY_phone1);
        DayCare_addKid_LAY_phone2 = findViewById(R.id.DayCare_addKid_LAY_phone2);
        DayCare_addKid_LAY_SOS = findViewById(R.id.DayCare_addKid_LAY_SOS);
        DayCare_addKid_LAY_allergies = findViewById(R.id.DayCare_addKid_LAY_allergies);
        DayCare_kidData_TBG_daysGroup = findViewById(R.id.DayCare_kidData_TBG_daysGroup);


        DayCare_addKid_BTN_add = findViewById(R.id.DayCare_addKid_BTN_add);
        DayCare_addKid_BTN_btnSelectDate = findViewById(R.id.DayCare_addKid_BTN_btnSelectDate);
        DayCare_addKid_BTN_exit = findViewById(R.id.DayCare_addKid_BTN_exit);
        DayCare_addKid_LBL_txtSelectedDate = findViewById(R.id.DayCare_addKid_LBL_txtSelectedDate);

        DayCare_addKid_BTN_boy = findViewById(R.id.DayCare_addKid_BTN_boy);
        DayCare_addKid_BTN_girl = findViewById(R.id.DayCare_addKid_BTN_girl);
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Validate name
        if (TextUtils.isEmpty(DayCare_addKid_INP_name.getText())) {
            DayCare_addKid_LAY_name.setError("First name is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_name.setError(null);
        }

        // Validate kid class
        if (TextUtils.isEmpty(DayCare_addKid_INP_class.getText())) {
            DayCare_addKid_LAY_class.setError("Kid's class is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_class.setError(null);
        }

        // Validate kid birthYear
        if (TextUtils.isEmpty(DayCare_addKid_LBL_txtSelectedDate.getText())) {
            DayCare_addKid_LBL_txtSelectedDate.setError("BirthYear is required");
            isValid = false;
        } else {
            DayCare_addKid_LBL_txtSelectedDate.setError(null);
        }

        // Validate email
        if (TextUtils.isEmpty(DayCare_addKid_INP_email.getText())) {
            DayCare_addKid_LAY_email.setError("Email is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_email.setError(null);        }

        // Validate address
        if (TextUtils.isEmpty(DayCare_addKid_INP_address.getText())) {
            DayCare_addKid_LAY_address.setError("Address is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_address.setError(null);
        }

        // Validate parent 1
        if (TextUtils.isEmpty(DayCare_addKid_INP_parent1.getText())) {
            DayCare_addKid_LAY_parent1.setError("Parent name is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_parent1.setError(null);
        }

        // Validate phone parent 1
        if (TextUtils.isEmpty(DayCare_addKid_INP_phone1.getText())) {
            DayCare_addKid_LAY_phone1.setError("Phone number is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_phone1.setError(null);
        }

        // Validate parent 2
        if (TextUtils.isEmpty(DayCare_addKid_INP_parent2.getText())) {
            DayCare_addKid_LAY_parent2.setError("Parent name is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_parent2.setError(null);
        }

        // Validate phone parent 2
        if (TextUtils.isEmpty(DayCare_addKid_INP_phone2.getText())) {
            DayCare_addKid_LAY_phone2.setError("Phone number is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_phone2.setError(null);
        }

        // Validate SOS phone
        if (TextUtils.isEmpty(DayCare_addKid_INP_SOS.getText())) {
            DayCare_addKid_LAY_SOS.setError("SOS phone is required");
            isValid = false;
        } else {
            DayCare_addKid_LAY_SOS.setError(null);
        }

        return isValid;
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
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    DayCare_addKid_LBL_txtSelectedDate.setText(selectedDate);
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
