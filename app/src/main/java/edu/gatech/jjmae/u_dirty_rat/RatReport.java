package edu.gatech.jjmae.u_dirty_rat;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by jackle91 on 10/5/17.
 */
public class RatReport {

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("project/u-dirty-rat-f750f/database/data");
//
//    // Attach a listener to read the data at our posts reference
//    ref.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            Post post = dataSnapshot.getValue(Post.class);
//            System.out.println(post);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            System.out.println("The read failed: " + databaseError.getCode());
//        }
//    });
//
//    JSONObject obj = new JSONObject("https://u-dirty-rat-f750f.firebaseio.com/");
//
//    List<String> list = new ArrayList<String>();
//    JSONArray array = obj.getJSONArray("interests");
//    for (int i = 0 ; i < array.length; i++) {
//        list.add(array.getJSONObject(i).getString("interestKey"));
//    }
//
//    private static List<RatReport> ratReport = new ArrayList<>();
//
//    public static List<RatReport> getRatReport() {
//        return ratReport;
//    }
//
//    public static void setRatReport(List<RatReport> ratReport) {
//        RatReport.ratReport = ratReport;
//    }
//
//
//    jsonReader.close();
//    private static final long serialVersionUID = 1L;
//
//    private static int num = 0;
//    private int idNUm;
//    private String uniqueKey = "";
//    private String createdDate = "";
//    private String locationType = "";
//    private String incidentZip = "";
//    private String incidentAddress = "";
//    private String city = "";
//    private String borough = "";
//    private String latitude = "";
//    private String longtitude = "";
//
//
//
//    String name;
//    ArrayList<Double> drinkingLevel;
//    ArrayList<Double> l;
//    ArrayList<Date> datelist;
//    String g;
//
//    public RatReport(String name, ArrayList<Double> l, ArrayList<Double> drinkingLevel, String g, ArrayList<Date> datelist) {
//        this.name = name;
//        this.g = g;
//        this.drinkingLevel = drinkingLevel;
//        this.l = l;
//        this.datelist = datelist;
//    }
//
//    public String getlocationName() {
//        return this.name;
//    }
//
//    public void setlocationName(String name) {
//        this.name = name;
//    }
//
//    public ArrayList<Double> getcriticalLevel() {
//        return this.drinkingLevel;
//    }
//
//    public void setcriticalLevel(ArrayList<Double> a) {
//        drinkingLevel = a;
//    }
//
//    public ArrayList<Double> getL() {
//        return l;
//    }
//
//    public void setL(ArrayList<Double> l) {
//        this.l = l;
//    }
//
//    public String getg() {
//        return g;
//    }
//
//
//    public void setg(String g) {
//        this.g = g;
//    }
//
//    public ArrayList<Date> getdatelist() {return datelist;}
//
//    public void setdatelist(ArrayList<Date> datelist) {
//        this.datelist = datelist;
//    }
}
