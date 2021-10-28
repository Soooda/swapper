package com.project.swapper.view;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.swapper.Model;
import com.project.swapper.R;

import java.util.List;

public class NetworkListAdapter extends ArrayAdapter<ScanResult> {
    private final Context context;
    private List<ScanResult> list;
    private Model model;

    public NetworkListAdapter(Context context, List<ScanResult> list) {
        super(context, R.layout.network_item, list);
        this.list = list;
        this.context = context;
        this.model = Model.getInstance();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        // Ref. https://www.javatpoint.com/android-custom-listview
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.network_item, null, true);

        TextView ssid = (TextView) rowView.findViewById(R.id.SSID);
        TextView bssid = (TextView) rowView.findViewById(R.id.BSSID);
        ImageView connected = (ImageView) rowView.findViewById(R.id.connected);
        ImageView saved = (ImageView) rowView.findViewById(R.id.saved);
        TextView signal = (TextView) rowView.findViewById(R.id.signal);

        String SSID = list.get(position).SSID;
        String BSSID = list.get(position).BSSID;
        int LEVEL = list.get(position).level;

        ssid.setText(SSID);
        bssid.setText(BSSID);
        if (model.networkGetCurrentBSSID() != null && model.networkGetCurrentBSSID().equals(
                BSSID)) {
            connected.setVisibility(View.VISIBLE);
        } else {
            connected.setVisibility(View.INVISIBLE);
        }
        if (model.databaseContains(BSSID)) {
            saved.setVisibility(View.VISIBLE);
        } else {
            saved.setVisibility(View.INVISIBLE);
        }

        signal.setText(LEVEL + "dBm");

        return rowView;
    }
}
