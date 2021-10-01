package com.project.swapper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NetworkInfo extends AppCompatActivity {

    private static final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate:NetworkInfo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_info);
    }
}