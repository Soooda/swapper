package com.project.swapper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class Password extends AppCompatActivity {

    String networkNameText = "as89dasdnowadsd7s";
    TextView netName;
    SwitchCompat autoJoinValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);

        netName = (TextView) findViewById(R.id.networkName);
        netName.setText(networkNameText);

        autoJoinValue = (SwitchCompat) findViewById(R.id.sw_auto_join);
        autoJoinValue.setShowText(false);

        //SwitchCompat swAutoJoin = findViewById(R.id.sw_auto_join);

        autoJoinValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Password.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Auto Join: " + autoJoinValue, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        Button btnBack = findViewById(R.id.btn_cancel);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Password.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
