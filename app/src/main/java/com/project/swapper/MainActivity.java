package com.project.swapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * The main screen activity when the user opens this application.
 */
public class MainActivity extends AppCompatActivity {
    // WIFI Manager
    private WifiManager mWifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Uses "activity_main.xml" as the layout
        setContentView(R.layout.activity_main);

        // Attains the wifi manager
        mWifiManager = (WifiManager)
                this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

//        List<ScanResult> results = mWifiManager.getScanResults();

        Button btnGraphView = findViewById(R.id.graph_view);

        btnGraphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });
    }
}