package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LabTest extends AppCompatActivity {
    public Button buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        buttonTest = (Button) findViewById(R.id.TakeLab);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTest = new Intent(LabTest.this, Tests.class);
                startActivity(intentTest);
            }
        });
    }
}