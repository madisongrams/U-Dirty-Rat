package edu.gatech.jjmae.u_dirty_rat.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Justin on 10/9/2017.
 */

/**
 * model class to hold a list of rat data
 */
public class SampleModel {
    public static final SampleModel INSTANCE = new SampleModel();

    private List<RatSightingDataItem> items;
//    private List<RatSightingDataItem> csvRats;
//    private List<RatSightingDataItem> newRats;
    private int currentID = 40000000;

    /**
     * constructor that initializes backing array
     */
    private SampleModel() {
        items = new ArrayList<>();
    }

    /**
     * method to add item to backing arrays
     * @param item item to be added to array
     * @param isNewRat whether or not rat is new
     */
    public void addItem(RatSightingDataItem item, boolean isNewRat) {
        items.add(item);
    }
    /**
     * getter for backing array
     * @return backing array
     */

    public List<RatSightingDataItem> getItems() {
        return items;
    }

    /**
     * search method to find items
     * @param id id of item to be found
     * @return item found or null if id not in list
     */
    public RatSightingDataItem findItemById(int id) {
        for (RatSightingDataItem d : items) {
            if (d.get_ID() == id) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }

    /**
     * current id is the id to be used for a new rat
     * id is incremented for next rat to guarantee rats do not have same id
     * @return current id
     */
    public int getCurrentID() {
        return currentID++;
    }

    /**
     * Get list of rats whose entry date lies in the date range
     * @param start the start date
     * @param end the end date
     * @return the list of rats
     */
    public ArrayList<RatSightingDataItem> getRatsByDates(Date start, Date end) {
        ArrayList<RatSightingDataItem> rats = new ArrayList<RatSightingDataItem>();

        Collections.sort(items);
        int index = Collections.binarySearch(items, new RatSightingDataItem(0, start, "default", 0, "default", "default", "default", 0, 0));
        if (index < 0) {
            index = (index + 1) * -1;
        }
        Log.d(TAG, "getRatsByDates: index = " + index);
        while (index < items.size() && end.compareTo(items.get(index).get_Date()) >= 0) {
            Log.d(TAG, "getRatsByDates: added data item " + items.get(index).get_ID());
            rats.add(items.get(index));
            index++;
        }

        return rats;
    }

    public void loadFromText(BufferedReader reader) {
        Log.d("SampleModel:", "Loading Text File");
        items.clear();
        try {
            String countStr = reader.readLine();
            Log.d("SampleModel", "loadFromText: " + countStr);
            assert countStr != null;
            int count = Integer.parseInt(countStr);
            // old current id
            String idStr = reader.readLine();
            Log.d("SampleModel", "loadFromText: " + idStr);
            int id = Integer.parseInt(idStr);
            currentID = id;
            //then read in each user to model
            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                RatSightingDataItem r = RatSightingDataItem.parseEntry(line);
                items.add(r);
            }
            //be sure and close the file
//            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        Log.d("SampleModel:", "Done loading text file with " + items.size() + " rat data items");

    }

    public boolean saveText(File file) {
        Log.d("SampleModel:", "Saving as a text file");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
            saveAsText(pw);
            //pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("UserData", "Error opening the text file for save!");
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return true;
    }

    void saveAsText(PrintWriter writer) {
        Log.d("SampleModel: ", "saving: " + items.size() + " data items");
        writer.println(items.size());
        // also saving currentID
        writer.println(currentID);
        for (RatSightingDataItem r : items) {
            r.saveAsText(writer);
        }
    }

}
