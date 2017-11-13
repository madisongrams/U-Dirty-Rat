package edu.gatech.jjmae.u_dirty_rat.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.UserData;

/**
 * user registration page
 */
public class RegistrationPageActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText newUsernameView;
    private EditText newPasswordView;
    private EditText newEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        spinner = (Spinner) findViewById(R.id.spinner);
        newUsernameView = (EditText) findViewById(R.id.editText);
        newPasswordView = (EditText) findViewById(R.id.editText2);
        newEmailView = (EditText) findViewById(R.id.editText4);
        Button createAccount = (Button) findViewById(R.id.button);
        addItemsOnSpinner();
      //  addListenerOnSpinnerItemSelection();
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (properRegistration()) {
                    Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });

    }

    /**
     * adds items (user, admin) to the spinner for this page
     */
    private void addItemsOnSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("user");
        list.add("admin");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    /**
     * listener for spinner
     * @return current spinner selection
     */
    private String addListenerOnSpinnerItemSelection() {
        return (String) spinner.getSelectedItem();
    }

    /**
     * tests registration when user presses register.
     * it does a few checks before passing it to the model to do a
     * final check on the username and login
     * @return boolean dependent on whether or not registration is successful
     */
    private boolean properRegistration() {

        // Store values at the time of the login attempt.
        String username = newUsernameView.getText().toString();
        String password = newPasswordView.getText().toString();
        String email = newEmailView.getText().toString();

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
            displayErrorMessage(getApplicationContext().getString(R.string.error_invalid_username));
            return false;
        }

        //check for a valid email
        if (TextUtils.isEmpty(email)) {
            displayErrorMessage(getApplicationContext().getString(R.string.error_field_required));
            return false;
        } else if (!email.contains("@") || !email.contains(".")) {
            displayErrorMessage(getApplicationContext().getString(R.string.error_invalid_email));
            return false;
        }

        boolean admin;
        admin = addListenerOnSpinnerItemSelection().equals("admin");
        String error = UserData.register(username, password, admin, email);
        if (error != null) {
            displayErrorMessage(error);
            return false;
        }
        Log.i("Registration", "new user registered, saving userdata");
        File file = new File(this.getFilesDir(), "userData.txt");
        UserData.saveText(file);
        return true;
    }
    /**
     * determines whether given user name is valid
     * @param username username to be tested
     * @return boolean whether or not username is valid
     */
    private boolean isUsernameValid(String username) {
        //TODO: decide what makes username valid?
     //   if(username.length() < 5) {
         //   return false;
       // }

        return true;
    }
    /**
     * determines whether given password is valid
     * @param password password to be tested
     * @return boolean whether or not password is valid
     */
    private boolean isPasswordValid(String password) {
        //TODO: make this better and more clear
        return password.length() > 2;
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

