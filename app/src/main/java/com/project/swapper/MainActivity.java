package com.project.swapper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends Activity {
    private Model model;

    private SwitchCompat serviceSwitch;
    private TextView serviceStatus;
    private SwitchCompat batterySwitch;
    private ImageButton wifiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Uses "activity_main.xml" as the layout
        setContentView(R.layout.activity_main);

        // Model Init
        Model.createInstance(this);
        this.model = Model.getInstance();

        // Binds widgets
        serviceSwitch = findViewById(R.id.Switch);
        serviceStatus = findViewById(R.id.service_status);
        batterySwitch = findViewById(R.id.battery);
        wifiButton = findViewById(R.id.wifi);

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
                    model.stopService();
                }
            }
        });

        wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}