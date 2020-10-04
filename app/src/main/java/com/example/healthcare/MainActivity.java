package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
public class MainActivity extends AppCompatActivity {
    public Button button;
    public Button buttonX;
    public Button buttonS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//set intent to the button for go to the next page
        button = (Button) findViewById(R.id.btnLab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LabTest.class);
                startActivity(intent);
            }
        });
        //set intent to the button for go to the next page
        buttonX = (Button) findViewById(R.id.btnX);
        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentx = new Intent(MainActivity.this, TakeX_Ray.class);
                startActivity(intentx);
            }
        });
//set intent to the button for go to the next page
        buttonS = (Button) findViewById(R.id.btnScan);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentS = new Intent(MainActivity.this, TakeScans.class);
                startActivity(intentS);
            }
        });
    }
}