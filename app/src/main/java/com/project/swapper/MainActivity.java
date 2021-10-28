package com.project.swapper;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.project.swapper.security.Network;
import com.project.swapper.security.NetworkListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    Network network1 = new Network("network1", true, true, 0);
    Network network2 = new Network("network2", false, false, 0);
    Network network3 = new Network("network3", false, true, 0);
    Network network4 = new Network("network4", false, true, 0);
    Network network5 = new Network("network5", false, false, 0);
    Network network6 = new Network("network6", false, true, 0);
    Network network7 = new Network("network7", false, true, 0);
    Network network8 = new Network("network8", false, true, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnGraphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
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