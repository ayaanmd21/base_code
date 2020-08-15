package com.dehaat.dehaatassignment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.dehaat.dehaatassignment.databinding.ActivityLoginBinding;
import com.dehaat.dehaatassignment.databinding.ActivitySplashBinding;
import com.dehaat.dehaatassignment.utils.SharedPrefUtils;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding splashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = splashBinding.getRoot();
        setContentView(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mIntent;
                if (!TextUtils.isEmpty(SharedPrefUtils.getStringData(getApplicationContext(), SharedPrefUtils.TOKEN))) {
                    mIntent = new Intent(getApplicationContext(), MainActivity.class);
                } else {
                    mIntent = new Intent(getApplicationContext(), LoginActivity.class);

                }
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                finish();

            }
        }, 2000);

    }
}