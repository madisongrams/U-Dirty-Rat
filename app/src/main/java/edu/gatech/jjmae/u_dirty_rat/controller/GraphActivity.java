package edu.gatech.jjmae.u_dirty_rat.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;

/**
 * graph activity class
 */
public class GraphActivity extends AppCompatActivity {
    private List<RatSightingDataItem> rats;
    private int[] ratCounter;
    //private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        int numMonths = 12;
        if (rats == null) {
//          rats = (ArrayList<RatSightingDataItem>) getIntent().getParcelableExtra("rats");
            Date start = (Date) getIntent().getSerializableExtra("start");
            Date end = (Date) getIntent().getSerializableExtra("end");
            int monthGap = (end.getMonth() - start.getMonth());
            int yearGap = numMonths * (end.getYear() - start.getYear());

            ratCounter = new int[monthGap + yearGap];
            rats = SampleModel.INSTANCE.getRatsByDates(start, end);
            Collections.sort(rats);
        }
        int index;
        for (RatSightingDataItem rat : rats) {
            int difference = rat.get_Date().getYear() - rats.get(0).get_Date().getYear();

//            int monthDiff = rat.get_Date().getMonth() - rats.get(0).get_Date().getMonth();

            if (difference != 0) {
                index = (difference * numMonths) - 1;
                ratCounter[rat.get_Date().getMonth() + index]++;
            }
            ratCounter[rat.get_Date().getMonth()]++;
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < ratCounter.length; i++) {
            entries.add(new Entry(i, ratCounter[i]));
        }

        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        LineDataSet dataSet = new LineDataSet(entries, "# of Rats");

       // Log.d("APP", "Made data set with : " + entries.size());

        LineData data = new LineData(dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSet.setDrawFilled(true);

        lineChart.setData(data);
        int aniDuration = 5000;
        lineChart.animateY(aniDuration);

        lineChart.getDescription().setText("Rats per Month");

    }

    //    public int[] getTotalArray() {
//        for (RatSightingDataItem rat : rats) {
//            int difference = rat.get_Date().getYear() - rats.get(0).get_Date().getYear();
//            if (difference != 0) {
//                index = difference * 12;
//                ratCounter[rat.get_Date().getMonth() + index]++;
//            }
//            ratCounter[rat.get_Date().getMonth()]++;
//        }
//        return ratCounter;
//    }

//    private List<Entry> convertDataSetToEntry(ArrayList<RatSightingDataItem> rats) {
//        int[] usableArray = getTotalArray();
//        List<Entry> entries = new ArrayList<>();
//        for (RatSightingDataItem rat : rats) {
//            if (rat != null) {
//                entries.add(new Entry(rat.get_Date().getMonth() + 1 + index,
//                        usableArray[rat.get_Date().getMonth()]));
//            } else {
//                entries.add(new Entry(rat.get_Date().getMonth() + 1 + index, 0));
//            }
//        }
//        return entries;
//    }
}

