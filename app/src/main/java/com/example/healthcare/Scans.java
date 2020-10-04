package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Scans extends AppCompatActivity {
    public Button buttonS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scans);
//go to the next page using an intent
        buttonS = (Button) findViewById(R.id.buttonTest);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentScans = new Intent(Scans.this, RequestForm.class);
                startActivity(intentScans);
            }
        });
    }
}