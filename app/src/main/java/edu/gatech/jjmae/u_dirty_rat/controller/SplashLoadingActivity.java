package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;

public class SplashLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable( getAssets(), "splash_logo_anim.gif" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gifDrawable != null) {
            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    Log.d("splashscreen","Gif animation completed");
                    if (can_be_finished && nextIntent != null){
                        startActivity(nextIntent);
                        finish();
                    }else {
                        can_be_finished = true;
                    }
                }
            });
        }
        logoView.setImageDrawable(gifDrawable);
//
//        setContentView(R.layout.activity_splash_loading);
        Intent intent = new Intent(this, SplashLoadingActivity.class);
        startActivity(intent);
        finish();
    }
}
