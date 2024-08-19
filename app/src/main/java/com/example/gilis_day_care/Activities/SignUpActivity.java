package com.example.gilis_day_care.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.gilis_day_care.Interface.CreationCallBack;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.Utilities.MyDbUserManager;
import com.example.gilis_day_care.Utilities.MyFireBase;
import com.example.gilis_day_care.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText register_INP_email;
    private TextInputEditText register_INP_password;
    private TextInputLayout register_LAY_email;
    private TextInputLayout register_LAY_password;
    private RelativeLayout register_REL_main;
    private CardView register_CARD_loading;
    private MaterialButton btnRegister;
    private MyFireBase fireBase;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);
        this.fireBase = new MyFireBase();
        findViews();
        setListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreenManager.getInstance().fullScreen(getWindow());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setListeners() {

        btnRegister.setOnClickListener(v -> {
            if (validateFields()) {
                FullScreenManager.getInstance().hideKeyboard(this);
                register_REL_main.setAlpha(0.5f);
                register_REL_main.setEnabled(false);
                register_CARD_loading.setVisibility(View.VISIBLE);

                // Set the screen to not touch
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                String email = Objects.requireNonNull(register_INP_email.getText()).toString();
                String password = Objects.requireNonNull(register_INP_password.getText()).toString();
                Log.d("Register", "Register"+email+password);
                MyDbUserManager.getInstance().createNewUser(email, password, this, new CreationCallBack() {
                    @Override
                    public void onCreated(String uid) {
                        String userType;
                        User user = new User(uid,email);
                        fireBase.saveUser(user);
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                    }

                    @Override
                    public void onCreationFailed(Exception exception) {
                        Toast.makeText(SignUpActivity.this,  exception.getMessage(), Toast.LENGTH_SHORT).show();
                        register_REL_main.setAlpha(1);
                        register_REL_main.setEnabled(true);
                        register_CARD_loading.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                });
            }
        });
    }


    public void resetLayout() {
        register_LAY_email.setError(null);
        register_LAY_password.setError(null);
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Validate email
        if (TextUtils.isEmpty(register_INP_email.getText())) {
            register_LAY_email.setError("Email is required");
            isValid = false;
        } else {
            register_LAY_email.setError(null);
        }

        // Validate password
        if (TextUtils.isEmpty(register_INP_password.getText())) {
            register_LAY_password.setError("Password is required");
            isValid = false;
        } else {
            register_LAY_password.setError(null);
        }

        return isValid;
    }

    public static String fixName(String name) {

        String firstLetter = name.substring(0, 1).toUpperCase();
        String remainingLetters = name.substring(1).toLowerCase();

        return firstLetter + remainingLetters;
    }

    private void findViews() {

        register_INP_email = findViewById(R.id.register_INP_email);
        register_INP_password = findViewById(R.id.register_INP_password);
        register_LAY_email = findViewById(R.id.register_LAY_email);
        register_LAY_password = findViewById(R.id.register_LAY_password);
        register_REL_main = findViewById(R.id.register_REL_main);
        register_CARD_loading = findViewById(R.id.register_CARD_loading);
        btnRegister = findViewById(R.id.register_BTN_register);
    }
}