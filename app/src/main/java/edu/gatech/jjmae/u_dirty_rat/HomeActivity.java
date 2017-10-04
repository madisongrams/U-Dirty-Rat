package edu.gatech.jjmae.u_dirty_rat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // When this button (logout) is hit, we should go back to welcome screen.
        // User should also be logged out
        Button next1 = (Button) findViewById(R.id.button3);
        next1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UserData.setCurrentUser(null);
                Intent myIntent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        // When this button (add a rat entry) is hit, ...
        Button next2 = (Button) findViewById(R.id.button8);
        next2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UserData.setCurrentUser(null);
                Intent myIntent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        // When this button (view rat entries) is hit, ...
        Button next3 = (Button) findViewById(R.id.button7);
        next3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UserData.setCurrentUser(null);
                Intent myIntent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
    }

    @Override
    public void onBackPressed() {
        // prevent back button from being pressed
    }

}
