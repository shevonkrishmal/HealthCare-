package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TakeX_Ray extends AppCompatActivity {
    public Button buttonX_ray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_x__ray);

//go to the next page using an intent
        buttonX_ray = (Button) findViewById(R.id.TakeXray);
        buttonX_ray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentX_ray = new Intent(TakeX_Ray.this, X_Ray.class);
                startActivity(intentX_ray);
            }
        });
    }
}