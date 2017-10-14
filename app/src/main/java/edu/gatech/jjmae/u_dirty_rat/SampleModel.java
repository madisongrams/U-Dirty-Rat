package edu.gatech.jjmae.u_dirty_rat;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Justin on 10/9/2017.
 */

/**
 * model class to hold a list of rat data
 */
public class SampleModel {
    public static final SampleModel INSTANCE = new SampleModel();

    private List<RatSightingDataItem> items;
    private int currentid = 35502400;

    /**
     * constructor that initializes backing array
     */
    private SampleModel() {
        items = new ArrayList<>();
    }

    /**
     * method to add item to backing array
     * @param item item to be added to array
     */
    public void addItem(RatSightingDataItem item) {
        items.add(item);
        currentid++;
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
     * @return current id
     */
    public int getCurrentid() {
        return currentid;
    }
}
