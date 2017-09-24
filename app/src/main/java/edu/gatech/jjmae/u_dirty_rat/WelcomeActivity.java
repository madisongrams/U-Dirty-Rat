package edu.gatech.jjmae.u_dirty_rat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //When this button is hit, new user registration page should be displayed
        //Replace HomeActivity class by "new user registration" class
        Button registration = (Button) findViewById(R.id.button);
        registration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Registration_Page.class);
                startActivityForResult(myIntent, 0);
            }

        });

        // When this button is hit, existing user page should be displayed.
        //Replace HomeActivity class by "existing user" class
        Button login = (Button) findViewById(R.id.button2);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ExistingUserLoginActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

    }

    @Override
    public void onBackPressed() {
        // prevent back button from being pressed
    }


}
