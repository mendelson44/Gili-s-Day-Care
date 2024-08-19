package com.example.gilis_day_care.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import com.example.gilis_day_care.Interface.LoginCallBack;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.Utilities.MyDbUserManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText login_INP_email;
    private TextInputEditText login_INP_password;
    private TextInputLayout login_LAY_email;
    private TextInputLayout login_LAY_password;
    private MaterialButton login_BTN_login;
    private MaterialTextView login_LBL_create_account;

    private LinearLayoutCompat login_LL_main;
    private CardView login_CARD_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        findViews();

        login_LBL_create_account.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        });

        login_BTN_login.setOnClickListener(v -> {
            login();
        });

        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    // Navigation bar is visible
                    FullScreenManager.getInstance().fullScreen(getWindow());
                }
            }
        });


    }



    public void login() {

        if (!TextUtils.isEmpty(login_INP_email.getText()) && !TextUtils.isEmpty(login_INP_password.getText())) {

            login_LL_main.setAlpha(0.5f);
            login_CARD_loading.setVisibility(View.VISIBLE);
           // FullScreenManager.getInstance().hideKeyboard(this);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            MyDbUserManager.getInstance().loginUser(login_INP_email.getText().toString(), login_INP_password.getText().toString(), this, new LoginCallBack() {
                public void onLoginSuccess(String uid) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

                @Override
                public void onLoginFailure(Exception exception) {
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    login_LL_main.setAlpha(1);
                    login_CARD_loading.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // FullScreenManager.getInstance().fullScreen(getWindow());
    }

    public void findViews(){
        login_INP_email = findViewById(R.id.login_INP_email);
        login_INP_password = findViewById(R.id.login_INP_password);

        login_LAY_email = findViewById(R.id.login_LAY_email);
        login_LAY_password = findViewById(R.id.login_LAY_password);

        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_LBL_create_account = findViewById(R.id.login_LBL_create_account);

        login_LL_main = findViewById(R.id.login_LL_main);
        login_CARD_loading = findViewById(R.id.login_CARD_loading);
    }


}