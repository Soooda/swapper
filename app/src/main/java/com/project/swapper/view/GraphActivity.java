package com.project.swapper.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.project.swapper.Model;
import com.project.swapper.R;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private BarGraphSeries<DataPoint> series;
    private Model model;
    private List<ScanResult> waps;
    private ArrayList<String> wiFiNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph);

        model = Model.getInstance();
        waps = model.networkScan();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new BarGraphSeries<>(generateData());

        graph.addSeries(series);

        series.setTitle("WiFi Strength");

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                int red = 0;
                int green = 0;
                int blue = 0;

                if (data.getY() <= 10) {
                    red = 206;
                    green = 77;
                    blue = 69;
                } else if (data.getY() > 10 && data.getY() < 70) {
                    red = 10;
                    green = 123;
                    blue = 131;
                } else if (data.getY() >= 70) {
                    red = 42;
                    green = 168;
                    blue = 118;
                }

                return Color.rgb(red, green, blue);
            }
        });

        series.setSpacing(10);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLUE);

        String[] wiFis = new String[] {};
        wiFis = wiFiNames.toArray(new String[wiFiNames.size()]);


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(wiFis);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setTextSize(30f);
        graph.getGridLabelRenderer().reloadStyles();
    }

    private DataPoint[] generateData() {
        // Init WiFi names
        wiFiNames = new ArrayList<>();

        int wapsSize = waps.size();
        DataPoint[] values = new DataPoint[wapsSize];

        for (int i = 0; i < waps.size(); i++) {
            int actualLevel = waps.get(i).level;

            /*
                Transform negative values to positive
                transformedLevelVal = 100 - Math.abs(actualLevel);
            */

            int transformedLevelVal = 100 - Math.abs(actualLevel);
            wiFiNames.add(waps.get(i).SSID);

            double x = i;
            double y = (double)transformedLevelVal;

            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }

        return values;
    }

    //Refresh
    public void refresh(View view) {
        wiFiNames = new ArrayList<>();

        waps.clear();
        for (ScanResult s : model.networkScan()) {
            waps.add(s);
        }
    }

}