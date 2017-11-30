package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Date;

import edu.gatech.jjmae.u_dirty_rat.R;

/**
 * activity to select a date range. used for both maps and graphs
 */
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
        startDateText.setFocusable(true);
        startDateText.requestFocus();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (areDatesValid()) {
                        Bundle bundle = getIntent().getExtras();
                        if (bundle == null) {
                            return;
                        }
                        if (("maps").equals(bundle.getString("activity"))) {
                            Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                            intent.putExtra("start", startDate);
                            intent.putExtra("end", endDate);
                            startActivity(intent);
                        }
                        if ("graphs".equals(bundle.getString("activity"))) {
                            if ((startDate.getMonth() == endDate.getMonth()) &&
                                    (startDate.getYear() == endDate.getYear()) &&
                                    (startDate.getDate() == endDate.getDate())) {
                                displayErrorMessage("Please include two separate dates to " +
                                        "show a graph.");
                            } else {
                                Intent intent = new Intent(getBaseContext(), GraphActivity.class);
                                intent.putExtra("start", startDate);
                                intent.putExtra("end", endDate);
                                startActivity(intent);
                            }
                    }
                }
            }
        });
    }

    /**
     * method to determine whether given dates entered by user are valid
     * @return true/false whether or not dates are valid
     */
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
            if (isNotValidDate(start)) {
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
            if (isNotValidDate(end)) {
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
    private boolean isNotValidDate(String date) {
        String regex =  "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        return !date.matches(regex);
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
