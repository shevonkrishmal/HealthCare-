package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TakeScans extends AppCompatActivity {
    Button buttonScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_scans);

//go to the next page using an intent
        buttonScan = (Button) findViewById(R.id.imagex);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentScan = new Intent(TakeScans.this, Scans.class);
                startActivity(intentScan);
            }
        });

    }
}