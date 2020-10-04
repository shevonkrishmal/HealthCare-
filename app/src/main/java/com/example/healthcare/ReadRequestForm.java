package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadRequestForm extends AppCompatActivity {

    public Button btnDeleteF, buttonUpdate;
    DatabaseReference dbRef;
    EditText nameR, idR, emailR, pNoR, pPres;
//    RequestInsert requestinsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_request_form);

//        readRef = FirebaseDatabase.getInstance().getReference().child("RequestInsert");
//
        nameR = (EditText) findViewById(R.id.EtName);
        idR = (EditText) findViewById(R.id.EtID);
        emailR = (EditText) findViewById(R.id.EtEmail);
        pNoR = (EditText) findViewById(R.id.EtContact);
        pPres = (EditText) findViewById(R.id.EtPres);
        buttonUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDeleteF = (Button) findViewById(R.id.btnD);

// Request Form
        btnDeleteF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDelete = new Intent(ReadRequestForm.this, DeleteRequiestForm.class);
                startActivity(intentDelete);
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        String email = intent.getStringExtra("email");
        String pNo = intent.getStringExtra("pNo");
        String presc = intent.getStringExtra("pres");

        nameR.setText(name);
        idR.setText(id);
        emailR.setText(email);
        pNoR.setText(pNo);
        pPres.setText(presc);


        //Update Request Form
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference();
                dbRef.child("RequestInsert").child("patient3").child("name").setValue(nameR.getText().toString().trim());
                dbRef.child("RequestInsert/patient3/id").setValue(idR.getText().toString().trim());
                dbRef.child("RequestInsert/patient3/email").setValue(emailR.getText().toString().trim());
                dbRef.child("RequestInsert/patient3/pNo").setValue(pNoR.getText().toString().trim());
                dbRef.child("RequestInsert/patient3/pres").setValue(pPres.getText().toString().trim());

                Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                //  clearControls();
            }
        });
    }
}
//    private void clearControls() {
//        nameR.setText("");
//        idR.setText("");
//        emailR.setText("");
//        pNoR.setText("");
//    }


//        buttonEdit1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                readRef = FirebaseDatabase.getInstance().getReference().child("RequestInsert").child("patient2");
//                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        if (snapshot.hasChildren()) {
//                            txtName.setText(snapshot.child("name").getValue().toString());
//                            txtID.setText(snapshot.child("id").getValue().toString());
//                            txtEmail.setText(snapshot.child("email").getValue().toString());
//                            txtContactNo.setText(snapshot.child("mobile").getValue().toString());
//                        }
//                        else
//                            Toast.makeText(getApplicationContext(), "Cannot find Patient1", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//        });


//      buttonEdit1 = (Button) findViewById(R.id.btnEdit);
//        requestinsert=new RequestInsert();

//         String name = getIntent().getStringExtra("keyName");
//         String id = getIntent().getStringExtra("keyId");
//         String email = getIntent().getStringExtra("keyEmail");
//         int pNo  = Integer.parseInt(getIntent().getStringExtra("keyMobile"));
//
//         txtName.setText(name);
//         txtID.setText(id);
//         txtEmail.setText(email);
//         txtContactNo.setText(pNo);
