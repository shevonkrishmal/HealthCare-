package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteNeedAHelp extends AppCompatActivity {

    public Button btndelete, btnGo;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_need_a_help);

        btndelete = (Button) findViewById(R.id.btnDel);
        btnGo = (Button) findViewById(R.id.btnLb);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGo = new Intent(DeleteNeedAHelp.this, MainActivity.class);
                startActivity(intentGo);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("NeedHelp").child("Help2");
                dbRef.removeValue();

                Toast.makeText(getApplicationContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}