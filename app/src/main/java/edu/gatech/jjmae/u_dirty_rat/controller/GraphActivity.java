package edu.gatech.jjmae.u_dirty_rat.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;

public class GraphActivity extends AppCompatActivity {
    private ArrayList<RatSightingDataItem> rats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
 /*       LineChart lineChart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = convertDataSetToEntry(rats);

        LineDataSet dataset = new LineDataSet(entries, "# of Rats");

       // Log.d("APP", "Made dataset with : " + entries.size());

        LineData data = new LineData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        dataset.setDrawFilled(true);

        lineChart.setData(data);
        //lineChart.animateY(5000);

        lineChart.getDescription().setText("Rats per Month");

    }
    private List<Entry> convertDataSetToEntry(ArrayList<RatSightingDataItem> rats) {
       List<Entry> entries = new ArrayList<>();

        for (RatSightingDataItem rat : rats) {
            entries.add(new Entry(5, 1));
        }

        return entries;
 */   }
}


