package edu.gatech.jjmae.u_dirty_rat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNewRatSighting extends AppCompatActivity {
    private EditText mEditDate;
    private EditText mEditLocation;
    private EditText mEditZip;
    private EditText mEditAddress;
    private EditText mEditCity;
    private EditText mEditLongitude;
    private EditText mEditLatitude;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        Date entryDate = new Date(1969, 12, 31);
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
        int zipInt = 0;
        try {
            zipInt = Integer.parseInt(zip);
        } catch (Exception e) {
            displayErrorMessage("Zip Code must be a number.");
            return false;
        }
        double longitudeDoub = 0.0;
        try {
            longitudeDoub = Double.parseDouble(longitude);
        } catch (Exception e) {
            displayErrorMessage("Longitude must be a valid decimal.");
            return false;
        }
        double latitudeDoub = 0.0;
        try {
            latitudeDoub = Double.parseDouble(latitude);
        } catch (Exception e) {
            displayErrorMessage("Latitude must be a valid decimal.");
            return false;
        }

        SampleModel model = SampleModel.INSTANCE;
        int id = model.getCurrentid();
        //TODO: add borough entry
        model.addItem(new RatSightingDataItem(id, entryDate, location, zipInt, address, city, city, latitudeDoub, longitudeDoub));
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
     * @param zip zipcode string to be checked
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
                    public void onClick(DialogInterface dialog, int which) {
                        // co nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
