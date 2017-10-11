package edu.gatech.jjmae.u_dirty_rat;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 10/9/2017.
 */

public class SampleModel {
    public static final SampleModel INSTANCE = new SampleModel();

    private List<RatSightingDataItem> items;

    private SampleModel() {
        items = new ArrayList<>();
    }

    public void addItem(RatSightingDataItem item) {
        items.add(item);
    }

    public List<RatSightingDataItem> getItems() {
        return items;
    }

    public RatSightingDataItem findItemById(int id) {
        for (RatSightingDataItem d : items) {
            if (d.get_ID() == id) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }
}
