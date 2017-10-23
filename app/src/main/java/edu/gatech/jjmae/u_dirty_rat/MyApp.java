package edu.gatech.jjmae.u_dirty_rat;

import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.gatech.jjmae.u_dirty_rat.model.UserData;

/**
 * Created by Madison on 10/22/2017.
 */

public class MyApp extends Application {
    public MyApp() {
        // this method fires only once per application start.
        // getApplicationContext returns null here

        Log.i("main", "Constructor fired");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // this method fires once as well as constructor
        // but also application has context here

        Log.i("main", "onCreate fired");
        File file = new File(getApplicationContext().getFilesDir(), "userData.txt");
        loadText(file);
    }

    public boolean loadText(File file) {
        try {
            //make an input object for reading
            BufferedReader reader = new BufferedReader(new FileReader(file));
            UserData.loadFromText(reader);

        } catch (FileNotFoundException e) {
            Log.e("MyApp", "Failed to open text file for loading!");
            return false;
        }

        return true;
    }


}
