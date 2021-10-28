package com.project.swapper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

<<<<<<< HEAD
import androidx.appcompat.widget.SwitchCompat;
=======
import com.project.swapper.security.Network;
import com.project.swapper.security.NetworkListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
>>>>>>> main

import com.project.swapper.view.NetworkInfoActivity;

<<<<<<< HEAD
public class MainActivity extends Activity {
    private Model model;

    private SwitchCompat serviceSwitch;
    private TextView serviceStatus;
    private SwitchCompat batterySwitch;
    private ImageButton wifiButton;
=======
    Network network1 = new Network("network1", true, true, 0);
    Network network2 = new Network("network2", false, false, 0);
    Network network3 = new Network("network3", false, true, 0);
    Network network4 = new Network("network4", false, true, 0);
    Network network5 = new Network("network5", false, false, 0);
    Network network6 = new Network("network6", false, true, 0);
    Network network7 = new Network("network7", false, true, 0);
    Network network8 = new Network("network8", false, true, 0);
>>>>>>> main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Uses "activity_main.xml" as the layout
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        // Model Init
        Model.createInstance(this);
        this.model = Model.getInstance();

        // Binds widgets
        serviceSwitch = findViewById(R.id.Switch);
        serviceStatus = findViewById(R.id.service_status);
        batterySwitch = findViewById(R.id.battery);
        wifiButton = findViewById(R.id.wifi);
=======

        ImageButton btnGraphView = findViewById(R.id.graph_view);

        ListView resultsListView = (ListView) findViewById(R.id.results_listView);
        ArrayList<Network> NetworkList = new ArrayList<>();
        NetworkList.add(network1);
        NetworkList.add(network2);
        NetworkList.add(network3);
        NetworkList.add(network4);
        NetworkList.add(network5);
        NetworkList.add(network6);
        NetworkList.add(network7);
        NetworkList.add(network8);
        NetworkListAdapter adapter = new NetworkListAdapter(this, R.layout.list_item, NetworkList);
        resultsListView.setAdapter(adapter);
>>>>>>> main

        // If the service is already running
        if (model.isServiceRunning()) {
            serviceStatus.setText("The service is running!");
            serviceStatus.setTextColor(Color.GREEN);
            serviceSwitch.setChecked(true);
        } else {
            serviceStatus.setText("The service is stopped!");
            serviceStatus.setTextColor(Color.RED);
            serviceSwitch.setChecked(false);
        }
        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // If WIFI is disabled
                    if (!model.networkIsConnectedToWIFI()) {
                        serviceStatus.setText("WIFI is disabled!");
                        serviceStatus.setTextColor(Color.LTGRAY);
                        serviceSwitch.setChecked(false);
                        return;
                    }

                    serviceStatus.setText("The service is running!");
                    serviceStatus.setTextColor(Color.GREEN);
                    // Runs service based on battery mode
                    model.startService(batterySwitch.isChecked());
                } else {
                    serviceStatus.setText("The service is stopped!");
                    serviceStatus.setTextColor(Color.RED);
                    model.stopService(batterySwitch.isChecked());
                }
            }
        });

        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        NetworkInfoActivity.class);
                startActivity(intent);
            }
        });
    }
<<<<<<< HEAD
=======

>>>>>>> main
}