package edu.gatech.jjmae.u_dirty_rat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Justin on 10/9/2017.
 */

public class RatSightingDataItem {

    private int _ID;
    private Date _Date;
    private String _Location;
    private int _ZipCode;
    private String _Address;
    private String _City;
    private String _Borough;
    private double _Latitude;
    private double _Longitude;

    public RatSightingDataItem(int id, Date date, String location, int zip,
                               String address, String city, String borough, double latitude,
                               double longitude) {
        this._ID = id;
        this._Date = date;
        this._Location = location;
        this._ZipCode = zip;
        this._Address = address;
        this._City = city;
        this._Borough = borough;
        this._Latitude = latitude;
        this._Longitude = longitude;
    }

    /**
     * All getters for the instance variables in this class
     *
     */
    public int get_ID() {
        return _ID;
    }

    public Date get_Date() {
        return _Date;
    }

    public String get_Location() {
        return _Location;
    }

    public int get_ZipCode() {
        return _ZipCode;
    }

    public String get_Address() {
        return _Address;
    }

    public String get_City() {
        return _City;
    }

    public String get_Borough() {
        return _Borough;
    }

    public double get_Latitude() {
        return _Latitude;
    }

    public double get_Longitude() {
        return _Longitude;
    }


    /**
     * a simple tostring method
     * @return string representing an instance of this class
     */
    public String toString() {
        return _ID + " " + _Date + " " + _Borough;
    }
}
