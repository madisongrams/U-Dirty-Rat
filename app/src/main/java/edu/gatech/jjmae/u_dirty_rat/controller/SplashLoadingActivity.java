package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import edu.gatech.jjmae.u_dirty_rat.MyApp;
import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;

public class SplashLoadingActivity extends AppCompatActivity {

    private final int DISPLAY_TIME = 7000;
//    private MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AnimationDrawable) getWindow().getDecorView().getBackground()).start();
        setContentView(R.layout.activity_splash_loading);
//        app = (MyApp) getApplication();
//
//        Log.i("main", "onCreate fired");
//        File file = new File(getApplicationContext().getFilesDir(), "userData.txt");
//        app.loadUserText(file);
//
//        File file2 = new File(getApplicationContext().getFilesDir(), "ratData.txt");
//        app.loadRatDataText(file2);
//
//        if (SampleModel.INSTANCE.getItems().size() < 100000) { //when app is used for first time, add csv file to model and save the file
//            Log.d("MyApp", "onCreate: saving from csv file");
//            app.readCSVFile();
////            File file3 = new File(this.getFilesDir(), "ratData.txt");
//            SampleModel.INSTANCE.saveText(file2);
//        }
//        Intent intent = new Intent(SplashLoadingActivity.this, WelcomeActivity.class);
//        SplashLoadingActivity.this.startActivity(intent);
//        SplashLoadingActivity.this.finish();

        Thread timer = new Thread() {

            public void run() {
                try {
                    sleep(DISPLAY_TIME);

                } catch(InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    Intent intent = new Intent(SplashLoadingActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashLoadingActivity.this, WelcomeActivity.class);
//                SplashLoadingActivity.this.startActivity(intent);
//                SplashLoadingActivity.this.finish();
//            }
//        }, DISPLAY_TIME);
//    }

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



//        GifDrawable gifDrawable = null;
//        try {
//            gifDrawable = new GifDrawable( getAssets(), "splash_logo_anim.gif" );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (gifDrawable != null) {
//            gifDrawable.addAnimationListener(new AnimationListener() {
//                @Override
//                public void onAnimationCompleted(int loopNumber) {
//                    Log.d("splashscreen","Gif animation completed");
//                    if (can_be_finished && nextIntent != null){
//                        startActivity(nextIntent);
//                        finish();
//                    }else {
//                        can_be_finished = true;
//                    }
//                }
//            });
//        }
//        logoView.setImageDrawable(gifDrawable);
//
//        setContentView(R.layout.activity_splash_loading);
//        Intent intent = new Intent(this, SplashLoadingActivity.class);
//        startActivity(intent);
//        finish();
//    }
//}
