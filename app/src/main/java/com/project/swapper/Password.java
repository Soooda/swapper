package com.project.swapper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    }

}
