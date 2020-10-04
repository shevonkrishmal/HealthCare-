package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import org.w3c.dom.Text;

import javax.annotation.RegEx;

public class Need_A_Help extends AppCompatActivity {

    public Button btnHlp, btnEdt;
    EditText tName, tEmail, tCntact, tMsg;
    DatabaseReference dbRef;
    NeedHelp needHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need__a__help);



        tName = (EditText) findViewById(R.id.cname);
        tEmail = (EditText) findViewById(R.id.cemail);
        tCntact = (EditText) findViewById(R.id.cNo);
        tMsg = (EditText) findViewById(R.id.cmsg);
        needHP = new NeedHelp();

        btnHlp = (Button) findViewById(R.id.btnPayment);
        btnEdt = (Button) findViewById(R.id.btnED);

        btnHlp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("NeedHelp");
                try{
                    if (TextUtils.isEmpty(tName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter your Name!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(tEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter your Email Address!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(tCntact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter your Contact Number!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(tMsg.getText()))
                        Toast.makeText(getApplicationContext(), "Please Enter your Message!", Toast.LENGTH_SHORT).show();
                    else{

                        String name = (tName.getText().toString().trim());
                        String email = (tEmail.getText().toString().trim());
                        int pNo = (Integer.parseInt(tCntact.getText().toString().trim()));
                        String message = (tMsg.getText().toString().trim());

                        needHP.setNameH(tName.getText().toString().trim());
                        needHP.setEmailH(tEmail.getText().toString().trim());
                        needHP.setContactH(pNo);
                        needHP.setMsgH(tMsg.getText().toString().trim());
                        //dbRef.push().setValue(needHP);
                        dbRef.child("Help2").setValue(needHP);
                    }

                    //feedback to the patient
                    Toast.makeText(getApplicationContext(), "Your Message sent to the Admin", Toast.LENGTH_SHORT).show();
                }catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }

                if(!tName.getText().toString().matches("[a-z,A-Z]*")){
                    tName.setError("Enter only Characters!");
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(tEmail.getText().toString()).matches()){
                    tEmail.setError("Please Enter Valid Email Address!");
                }

                else if(!tCntact.getText().toString().matches("[0-9]{10}")) {
                    tCntact.setError("Phone Number Must Be 10 numbers");

                }
                else if (!tMsg.getText().toString().matches("[a-z,A-Z]*")) {
                    tName.setError("Enter only Characters!");
                }

            }
        });

        btnEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("NeedHelp").child("Help2");

                String name = tName.getText().toString();
                String email = tEmail.getText().toString();
                String pNo = tCntact.getText().toString();
                String message = tMsg.getText().toString();

                Intent intentHL = new Intent(Need_A_Help.this, ReadNeedAHelp.class);
                intentHL.putExtra("name", name);
                intentHL.putExtra("email", email);
                intentHL.putExtra("pNo", pNo);
                intentHL.putExtra("message", message);

                startActivity(intentHL);

            }
        });
//        btnHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Need_A_Help.this, ReadNeedAHelp.class);
//                startActivity(intent);
//            }
//        });
    }
}
