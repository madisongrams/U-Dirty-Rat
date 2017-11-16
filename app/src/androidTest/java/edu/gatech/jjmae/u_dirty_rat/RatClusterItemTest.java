package edu.gatech.jjmae.u_dirty_rat;

/**
 * Created by jpaul on 11/12/17.
 */


import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.android.gms.maps.model.LatLng;
import edu.gatech.jjmae.u_dirty_rat.model.RatClusterItem;
import static junit.framework.Assert.assertEquals;

/**
 * This class tests whether RatClusterItem.class functions correctly and covers all cases.
 */
public class RatClusterItemTest {

    private List<RatClusterItem> testRats;
//    private RatClusterItem[] correctRats;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        testRats = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testCreateRatClusterItems() {
        testRats.add(new RatClusterItem(90, -90, "dirty rat 1",
                "number one rat"));
        testRats.add(new RatClusterItem(91, -91, "dirty rat 2",
                "number two rat"));
        testRats.add(new RatClusterItem(92, -92, "dirty rat 3",
                "number three rat"));
        testRats.add(new RatClusterItem(93, -93, "dirty rat 4",
                "number four rat"));
        testRats.add(new RatClusterItem(94, -94, "dirty rat 5",
                "number five rat"));

        assertEquals(5, testRats.size());

        testRats.add(new RatClusterItem(95, -95, "dirty rat 6",
                "number six rat"));
        testRats.add(new RatClusterItem(96, -96, "dirty rat 7",
                "number seven rat"));
        testRats.add(new RatClusterItem(97, -97, "dirty rat 8",
                "number eight rat"));
        testRats.add(new RatClusterItem(98, -98, "dirty rat 9",
                "number nine rat"));
        testRats.add(new RatClusterItem(99, -99, "dirty rat 10",
                "number ten rat"));

        assertEquals(10, testRats.size());

        testRats.add(new RatClusterItem(100, -100));
        testRats.add(new RatClusterItem(101, -101));

        assertEquals(12, testRats.size());
    }

    @Test(timeout = TIMEOUT)
    public void testGetTitle() {
        testRats.add(new RatClusterItem(90, -90, "dirty rat 1",
                "number one rat"));
        assertEquals("dirty rat 1", testRats.get(0).getTitle());

        testRats.add(new RatClusterItem(100, -100));
        assertEquals("", testRats.get(1).getTitle());

    }

    @Test(timeout =  TIMEOUT)
    public void testGetSnippet() {
        testRats.add(new RatClusterItem(90, -90, "dirty rat 1",
                "number one rat"));
        assertEquals("number one rat", testRats.get(0).getSnippet());

        testRats.add(new RatClusterItem(100, -100));
        assertEquals("", testRats.get(1).getSnippet());
    }

    @Test(timeout = TIMEOUT)
    public void testGetPosition() {
        testRats.add(new RatClusterItem(0, 0, "dirty rat 1",
                "number one rat"));
        assertEquals(new LatLng(0.0,0.0), testRats.get(0).getPosition());

        testRats.add(new RatClusterItem(100, -100));
        assertEquals(new LatLng(100.00, -100.00), testRats.get(1).getPosition());

    }
//
//    @Test(timeout = TIMEOUT)
//    public void testCreateEmpties() {
//        Random rand = new Random(13);
//        for (int i = 0; i < 10; i++) {
//            testRats.add(i, new RatClusterItem(rand.nextDouble(), rand.nextDouble()));
//        }
//    }
}
