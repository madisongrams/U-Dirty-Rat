package edu.gatech.jjmae.u_dirty_rat;

import java.lang.reflect.Field;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by jackle91 on 11/14/17.
 */

public class FindItemByIDTest {

    private SampleModel model;
    private List<RatSightingDataItem> list;
    RatSightingDataItem item1 = new RatSightingDataItem(1, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item2 = new RatSightingDataItem(2, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item3 = new RatSightingDataItem(3, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item4 = new RatSightingDataItem(4, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item5 = new RatSightingDataItem(5, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item6 = new RatSightingDataItem(6, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item7 = new RatSightingDataItem(7, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item8 = new RatSightingDataItem(5050, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);
    RatSightingDataItem item9 = new RatSightingDataItem(-99, new Date("09/01/2017"),
            "Georgia",30013, "Tech", "Atl", "Conyers",
            90.9,-90.5);

    @Before
    public void setUp() {
        model = new SampleModel();
        list = new ArrayList<>();
    }
    private void setList(SampleModel model, List<RatSightingDataItem> list) {
        try {
            Field field = SampleModel.class.getDeclaredField("items");
            field.setAccessible(true);
            field.set(model, list);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void populateList() {
        setUp();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);
        list.add(item6);
        list.add(item7);
        list.add(item8);
        list.add(item9);
        setList(model, list);
    }
    @Test
    public void test() {
        populateList();
        assertEquals(model.findItemById(1), item1);
        assertEquals(model.findItemById(2), item2);
        assertEquals(model.findItemById(5), item5);
        assertEquals(model.findItemById(7), item7);
        assertEquals(model.findItemById(5050), item8);
        assertEquals(model.findItemById(-99), item9);

        assertNull(model.findItemById(100));
        assertNull(model.findItemById(-1));

        //Class c = listModel.class;
//        m = list.getClass().getMethod("findItemById", Integer (1));
//        RatSightingDataItem result = (RatSightingDataItem) m.invoke(1);
//        assertEquals(item1, result);
//

//        RatSightingDataItem item1 = new RatSightingDataItem(1, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item2 = new RatSightingDataItem(2, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item3 = new RatSightingDataItem(3, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item4 = new RatSightingDataItem(4, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item5 = new RatSightingDataItem(5, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item6 = new RatSightingDataItem(6, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item7 = new RatSightingDataItem(7, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item8 = new RatSightingDataItem(5050, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        RatSightingDataItem item9 = new RatSightingDataItem(-99, new Date("09/01/2017"),
//                "Georgia",30013, "Tech", "Atl", "Conyers",
//                90.9,-90.5);
//        list.add(item1);
//        list.add(item2);
//        list.add(item3);
//        list.add(item4);
//        list.add(item5);
//        list.add(item6);
//        list.add(item7);
//        list.add(item8);
//        list.add(item9);
//


    }
}
