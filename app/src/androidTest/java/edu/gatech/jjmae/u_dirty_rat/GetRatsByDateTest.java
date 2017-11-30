package edu.gatech.jjmae.u_dirty_rat;

import org.junit.Test;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jpaul on 11/20/17.
 */

public class GetRatsByDateTest {

    private List<RatSightingDataItem> rats;
    private Date start;
    private Date end;

    @Test
    public void testGetRats() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        start = sf.parse("01/10/2017");
        SimpleDateFormat ef = new SimpleDateFormat("dd/MM/yyyy");
        end = sf.parse("20/11/2017");
        rats = SampleModel.INSTANCE.getRatsByDates(start, end);
        assertEquals(4, rats.size());

        start = sf.parse("01/01/2010");
        end = sf.parse("20/11/2017");
        rats = SampleModel.INSTANCE.getRatsByDates(start, end);
        assertEquals(100638, rats.size());

        start = sf.parse("01/01/2010");
        end = sf.parse("20/11/2017");
        rats = SampleModel.INSTANCE.getRatsByDates(start, end);
        assertEquals(100638, rats.size());

        start = sf.parse("01/12/2018");
        end = sf.parse("20/11/2017");
        rats = SampleModel.INSTANCE.getRatsByDates(start, end);
        assertEquals(0, rats.size());

    }

    @Test(expected = NullPointerException.class)
    public void testNull() {
        rats = SampleModel.INSTANCE.getRatsByDates(start, end);
    }

}
