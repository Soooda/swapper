package com.project.swapper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.project.swapper.network.AutoSwitchingService;
import com.project.swapper.security.EncryptionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private Model model;
    private WifiManager wifi;

    String[] networkName = {"networkName 1", "networkName 2", "networkName 3", "networkName 4", "networkName 5"};

    boolean connectionStatus = false;
    boolean storedInDB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnGraphView = findViewById(R.id.graph_view);

        ListView resultsListView = (ListView) findViewById(R.id.results_listView);

        resultsListView.setAdapter(NetworkList(networkName));

        TextView text = findViewById(R.id.toolbar_title);

        model = new Model(this);
//        model.databaseAdd("fc:d7:33:25:71:1b", "kxg123456");
//        model.databaseAdd("d4:9e:05:9f:fd:8f", "cachpeti");
//        wifi.removeNetwork(126);
//        wifi.removeNetwork(0);

        btnGraphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.startService();
            }
        });

        ImageButton btnSearch = findViewById(R.id.Search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.isServiceRunning()) {
                    text.setText("Running");
                } else {
                    text.setText("Stopped");
                }
                List<WifiConfiguration> configs = wifi.getConfiguredNetworks();
                for (WifiConfiguration w : configs) {
                    System.out.println(w.networkId + " " + w.SSID + " " + w.BSSID);
                }
            }
        });

        ImageButton btnSetting = findViewById(R.id.Settings);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.stopService();
            }
        });
    }

    public SimpleAdapter NetworkList(String[] networkName){
        HashMap<String, String> name = new HashMap<>();
        for(int i = 0; i < networkName.length; i ++){
            name.put("network " + (i+1), "name: " + networkName[i]);
        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"}, new int[]{R.id.text1, R.id.text2});
        Iterator it = name.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        return adapter;
    }


}