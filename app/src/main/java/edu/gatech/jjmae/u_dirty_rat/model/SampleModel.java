package edu.gatech.jjmae.u_dirty_rat.model;

import android.util.Log;

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
    private int currentid = 40000000;

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
//        if (isNewRat) {
//            addToNewRats(item);
//        } else {
//            csvRats.add(item);
//        }
    }

//    /**
//     * adds new rat item to new rats so that list remains ordered by date
//     * @param item item to be added
//     */
//    private void addToNewRats(RatSightingDataItem item) {
//        if (newRats.size() < 1) {
//            newRats.add(item);
//            return;
//        }
//        Date newDate = item.get_Date();
//        int index = 0;
//        while (index < newRats.size() && newDate.compareTo(newRats.get(index).get_Date()) > 0) {
//            index++;
//        }
//        if (index >= newRats.size()) {
//            newRats.add(item);
//        } else {
//            newRats.add(index, item);
//        }
//    }
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
     * @return current id
     */
    public int getCurrentid() {
        return currentid++;
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
}
