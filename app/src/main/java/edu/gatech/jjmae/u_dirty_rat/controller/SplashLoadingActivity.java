package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import edu.gatech.jjmae.u_dirty_rat.R;

public class SplashLoadingActivity extends AppCompatActivity {

    private final int DISPLAY_TIME = 4200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashLoadingActivity.this, WelcomeActivity.class);
                SplashLoadingActivity.this.startActivity(intent);
                SplashLoadingActivity.this.finish();
            }
        }, DISPLAY_TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}