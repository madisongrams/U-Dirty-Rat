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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;

public class SelectDatesActivity extends AppCompatActivity {

    private EditText startDateText;
    private EditText endDateText;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startDateText = (EditText) findViewById(R.id.startDateInput);
        endDateText = (EditText) findViewById(R.id.endDateInput);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areDatesValid()) {
                    ArrayList<RatSightingDataItem> rats = SampleModel.INSTANCE.getRatsByDates(startDate, endDate);
                    if (rats.size() < 1) {
                        displayErrorMessage("No rat data found between those dates.");
                    } else {
                        // TODO: EDIT THIS WHEN MAP IS READY!
//                        Intent intent = new Intent(getBaseContext(), WHATEVERMAPACTIVITYISCALLED.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("rats", rats);
//                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean areDatesValid() {
        String start = startDateText.getText().toString();
        String end = endDateText.getText().toString();

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        startDate = today;
        endDate = today;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");



        if (start.length() < 1) {
            try {
                startDate = df.parse("01/01/2017");
            } catch (ParseException e) {
            }
        } else {
            if (!isValidDate(start)) {
                displayErrorMessage("Date must be in format MM/DD/YYYY");
                return false;
            }
            try {
                startDate = df.parse(start);
            } catch (ParseException e) {
                displayErrorMessage("Date must be in format MM/DD/YYYY");
                return false;
            }
        }

        if (end.length() < 1) {
            endDate = today;
        } else {
            if (!isValidDate(end)) {
                displayErrorMessage("Date must be in format MM/DD/YYYY");
                return false;
            }
            try {
                endDate = df.parse(end);
            } catch (ParseException e) {
                displayErrorMessage("Date must be in format MM/DD/YYYY");
                return false;
            }
        }

        if (startDate.compareTo(today) > 0) {
            displayErrorMessage("Start date cannot be in the future.");
            return false;
        }

        if (startDate.compareTo(endDate) > 0) {
            displayErrorMessage("Start date cannot be after end date.");
            return false;
        }

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
