package edu.gatech.jjmae.u_dirty_rat.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        this._Date = date;
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
     * This is a static factory method that constructs a rat data item given a text line in the correct format.
     * It assumes that a rata data item is in a single string with each attribute separated by a tab character
     * The order of the data is assumed to be:
     *
     * 0 - id
     * 1 - date
     * 2 - location
     * 3 - zip
     * 4 - address
     * 5 - city
     * 6 - borough
     * 7 - latitude
     * 8 - longitude
     *
     * @param line  the text line containing the data
     * @return the rat data object
     */
    public static RatSightingDataItem parseEntry(String line) {
        assert line != null;
        String[] tokens = line.split("\t");
        assert tokens.length == 9;

        int id = 0;
        int zip = 0;
        try {
            id = Integer.parseInt(tokens[0]);
        } catch (Exception e) {
        }
        try {
            zip = Integer.parseInt(tokens[3]);
        } catch (Exception e) {
        }
        double latitude =  0.0;
        double longitude = 0.0;
        try {
            latitude = Double.parseDouble(tokens[7]);
            longitude = Double.parseDouble(tokens[8]);
        } catch (Exception e) {

        }
        Date entryDate = new Date(1969, 12, 31);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            entryDate = df.parse(tokens[1]);
        } catch (Exception e) {

        }
        return new RatSightingDataItem(id, entryDate, tokens[2], zip, tokens[4], tokens[5], tokens[6], latitude, longitude);
    }
    public void saveAsText(PrintWriter writer) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        writer.println(_ID + "\t" + df.format(_Date) + "\t" + _Location + "\t" + _ZipCode
                + "\t" + _Address + "\t" + _City + "\t" + _Borough + "\t" + _Latitude
                + "\t" + _Longitude);
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

