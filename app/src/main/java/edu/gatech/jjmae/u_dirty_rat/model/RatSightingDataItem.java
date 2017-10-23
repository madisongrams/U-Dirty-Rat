package edu.gatech.jjmae.u_dirty_rat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Justin on 10/9/2017.
 */

public class RatSightingDataItem implements Comparable<RatSightingDataItem>, Comparator<RatSightingDataItem>, Parcelable {

    private int _ID;
    private Date _Date;
    private String _Location;
    private int _ZipCode;
    private String _Address;
    private String _City;
    private String _Borough;
    private double _Latitude;
    private double _Longitude;

    /**
     * constructor to create a data item with all necessary fields
     * @param id rat id
     * @param date date of rat sighting
     * @param location location of rat
     * @param zip zip code of rat
     * @param address address where rat was seen
     * @param city city rat was seen
     * @param borough borough rat was seen
     * @param latitude rat's location latitude
     * @param longitude rat's location longitude
     */
    public RatSightingDataItem(int id, Date date, String location, int zip,
                               String address, String city, String borough, double latitude,
                               double longitude) {
        this._ID = id;
        this._Date =date;
        this._Location = location;
        this._ZipCode = zip;
        this._Address = address;
        this._City = city;
        this._Borough = borough;
        this._Latitude = latitude;
        this._Longitude = longitude;
    }

    protected RatSightingDataItem(Parcel in) {
        _ID = in.readInt();
        _Location = in.readString();
        _ZipCode = in.readInt();
        _Address = in.readString();
        _City = in.readString();
        _Borough = in.readString();
        _Latitude = in.readDouble();
        _Longitude = in.readDouble();
    }

    public static final Parcelable.Creator<RatSightingDataItem> CREATOR
            = new Parcelable.Creator<RatSightingDataItem>() {
        public RatSightingDataItem createFromParcel(Parcel in) {
            return new RatSightingDataItem(in);
        }

        public RatSightingDataItem[] newArray(int size) {
            return new RatSightingDataItem[size];
        }
    };

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

    @Override
    public int compareTo(RatSightingDataItem r) {
        return get_Date().compareTo(r.get_Date());
    }

    @Override
    public int compare(RatSightingDataItem r1, RatSightingDataItem r2) {
        return r1.get_Date().compareTo(r2.get_Date());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Address);
        dest.writeInt(_ID);
        dest.writeString(_Location);
        dest.writeSerializable(_Date);
        dest.writeDouble(_Latitude);
        dest.writeDouble(_Longitude);
        dest.writeString(_Borough);
        dest.writeString(_City);
        dest.writeInt(_ZipCode);
    }
}

