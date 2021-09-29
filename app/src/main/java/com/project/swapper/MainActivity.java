package com.project.swapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

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
    }
}