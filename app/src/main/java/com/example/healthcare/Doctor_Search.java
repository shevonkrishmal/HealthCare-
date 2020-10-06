package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Doctor_Search extends AppCompatActivity {


    public static final String EXTRA_TEXTDOCTOR = "SearchDoctor";
    EditText input;
    ImageButton buttonSearch;
    private RecyclerView Doctorlist;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private TextView SpecializationSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonSearch = (ImageButton) findViewById(R.id.imgBtnSearch);
        input = (EditText) findViewById(R.id.tfSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Specialization = input.getText().toString();
                Log.i("searchwindow", Specialization);

               Intent intent = new Intent(Doctor_Search.this, Doctor_resultList.class);
                intent.putExtra(EXTRA_TEXTDOCTOR, Specialization);
                startActivity(intent);

            }
        });

    }
}