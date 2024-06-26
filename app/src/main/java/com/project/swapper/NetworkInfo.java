package com.project.swapper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class NetworkInfo extends AppCompatActivity {

    String networkNameText = "as89dasdnowadsd7s";
    TextView netName;
    String ipAddressText = "192.168.1.159";
    TextView ipText;
    String subnetMaskText = "255.255.255.0";
    TextView subMask;
    String routerText = "192.168.1.1";
    TextView routerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_info);

        netName = (TextView) findViewById(R.id.networkName);
        netName.setText(networkNameText);

        ipText = (TextView) findViewById(R.id.ipAddress);
        ipText.setText(ipAddressText);

        subMask = (TextView) findViewById(R.id.subnetMask);
        subMask.setText(subnetMaskText);

        routerView = (TextView) findViewById(R.id.router);
        routerView.setText(routerText);

        Button btnForget = findViewById(R.id.btn_forget);

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NetworkInfo.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Forget: " + networkNameText, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }
}