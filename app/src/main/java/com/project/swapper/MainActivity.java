package com.project.swapper;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    String[] networkName = {"networkName 1", "networkName 2", "networkName 3", "networkName 4", "networkName 5"};

    boolean connectionStatus = false;
    boolean storedInDB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnGraphView = findViewById(R.id.graph_view);

        ListView resultsListView = (ListView) findViewById(R.id.results_listView);

        resultsListView.setAdapter(NetworkList(networkName));

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