package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class X_Ray extends AppCompatActivity {
    Button buttonX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x__ray);

//go to the next page using an intent
        buttonX = (Button) findViewById(R.id.buttonTest);
        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentxray = new Intent(X_Ray.this, RequestForm.class);
                startActivity(intentxray);
            }
        });
    }
}