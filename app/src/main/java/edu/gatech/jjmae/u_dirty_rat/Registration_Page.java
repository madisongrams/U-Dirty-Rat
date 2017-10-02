package edu.gatech.jjmae.u_dirty_rat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.content.Intent;

public class Registration_Page extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__page);
        Button createaccount = (Button) findViewById(R.id.button);
        addItemsOnSpinner();
        addListenerOnSpinnerItemSelection();
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                    startActivityForResult(myIntent, 0);
                setContentView(R.layout.activity_welcome);
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
       // spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
