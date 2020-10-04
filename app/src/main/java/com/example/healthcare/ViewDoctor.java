package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewDoctor extends AppCompatActivity {

    Button dHeartBtn,dEntBtn,dBrainBtn,dVogBtn,dOpdBtn,dEyeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);


        dBrainBtn = findViewById(R.id.brainBtn);
        dEntBtn = findViewById(R.id.entBtn);
        dEyeBtn = findViewById(R.id.eyeBtn);
        dHeartBtn = findViewById(R.id.heartBtn);
        dOpdBtn = findViewById(R.id.opdBtn);
        dVogBtn = findViewById(R.id.vogBtn);




        dBrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Brain.class));
            }
        });

        dEyeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Eye.class));
            }
        });

        dEntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Ent.class));
            }
        });

        dVogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Vog.class));
            }
        });

        dHeartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),heart.class));
            }
        });

        dOpdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Opd.class));
            }
        });
    }
}