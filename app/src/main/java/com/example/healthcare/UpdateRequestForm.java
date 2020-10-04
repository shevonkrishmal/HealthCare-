package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

public class UpdateRequestForm extends AppCompatActivity {

    public Button btn1;
    EditText txtName, txtID, txtEmail,txtContactNo;
    DatabaseReference dbRef;
    RequestInsert insertData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request_form);

        txtName = findViewById(R.id.EtName);
        txtID = findViewById(R.id.EtID);
        txtEmail = findViewById(R.id.EtEmail);
        txtContactNo = findViewById(R.id.EtContact);
        btn1 = (Button) findViewById(R.id.buttonDone);

        insertData = new RequestInsert();

//go to the next page using an intent
        btn1 = (Button) findViewById(R.id.buttonDone);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateRequestForm.this, DeleteRequiestForm.class);
                startActivity(intent);
            }
        });

    }
}