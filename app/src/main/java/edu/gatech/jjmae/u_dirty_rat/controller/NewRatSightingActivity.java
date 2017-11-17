package edu.gatech.jjmae.u_dirty_rat.controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import edu.gatech.jjmae.u_dirty_rat.services.GPSTracker;

/**
 * activity screen for reporting a new rat sighting
 */
public class NewRatSightingActivity extends AppCompatActivity {
    private EditText mEditDate;
    private EditText mEditLocation;
    private EditText mEditZip;
    private EditText mEditAddress;
    private EditText mEditCity;
    private EditText mEditLongitude;
    private EditText mEditLatitude;
    private double mCurrLatitude;
    private double mCurrLongitude;

    private static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_new_rat_sighting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditDate = (EditText) findViewById(R.id.editdate);
        mEditLocation = (EditText) findViewById(R.id.editlocation);
        mEditZip = (EditText) findViewById(R.id.editzip);
        mEditAddress = (EditText) findViewById(R.id.editaddress);
        mEditCity = (EditText) findViewById(R.id.editcity);
        mEditLongitude = (EditText) findViewById(R.id.editlongitude);
        mEditLatitude = (EditText) findViewById(R.id.editlatitude);
        mCurrLatitude = 0.0;
        mCurrLongitude = 0.0;

        try {
            String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
//                execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        GPSTracker gps = new GPSTracker(NewRatSightingActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){
            mEditLatitude.setText(String.valueOf(gps.getLatitude()));
            mEditLongitude.setText(String.valueOf(gps.getLongitude()));

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }



        Button submitNewRatButton = (Button) findViewById(R.id.button);
        submitNewRatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addNewRatData()) {
                    Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });

    }

    /**
     * method called when submit button is pressed
     * checks if all fields are valid
     * @return true or false depending on whether or not rat data was successfully added
     */
    private boolean addNewRatData() {
        String date = mEditDate.getText().toString();
        String location = mEditLocation.getText().toString();
        String zip = mEditZip.getText().toString();
        String address = mEditAddress.getText().toString();
        String city = mEditCity.getText().toString();
        String longitude = mEditLongitude.getText().toString();
        String latitude = mEditLatitude.getText().toString();


        if (date.isEmpty() || location.isEmpty() || zip.isEmpty() || address.isEmpty()
                || city.isEmpty() || longitude.isEmpty() || latitude.isEmpty()) {
            displayErrorMessage("All fields are required.");
            return false;
        }

        if (!isValidDate(date)) {
            displayErrorMessage("Date must be in format MM/DD/YYYY");
            return false;
        }
        Date entryDate;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            entryDate = df.parse(date);
        } catch (Exception e) {
            displayErrorMessage("Date must be in format MM/DD/YYYY");
            return false;
        }

        if (!isValidZip(zip)) {
            displayErrorMessage("Zip Code must be a number.");
            return false;
        }
        int zipInt;
        try {
            zipInt = Integer.parseInt(zip);
        } catch (Exception e) {
            displayErrorMessage("Zip Code must be a number.");
            return false;
        }

        double longitudeDouble;
        double latitudeDouble;
        if ((mCurrLongitude != 0) && (mCurrLatitude != 0)) {
            longitudeDouble = mCurrLongitude;
            latitudeDouble = mCurrLatitude;
        } else {
            try {
                longitudeDouble = Double.parseDouble(longitude);
            } catch (Exception e) {
                displayErrorMessage("Longitude must be a valid decimal.");
                return false;
            }
            final int minLong = -180;
            final int maxLong = 180;
            if ((longitudeDouble < minLong) || (longitudeDouble > maxLong)) {
                displayErrorMessage("Invalid longitude.");
            }

            try {
                latitudeDouble = Double.parseDouble(latitude);
            } catch (Exception e) {
                displayErrorMessage("Latitude must be a valid decimal.");
                return false;
            }
            final int minLat = -90;
            final int maxLat = 90;
            if ((latitudeDouble < minLat) || (latitudeDouble > maxLat)) {
                displayErrorMessage("Invalid latitude.");
                return false;
            }

        }
//
        SampleModel model = SampleModel.INSTANCE;
        int id = model.getCurrentID();

        model.addItem(new RatSightingDataItem(id, entryDate, location, zipInt, address, city, city,
                latitudeDouble, longitudeDouble));
        File file = new File(this.getFilesDir(), "ratData.txt");
        SampleModel.INSTANCE.saveText(file);
        return true;
    }

    /**
     * method using regex to make sure date string is valid
     * @param date date string to be checked
     * @return whether or not date is valid
     */
    private boolean isValidDate(String date) {
        String regex =  "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        return date.matches(regex);
    }

    /**
     * method using regex to make sure zip code string is valid
     * @param zip zip code string to be checked
     * @return whether or not zip code is valid
     */
    private boolean isValidZip(String zip) {
        return zip.matches("[0-9]+");
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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // co nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}

