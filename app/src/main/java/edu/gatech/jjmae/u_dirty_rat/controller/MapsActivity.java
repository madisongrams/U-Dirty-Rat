package edu.gatech.jjmae.u_dirty_rat.controller;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Date;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatClusterItem;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import edu.gatech.jjmae.u_dirty_rat.services.GPSTracker;


/**
 * activity for maps
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<RatSightingDataItem> rats;
    private ClusterManager<RatClusterItem> mClusterManager;
    private LatLng initialView;
    private boolean mapsReady;

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
        if (gps.canGetLocation()) {
            mapsReady = true;
            initialView = new LatLng(gps.getLatitude(), gps.getLongitude());
            Log.d("Initial location = ", String.valueOf(initialView));
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupMap();
        setUpClusterer();
        mMap.setMyLocationEnabled(true);

        if (mapsReady) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialView, 6));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(rats.get(500).get_Latitude(),
                    rats.get(500).get_Longitude()), 6));
        }
    }

    private void setUpClusterer() {
        mClusterManager = new ClusterManager<>(this, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);

        addClusterItems();
    }

    /**
     * This method adds rat items to the cluster layer of the map and links the info windows of each
     * marker to the details page of the rat
     */
    private void addClusterItems() {
        for (RatSightingDataItem rat: rats) {
            RatClusterItem ratItem = new RatClusterItem(rat.get_Latitude(), rat.get_Longitude(),
                    String.valueOf(rat.get_ID()), rat.getModifiedDate());
            mClusterManager.addItem(ratItem);
        }

        mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
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
    /**
     * this private method sets up the map for a better UX
     */
    private void setupMap() {
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
    }
}

