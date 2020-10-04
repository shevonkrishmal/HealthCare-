package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteRequiestForm extends AppCompatActivity {

    public Button bdelete, bHelp;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_requiest_form);

        bdelete = (Button) findViewById(R.id.btnDel);
        bHelp = (Button) findViewById(R.id.buttonHelp);

        bHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHp = new Intent(DeleteRequiestForm.this, Need_A_Help.class);
                startActivity(intentHp);
            }
        });

        bdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("RequestInsert").child("patient3");
                dbRef.removeValue();

                Toast.makeText(getApplicationContext(), "Form Deleted Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}