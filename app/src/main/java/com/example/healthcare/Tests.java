package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tests extends AppCompatActivity {
    Button buttonTets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

//go to the next page using an intent
        buttonTets = (Button) findViewById(R.id.buttonTest);
        buttonTets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTests = new Intent(Tests.this, RequestForm.class);
                startActivity(intentTests);
            }
        });
    }
}