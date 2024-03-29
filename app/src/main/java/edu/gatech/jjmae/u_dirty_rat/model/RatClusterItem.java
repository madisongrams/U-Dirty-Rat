package edu.gatech.jjmae.u_dirty_rat.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by jpaul on 11/7/17.
 * clusters rat sightings
 */

public class RatClusterItem implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    /**
     * constructor
     * @param lat latitude
     * @param lng longitude
     */
    public RatClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    /**
     * constructor
     * @param lat latitude
     * @param lng longitude
     * @param title title
     * @param snippet snippet
     */
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
        if (mTitle == null) {
            return "";
        } else {
            return mTitle;
        }
    }

    @Override
    public String getSnippet() {
        if (mSnippet == null) {
            return "";
        } else {
            return mSnippet;
        }
    }

}
