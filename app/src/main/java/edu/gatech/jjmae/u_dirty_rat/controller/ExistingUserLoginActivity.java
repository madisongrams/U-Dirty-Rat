package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.UserData;

/**
 * A login screen that offers login via username/password.
 */
public class ExistingUserLoginActivity extends AppCompatActivity {
    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user_login);
        mUsernameView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attemptLogin()) {
                    Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });

        View mLoginFormView = findViewById(R.id.login_form);
        View mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * @return boolean dependent on whether or not login is successful
     */
    private boolean attemptLogin() {

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

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
        String error = UserData.login(username, password);
        if (error == null) {
            return true;
        } else {
            displayErrorMessage(error);
            return false;
        }
    }

    /**
     * determines whether given user name is valid
     * @param username username to be tested
     * @return boolean whether or not username is valid
     */
    private boolean isUsernameValid(String username) {
        //TODO: decide what makes username valid?
        return true;
    }
    /**
     * determines whether given password is valid
     * password much be at least three characters long and must not contain  &, ., -, or *
     * @param password password to be tested
     * @return boolean whether or not password is valid
     */
    private boolean isPasswordValid(String password) {
        if (password.length() < 3) {
            return false;
        }
        String temp = "";
        for (int i = 0; i < password.length(); i++) {
            temp = password.substring(i, i+1);
            if (temp == "&" || temp == "." || temp == "-" || temp == "*") {
                return false;
            }
        }
        return true;
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

