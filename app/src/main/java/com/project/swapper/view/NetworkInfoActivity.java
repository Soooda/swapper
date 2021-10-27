package com.project.swapper.view;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.project.swapper.view.GraphActivity;
import com.project.swapper.Model;
import com.project.swapper.R;

import java.util.List;

public class NetworkInfoActivity extends Activity {
    private List<ScanResult> waps;
    private Model model;

    private ListView list;
    private ImageButton refresh;
    private ImageButton graph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Uses "activity_networkinfo.xml" as the layout
        setContentView(R.layout.activity_networkinfo);

        model = Model.getInstance();
        waps = model.networkScan();

        list = findViewById(R.id.network_list);
        refresh = findViewById(R.id.refresh);
        graph = findViewById(R.id.graph);

        NetworkListAdapter adapter = new NetworkListAdapter(this, waps);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waps.clear();
                for (ScanResult s : model.networkScan()) {
                    waps.add(s);
                }
                adapter.notifyDataSetChanged();
            }
        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(intent);
            }
        });
    }
}
