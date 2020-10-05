package com.example.healthcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Doctor_Register extends AppCompatActivity {


    private static String TAG = "tag";
    private EditText fullname,email,password,Phone,gmc,roomnum,adate,atime,dfee,splzn;
    private Button registerbtn;
    private TextView loginbtn;
    //   private ProgressBar progressBar;



    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private FirebaseAuth auth;



    private FirebaseFirestore fstore;

    private ProgressDialog progress;


    private String Password;
    private String Email;
    private  String  fullName;
    private String phone;
    private String url;
    private String DoctorID;
    private String Gmc;
    private  String Roomnum;
    private  String Adate;
    private  String Atime;
    private  String Dfee;
    private  String Specialization;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__register);


        //DB
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("Doctor");



        //Authentication
        auth = FirebaseAuth.getInstance();

        //firestore

        fstore = FirebaseFirestore.getInstance() ;



        // profilePic = findViewById(R.id.profilePic);
        fullname =  findViewById(R.id.fullName);
        email =  findViewById(R.id.Email);
        password = findViewById(R.id.password);
        Phone =  findViewById(R.id.phone);
        registerbtn =  findViewById(R.id.registerBtn);
        loginbtn =  findViewById(R.id.createText);
        // progressBar = findViewById(R.id.progressBar);
        gmc = findViewById(R.id.GMC);
        roomnum = findViewById(R.id.roomnum);
        adate= findViewById(R.id.Adate);
        atime = findViewById(R.id.Atime);
        dfee  = findViewById(R.id.channelingfee);
        splzn = findViewById(R.id.Specialization);

        /*if (auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/




        registerbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(email.getText().toString() != null && password.getText().toString() != null) {
                    ConDoctor();
                }

            }
        });

    }



    private void ConDoctor(){


        final String  Email = email.getText().toString();
        final String Password = password.getText().toString();
        final String  fullName = fullname.getText().toString();
        final String   phone = Phone.getText().toString();
        final String   Gmc = gmc.getText().toString();
        final String   Atime = atime.getText().toString();
        final String   Adate = adate.getText().toString();
        final String   Roomnum = roomnum.getText().toString();
        final String   Dfee = dfee.getText().toString();
        final String   Specialization = splzn.getText().toString();

        // url = imageUri.toString();

        if(TextUtils.isEmpty(Email)){
            email.setError("Email is Required.");
            return;
        }

        if(TextUtils.isEmpty(Password)){
            password.setError("Password is Required.");
            return;
        }

        if(password.length() < 6){
            password.setError("Password Must be >= 6 Characters");
            return;
        }

        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(Doctor_Register.this,"user created", Toast.LENGTH_LONG).show();
                            DoctorID = auth.getCurrentUser().getUid();

                            DocumentReference documentReference = fstore.collection("Doctors").document(DoctorID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fullname",fullName);
                            user.put("email",Email);
                            user.put("phone",phone);
                            user.put("gmc",Gmc);
                            user.put("room number",Roomnum);
                            user.put("Appointmentdate",Adate);
                            user.put("Appointmenttime",Atime);
                            user.put("Doctorfee",Dfee);
                            user.put("Specialization",Specialization);

                            //realtime db
                            mDatabase.child(DoctorID).setValue(user);


                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                            // FirebaseUser doctor = auth.getCurrentUser();
                            //  updateUI(doctor);
                            startActivity(new Intent(getApplicationContext(),Doctor_Login.class));

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Doctor_Register.this,"Error" + task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }


                    }

                });


    }



 /*   private void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(doctor); //adding user info to database
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }
*/

}

