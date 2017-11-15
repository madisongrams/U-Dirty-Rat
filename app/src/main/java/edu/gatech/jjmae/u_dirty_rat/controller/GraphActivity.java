package edu.gatech.jjmae.u_dirty_rat.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        int numMonths = 12;
        Date start = (Date) getIntent().getSerializableExtra("start");
        Date end = (Date) getIntent().getSerializableExtra("end");
        boolean oneMonth = false;
        if (rats == null) {
            int monthGap = (end.getMonth() - start.getMonth()) + 1;
            int yearGap = numMonths * (end.getYear() - start.getYear());

            if (end.getMonth() < start.getMonth()) { // can only happen when dates are in
                // different years
                monthGap = (11 - start.getMonth()) + end.getMonth() + 2; //a couple of notes on this
                // if start month = December (11), endMonth = March (2) for example
                // we need a size of 4 months (dec, jan, feb, march)
                // so 11 - 11 = 0 (number of months left in start year)
                // (january = 0 for dates bc java decided that's a good idea)
                // 0 + 2 = 2 (adding month for end year)
                // 2 + 2 = 4 (buffer to get right amount of months)
                yearGap = yearGap - 12;
            }

            if ((monthGap == 1) && (yearGap == 0)) { // if only 1 month, display rats by day
                int dayGap = (end.getDate() - start.getDate()) + 1;
                ratCounter = new int[dayGap];
                oneMonth = true;
            } else {
                ratCounter = new int[monthGap + yearGap];
            }

            rats = SampleModel.INSTANCE.getRatsByDates(start, end);
            Collections.sort(rats);
        }
        List<String> labels = new ArrayList<String>(ratCounter.length);
        if (!oneMonth) { // displaying rats by month
            int curMonth = start.getMonth();
            int curYear = start.getYear();
            int counterIndex = 0;
            labels.add((curMonth + 1) + "/" + Integer.toString(curYear).substring(1, 3));
            // x-axis shows mm/yy

            for (RatSightingDataItem rat : rats) {
                Date ratDate = rat.get_Date();
                while ((ratDate.getMonth() != curMonth) || (ratDate.getYear() != ratDate.getYear())) {
                    // how we can populate the arrays is by incrementing months and indices
                    // until we are at the correct one for this rat
                    counterIndex++;
                    curMonth++;
                    if (curMonth > 11) { // new year!
                        curMonth = 0;
                        curYear++;
                    }
                    labels.add((curMonth + 1) + "/" + Integer.toString(curYear).substring(1, 3));
                }
                ratCounter[counterIndex]++;
            }
        } else { // displaying rats by day...
            int curDay = start.getDate();
            int counterIndex = 0;
            labels.add((start.getMonth() + 1) + "/" + curDay); // x-axis shows mm/dd

            for (RatSightingDataItem rat : rats) {
                Date ratDate = rat.get_Date();
                while (ratDate.getDate() != curDay) {
                    counterIndex++;
                    curDay++;
                    labels.add((start.getMonth() + 1) + "/" + curDay);
                }
                ratCounter[counterIndex]++;
            }
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < ratCounter.length; i++) {
            entries.add(new Entry(i, ratCounter[i]));
        }
        final List<String> finalLabels = labels;
        IAxisValueFormatter formatter = new IAxisValueFormatter() { // this will format the x-axis
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return finalLabels.get((int) value);
            }
        };

        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        LineDataSet dataSet = new LineDataSet(entries, "# of Rats");

        LineData data = new LineData(dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSet.setDrawFilled(true);

        lineChart.setData(data);
        int aniDuration = 5000;
        lineChart.animateY(aniDuration);

        if (oneMonth) {
            lineChart.getDescription().setText("Rat Sightings per Day");
        } else {
            lineChart.getDescription().setText("Rat Sightings per Month");
        }

    }

}

