package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Manifest;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;

import static com.google.android.gms.maps.UiSettings.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<RatSightingDataItem> rats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (rats == null) {
//            rats = (ArrayList<RatSightingDataItem>) getIntent().getParcelableExtra("rats");
            Date start = (Date) getIntent().getSerializableExtra("start");
            Date end = (Date) getIntent().getSerializableExtra("end");
            rats = SampleModel.INSTANCE.getRatsByDates(start, end);

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NewRatSightingActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupMap();
        createMarkers(rats);
    }

    /**
     * Private method to create rat markers for all rats requested
     * @param rats the rat items we're creating markers for
     */
    private void createMarkers(ArrayList<RatSightingDataItem> rats) {
        LatLng ratMarker = new LatLng(rats.get(0).get_Latitude(), rats.get(0).get_Longitude());
        String date = "";
        for (RatSightingDataItem rat: rats) {
            ratMarker = new LatLng(rat.get_Latitude(), rat.get_Longitude());

            date = rat.get_Date().toString();
            try {
                date = date.substring(0, 10) + " " + date.substring(30, 34);
            } catch (IndexOutOfBoundsException e) {
                date = date.substring(0, 10) + " " + date.substring(24, 28);
            }

            mMap.addMarker(new MarkerOptions()
                    .position(ratMarker)
                    .title(Integer.toString(rat.get_ID()))
                    .snippet(date));
        }
//        mMap.moveCamera(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ratMarker));
        mMap.setMinZoomPreference(5);
        mMap.setMaxZoomPreference(20);
    }

    private void setupMap() {
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
    }
}
