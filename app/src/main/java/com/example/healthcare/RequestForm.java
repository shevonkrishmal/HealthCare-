package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestForm extends AppCompatActivity {

    //create variables
    Button buttonDone, buttonEdit, btnHelp;
    EditText txtName, txtID, txtEmail,txtContactNo, txtPres;
    DatabaseReference dbRef;
    RequestInsert requestinsert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

//connect to the firebase
        dbRef = FirebaseDatabase.getInstance().getReference().child("RequestInsert");

        txtName = (EditText) findViewById(R.id.EtName);
        txtID = (EditText)findViewById(R.id.EtID);
        txtEmail = (EditText)findViewById(R.id.EtEmail);
        txtContactNo = (EditText)findViewById(R.id.EtContact);
        txtPres = (EditText) findViewById(R.id.EtPres);
        buttonDone = (Button) findViewById(R.id.buttonDone);
        buttonEdit = (Button) findViewById(R.id.btnEdit);
        requestinsert=new RequestInsert();

        btnHelp = (Button) findViewById(R.id.buttonHelp);

        //go to the NeedAHelp page
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the next page using an Intent
                Intent intentH = new Intent(RequestForm.this, Need_A_Help.class);
                startActivity(intentH);
            }
        });

        //retrieve Data
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intentHelp = new Intent(RequestForm.this, ReadRequestForm.class);
//                startActivity(intentHelp);

                dbRef = FirebaseDatabase.getInstance().getReference().child("RequestInsert").child("patient3");

                String name =txtName.getText().toString();
                String id =txtID.getText().toString();
                String email =txtEmail.getText().toString();
                String pNo= txtContactNo.getText().toString();
                String prescription = txtPres.getText().toString();

                Intent intentHelp = new Intent(RequestForm.this, ReadRequestForm.class);
                intentHelp.putExtra("name", name);
                intentHelp.putExtra("id", id);
                intentHelp.putExtra("email", email);
                intentHelp.putExtra("pNo", pNo);
                intentHelp.putExtra("pres",prescription);
               // intentHelp.putExtra("RequestInsert", patient1.getText());
//                intentHelp.putExtra("keyId", txtID.getText());
//                intentHelp.putExtra("keyEmail", txtEmail.getText());
//                intentHelp.putExtra("keyMobile", txtContactNo.getText());
                startActivity(intentHelp);

            }
        });


        //insert data
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtID.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an ID", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an Email", Toast.LENGTH_SHORT).show();
                    else{

                        //
//                        String name =(txtName.getText().toString().trim());
//                        String id =(txtID.getText().toString().trim());
//                        String email =(txtEmail.getText().toString().trim());
                        int pNo=(Integer.parseInt(txtContactNo.getText().toString().trim()));
//                        String prescription = (txtPres.getText().toString().trim());
                        //
                        requestinsert.setName(txtName.getText().toString().trim());
                        requestinsert.setEmail(txtEmail.getText().toString().trim());
                        requestinsert.setId(txtID.getText().toString().trim());
                        requestinsert.setMobile(pNo);
                        requestinsert.setPresc(txtPres.getText().toString().trim());
                        //dbRef.push().setValue(requestinsert);

                        dbRef.child("patient3").setValue(requestinsert);
                    }

                     if(!txtName.getText().toString().matches("[a-z,A-Z]*")){
                        txtName.setError("Enter only Characters!");
                    }

                    else if(!txtID.getText().toString().matches("[0-9]")){
                        txtID.setError("ID is Required");
                    }

                    else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()){
                        txtEmail.setError("Please Enter Valid Email Address!");
                    }

                    else if(!txtContactNo.getText().toString().matches("[0-9]{10}")) {
                        txtContactNo.setError("Phone Number Must Be 10 numbers");
                    }

                    else if(!txtPres.getText().toString().matches("[a-z,A-Z]*")){
                        txtPres.setError("Enter only Characters!");
                    }
                    //feedback to the Patient
                    Toast.makeText(getApplicationContext(), "Data Saved Successfully!!", Toast.LENGTH_SHORT).show();


                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
//    private void clearControls() {
//        txtName.setText("");
//        txtID.setText("");
//        txtEmail.setText("");
//        txtContactNo.setText("");
//    }

}