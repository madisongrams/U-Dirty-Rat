package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import edu.gatech.jjmae.u_dirty_rat.model.UserData;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // When this button (logout) is hit, we should go back to welcome screen.
        // User should also be logged out
//        Button next1 = (Button) findViewById(R.id.button3);
//        next1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                UserData.setCurrentUser(null);
//                Intent myIntent = new Intent(view.getContext(), WelcomeActivity.class);
//                startActivityForResult(myIntent, 0);
//            }
//
//        });



        // When this button (add a rat entry) is hit, ...
        Button next2 = (Button) findViewById(R.id.button8);
        next2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NewRatSightingActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        // When this button (view rat entries) is hit, ...
        Button next3 = (Button) findViewById(R.id.button7);
        next3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onLoadButtonPressed(view);
            }

        });

        // When this button (select dates activity) is hit, ..
        Button next4 = (Button) findViewById(R.id.button6);
        next4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SelectDatesActivity.class);
                myIntent.putExtra("activity", "maps");
                startActivityForResult(myIntent, 0);
            }

        });

        //graphs
        Button next5 = (Button) findViewById(R.id.button3);
        next5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SelectDatesActivity.class);
                myIntent.putExtra("activity", "graphs");
                startActivityForResult(myIntent, 0);
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                UserData.setCurrentUser(null);
                Intent myIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivityForResult(myIntent, 0);
                return true;
            case R.id.action_profile:
                //go to profile page
                return true;
            case R.id.action_view_users:
                if (!UserData.getCurrentUser().getIsAdmin()) {
                    displayErrorMessage("Only admins can view this page!");
                    return true;
                }
                Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                startActivityForResult(intent, 0);
                // go to user list
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    /**
     * method for what happens when view rat data is pressed.
     * if it is the app's first time, the data is read in from csv file and only then
     *
     * @param view view where button is located
     */
    public void onLoadButtonPressed(View view) {
        Log.v(HomeActivity.TAG, "Pressed the load button");
        SampleModel model = SampleModel.INSTANCE;
        if (model.getItems().size() < 100000) {
            readSDFile();
        }
        Intent intent = new Intent(this, RatSightingsListActivity.class);
        startActivity(intent);
    }

    /**
     * method that reads in the csv file
     * reads in entire csv file and records data into a SampleModel
     */
    private void readSDFile() {
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
                }
                try {
                    zip = Integer.parseInt(tokens[8]);
                } catch (Exception e) {
                }
                double latitude =  0.0;
                double longitude = 0.0;
                try {
                    latitude = Double.parseDouble(tokens[49]);
                    longitude = Double.parseDouble(tokens[50]);
                } catch (IndexOutOfBoundsException e) {

                }
                Date entryDate = new Date(1969, 12, 31);
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    entryDate = df.parse(tokens[1]);
                } catch (Exception e) {

                }

                model.addItem(new RatSightingDataItem(id, entryDate, tokens[7], zip, tokens[9], tokens[16], tokens[23], latitude, longitude), false);
            }
            br.close();
        } catch (IOException e) {
            Log.e(HomeActivity.TAG, "error reading assets", e);
        }

    }

    @Override
    public void onBackPressed() {
        // prevent back button from being pressed
    }

    /**
     * method to display an error message with an alert dialog
     * @param error the error message to be displayed
     */
    private void displayErrorMessage(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.e("error", "displayErrorMessage: " + error);
        builder.setTitle("Error")
                .setMessage(error)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // co nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
