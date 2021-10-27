package com.project.swapper.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;

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
                String temp = waps.get(position).BSSID;
                // If it's in the database, prompt delete operation
                if (model.databaseContains(temp)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            NetworkInfoActivity.this);
                    builder.setTitle("Delete from Database")
                            .setMessage("Are you sure to delete this configuration?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    model.databaseDelete(temp);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Nothing happens
                                }
                            });
                    builder.create().show();
                } else {
                    // If it is not in the database
                    Intent intent = new Intent(NetworkInfoActivity.this, AddNetworkActivity.class);
                    if (intent != null) {
                        intent.putExtra("ssid", waps.get(position).SSID);
                        intent.putExtra("bssid", temp);

                        startActivity(intent);
                    }
                }
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
                Intent intent = new Intent(NetworkInfoActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });
    }
}
