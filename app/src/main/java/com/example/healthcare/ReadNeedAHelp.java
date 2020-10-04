package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReadNeedAHelp extends AppCompatActivity {

    public Button btnUpdate, BtnDel;
    DatabaseReference dbRef;
    EditText nameH, emailH, contactH, msgH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_need_a_help);

        nameH = (EditText) findViewById(R.id.cname);
        emailH = (EditText) findViewById(R.id.cemail);
        contactH = (EditText) findViewById(R.id.cNo);
        msgH = (EditText) findViewById(R.id.cmsg);

        btnUpdate = (Button) findViewById(R.id.btnPayment);
        BtnDel = (Button) findViewById(R.id.btnEDT);

        //update the details
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference();
                dbRef.child("NeedHelp/Help2/name").setValue(nameH.getText().toString().trim());
                dbRef.child("NeedHelp/Help2/email").setValue(emailH.getText().toString().trim());
                dbRef.child("NeedHelp/Help2/pNo").setValue(contactH.getText().toString().trim());
                dbRef.child("NeedHelp/Help2/message").setValue(msgH.getText().toString().trim());

                Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
            }
        });

        //go to the next page
        BtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intentDel = new Intent(ReadNeedAHelp.this, DeleteNeedAHelp.class);
                    startActivity(intentDel);
            }
        });

        //retrieve the data from the form
        Intent intentRead = getIntent();
        String name = intentRead.getStringExtra("name");
        String email = intentRead.getStringExtra("email");
        String pNo = intentRead.getStringExtra("pNo");
        String message = intentRead.getStringExtra("message");

        nameH.setText(name);
        emailH.setText(email);
        contactH.setText(pNo);
        msgH.setText(message);

//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ReadNeedAHelp.this, UpdateNeedAHelp.class);
//                startActivity(intent);
//            }
//        });
    }
}