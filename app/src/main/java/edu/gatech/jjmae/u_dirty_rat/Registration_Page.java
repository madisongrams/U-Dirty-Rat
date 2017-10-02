package edu.gatech.jjmae.u_dirty_rat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.content.Intent;

public class Registration_Page extends AppCompatActivity {

    private Spinner spinner;
    private EditText newUsernameView;
    private EditText newPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__page);
        newUsernameView = (EditText) findViewById(R.id.editText);
        newPasswordView = (EditText) findViewById(R.id.editText2);
        Button createaccount = (Button) findViewById(R.id.button);
        addItemsOnSpinner();
        addListenerOnSpinnerItemSelection();
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (properRegistration()) {
                    Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });

    }

    public void addItemsOnSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("user");
        list.add("admin");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.spinner);
      //  spinner.setOnItemSelectedListener(new OnItemSelectedListener());
    }
    private boolean properRegistration() {

        // Store values at the time of the login attempt.
        String username = newUsernameView.getText().toString();
        String password = newPasswordView.getText().toString();
        if (username == null || password == null) {
            return false;
        }

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            displayErrorMessage(getApplicationContext().getString(R.string.error_field_required));
            return false;
        } else if (!isPasswordValid(password)) {
            displayErrorMessage(getApplicationContext().getString(R.string.error_invalid_password));
            return false;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            displayErrorMessage(getApplicationContext().getString(R.string.error_field_required));
            return false;
        } else if (!isUsernameValid(username)) {
            displayErrorMessage(getApplicationContext().getString(R.string.error_invalid_email));
            return false;
        }
        String error = UserData.register(username, password, false);
        if (error != null) {
            displayErrorMessage(error);
            return false;
        }
        return true;
    }

    private boolean isUsernameValid(String username) {
        //TODO: decide what makes username valid?
     //   if(username.length() < 5) {
         //   return false;
       // }

        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: make this better and more clear
        return password.length() > 2;
    }

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

