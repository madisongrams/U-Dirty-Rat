package edu.gatech.jjmae.u_dirty_rat.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import edu.gatech.jjmae.u_dirty_rat.services.GPSTracker;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<RatSightingDataItem> rats;

    private double latitude;
    private double longitude;

    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

//     GPSTracker class
    GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (rats == null) {
            Date start = (Date) getIntent().getSerializableExtra("start");
            Date end = (Date) getIntent().getSerializableExtra("end");
            rats = SampleModel.INSTANCE.getRatsByDates(start, end);

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
//                execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        gps = new GPSTracker(MapsActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
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

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, RatSightingViewDetailActivity.class);
                    Log.d("MYAPP", "Switch to detailed view for item: " + marker.getTitle());
                    intent.putExtra(RatSightingDetailFragment.ARG_ITEM_ID, Integer.parseInt(marker.getTitle()));

                    context.startActivity(intent);
                }
            });
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ratMarker));
        mMap.setMinZoomPreference(2);
        mMap.setMaxZoomPreference(16);
    }

    private void setupMap() {
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
    }
}
