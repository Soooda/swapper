package com.project.swapper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class Password extends AppCompatActivity {

    String networkNameText = "as89dasdnowadsd7s";
    TextView netName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);

        netName = (TextView) findViewById(R.id.networkName);
        netName.setText(networkNameText);

        Button btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Password.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Click listener for join button, check password for specific network
        Button btnJoin = findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set a testing wifi name and password
                String networkSSID = "Niko";
                String networkPass = "123456";

                // Create wifi configuration instance
                WifiConfiguration conf = new WifiConfiguration();
                // Please note the quotes. String should contain ssid in quotes
                conf.SSID = "\"" + networkSSID + "\"";

                // configure the WEP network
                conf.wepKeys[0] = "\"" + networkPass + "\"";
                conf.wepTxKeyIndex = 0;
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

//                // In case for WPA network
//                conf.preSharedKey = "\""+ networkPass +"\"";
//
                // Open network
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

                // Add information to wifi manager
                WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                wifiManager.addNetwork(conf);
//
                // establish connection
                List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                for( WifiConfiguration i : list ) {
                    if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                        wifiManager.disconnect();
                        wifiManager.enableNetwork(i.networkId, true);
                        wifiManager.reconnect();

                        break;
                    }
                }
                System.out.println("Join network succeeded.");
            }
        });
    }

}
