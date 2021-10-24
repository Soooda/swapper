package com.project.swapper;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Uses "activity_main.xml" as the layout
        setContentView(R.layout.activity_main);
    }
}