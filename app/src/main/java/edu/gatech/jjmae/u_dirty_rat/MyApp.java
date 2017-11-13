package edu.gatech.jjmae.u_dirty_rat;

import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import edu.gatech.jjmae.u_dirty_rat.model.UserData;


/**
 * Created by Madison on 10/22/2017.
 * A custom Application class which is used on app start up to do some backend setup before app runs
 */
public class MyApp extends Application {
    /**
     * default constructor called when app first runs
     */
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
        loadUserText(file);

        File file2 = new File(getApplicationContext().getFilesDir(), "ratData.txt");
        loadRatDataText(file2);

        if (SampleModel.INSTANCE.getItems().size() < 100000) { //when app is used for first time, add csv file to model and save the file
            Log.d("MyApp", "onCreate: saving from csv file");
            readCSVFile();
//            File file3 = new File(this.getFilesDir(), "ratData.txt");
            SampleModel.INSTANCE.saveText(file2);
        }

    }

    /**
     * used to load a text file saved on the phone
     * @param file file being loaded
     *
     */
    public void loadUserText(File file) {
        try {
            //make an input object for reading
            BufferedReader reader = new BufferedReader(new FileReader(file));
            UserData.loadFromText(reader);
        } catch (FileNotFoundException e) {
            Log.e("MyApp", "Failed to open user text file for loading!");
        }

    }

    /**
     * used to load a text file saved on the phone
     * @param file file being loaded
     * @return whether or not file loading was successful
     */
    public void loadRatDataText(File file) {
        try {
            //make an input object for reading
            BufferedReader reader = new BufferedReader(new FileReader(file));
            SampleModel.INSTANCE.loadFromText(reader);
        } catch (FileNotFoundException e) {
            Log.e("MyApp", "Failed to open rat data text file for loading!");
        }

    }
    /**
     * method that reads in the csv file
     * reads in entire csv file and records data into a SampleModel
     */
    private void readCSVFile() {
        SampleModel model = SampleModel.INSTANCE;

        try {
            InputStream is = getResources().openRawResource(R.raw.rat_sightings);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                //Log.d(HomeActivity.TAG, line);
                String[] tokens = line.split(",");
                int id = 0;
                int zip = 0;
                try {
                    id = Integer.parseInt(tokens[0]);
                } catch (Exception e) {
                    id = 0;
                }
                try {
                    zip = Integer.parseInt(tokens[8]);
                } catch (Exception e) {
                    zip = 0;
                }
                double latitude =  0.0;
                double longitude = 0.0;
                try {
                    latitude = Double.parseDouble(tokens[49]);
                    longitude = Double.parseDouble(tokens[50]);
                } catch (IndexOutOfBoundsException e) {
                    latitude = 0.0;
                    longitude = 0.0;

                }
                Date entryDate = new Date(1969, 12, 31);
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    entryDate = df.parse(tokens[1]);
                } catch (Exception e) {

                }

                model.addItem(new RatSightingDataItem(id, entryDate, tokens[7], zip, tokens[9], tokens[16], tokens[23], latitude, longitude));
            }
            br.close();
        } catch (IOException e) {
            Log.e("MyApp", "error reading assets", e);
        }

    }


}
