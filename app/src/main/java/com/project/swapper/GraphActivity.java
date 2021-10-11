package com.project.swapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Random;

public class GraphActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    private BarGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new BarGraphSeries<>(generateData());

        graph.addSeries(series);

        series.setTitle("WiFi Strength");

        //graph.getGridLabelRenderer().setHorizontalAxisTitle("WiFis");
        //graph.getGridLabelRenderer().setVerticalAxisTitle("Strength");

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                int red = 0;
                int green = 0;
                int blue = 0;

                if (data.getY() <= 5) {
                    red = 255;
                } else if (data.getY() > 5 && data.getY() <= 7) {
                    blue = 255;
                } else if (data.getY() > 7) {
                    green = 255;
                }

                return Color.rgb(red, green, blue);
            }
        });

        series.setSpacing(10);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLUE);
        //series.setValuesOnTopSize(50);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"WIFI_A", "WIFI_B", "WIFI_C", "WIFI_D", "WIFI_E"});
        //graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setTextSize(30f);
        graph.getGridLabelRenderer().reloadStyles();
    }

    private DataPoint[] generateData() {
        int count = 5;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double y = mRand.nextInt(10) + 1;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    Random mRand = new Random();

    private Runnable mTimer = new Runnable() {
        @Override
        public void run() {
            series.resetData(generateData());
            mHandler.postDelayed(this, 1000);
        }
    };

    public void startSignal(View view) {
        mHandler.postDelayed(mTimer, 1000);
    }

    public void stopSignal(View view) {
        mHandler.removeCallbacks(mTimer);
    }


}