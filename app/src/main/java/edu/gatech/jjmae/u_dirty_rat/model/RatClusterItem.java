package edu.gatech.jjmae.u_dirty_rat.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by jpaul on 11/3/17.
 */

public class RatClusterItem implements ClusterItem {
    private LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    public RatClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public RatClusterItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
