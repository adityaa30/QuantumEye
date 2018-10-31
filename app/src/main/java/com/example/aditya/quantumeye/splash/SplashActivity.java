package com.example.aditya.quantumeye.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.aditya.quantumeye.cloud_messaging.MainActivity;
import com.example.aditya.quantumeye.R;
import com.example.aditya.quantumeye.login.LoginActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView mAtomImage;
    private Handler mHandler = new Handler();

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        final String password = mPreferences.getString("password", "");

        mAtomImage = (ImageView) findViewById(R.id.splash_atom);

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.setDuration(1500L);
        fadeIn.setFillAfter(true);

        mAtomImage.startAnimation(fadeIn);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (password.isEmpty()){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1515L);


    }
}
