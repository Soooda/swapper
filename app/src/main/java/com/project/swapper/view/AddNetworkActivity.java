package com.project.swapper.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.project.swapper.Model;
import com.project.swapper.R;

public class AddNetworkActivity extends Activity {
    private TextView ssid;
    private EditText password;
    private Button confirm;
    private Model model;

    private String SSID;
    private String BSSID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Uses "activity_networkinfo.xml" as the layout
        setContentView(R.layout.activity_addnetwork);

        model = Model.getInstance();

        ssid = findViewById(R.id.saved_ssid);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);

        SSID = getIntent().getStringExtra("ssid");
        BSSID = getIntent().getStringExtra("bssid");
        ssid.setText(SSID);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().equals("")) {
                    password.setHint("Please enter the password!");
                } else {
                    model.databaseAdd(BSSID, password.getText().toString());
                    finish();
                }
            }
        });
    }
}
