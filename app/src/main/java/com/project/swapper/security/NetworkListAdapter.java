package com.project.swapper.security;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.swapper.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NetworkListAdapter extends ArrayAdapter<Network> {

    private Context mContext;
    int mResource;

    int count = 0;

    public NetworkListAdapter(@NonNull Context context, int resource, ArrayList<Network> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        count += 1;

        String networkName = getItem(position).getNetworkName();
        Boolean StoredInDB = getItem(position).isStoredInDB();
        Boolean connectionStatus = getItem(position).isConnectionStatus();
        int Strength = getItem(position).getNetworkStrength();

        Network network = new Network(networkName, StoredInDB, connectionStatus, Strength);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvNetworkList = (TextView) convertView.findViewById(R.id.text1);
        TextView tvNetworkName = (TextView) convertView.findViewById(R.id.text2);
        ImageView tvStoredInDB = (ImageView) convertView.findViewById(R.id.storedInDB);
        ImageView tvConnected = (ImageView) convertView.findViewById(R.id.Connected);
        TextView tvStrength = (TextView) convertView.findViewById(R.id.Strength);

        tvNetworkList.setText("Network " + count);
        tvNetworkName.setText("name: " + networkName);

        if(StoredInDB == true){
            tvStoredInDB.setImageResource(R.drawable.ic_baseline_verified_user_24);
        }

        if(connectionStatus == true){
            tvConnected.setImageResource(R.drawable.ic_baseline_wifi_24);
        }

        tvStrength.setText(String.valueOf(Strength));


            return convertView;
    }
}
