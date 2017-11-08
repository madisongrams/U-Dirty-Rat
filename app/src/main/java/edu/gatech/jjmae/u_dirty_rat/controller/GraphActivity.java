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
import java.util.Date;
import java.util.List;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;

public class GraphActivity extends AppCompatActivity {
    private ArrayList<RatSightingDataItem> rats;
    public int[] ratcounter;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        if (rats == null) {
//            rats = (ArrayList<RatSightingDataItem>) getIntent().getParcelableExtra("rats");
            Date start = (Date) getIntent().getSerializableExtra("start");
            Date end = (Date) getIntent().getSerializableExtra("end");
            rats = SampleModel.INSTANCE.getRatsByDates(start, end);
            int startYear = start.getYear();
            int endYear = end.getYear();
            int startMonth = start.getMonth();
            int endMonth = end.getMonth();
            ratcounter = new int[((endMonth-startMonth) + ((endYear-startYear)*12)) + 1];
        }
        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = convertDataSetToEntry(rats);

        LineDataSet dataset = new LineDataSet(entries, "# of Rats");

       // Log.d("APP", "Made dataset with : " + entries.size());

        LineData data = new LineData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(5000);

        lineChart.getDescription().setText("Rats per Month");

    }

    private List<Entry> convertDataSetToEntry(ArrayList<RatSightingDataItem> rats) {
       List<Entry> entries = new ArrayList<>();
        int[] usablearray = getTotalArray();
        for (RatSightingDataItem rat : rats) {
            if (rat != null) {
                entries.add(new Entry(rat.get_Date().getMonth() + index, usablearray[rat.get_Date().getMonth()]));
            } else {
                entries.add(new Entry(rat.get_Date().getMonth(), 0));
            }
        }
        return entries;
    }
    public int[] getTotalArray() {
        for (RatSightingDataItem rat : rats) {
            int difference = rat.get_Date().getYear() - rats.get(0).get_Date().getYear();
            if (rat.get_Date() != null) {
            //    if (rat.get_Date().getYear() == rats.get(0).get_Date().getYear()) ;
                    index = difference * 12;
                    ratcounter[rat.get_Date().getMonth() + index]++;
            }
        }
        return ratcounter;
    }

////        for (int i: ratcounter) {
//            for (RatSightingDataItem rat: rats) {
//                if (i+1 == rat.get_Date().getMonth()) {
//                    ratcounter[i]++;
//                }
//            }
////        }
//               return ratcounter;

           }
//       }
//   }


