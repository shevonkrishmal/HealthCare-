package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GetAppoinment extends AppCompatActivity {

    public static final String TAG = "TAG";

    FirebaseFirestore fStore;

    EditText aFullName,aNic,aPhone,aEmail,aDate,aTime,aPassword;
    Button aBookBtn;

    FirebaseAuth fAuth;
    ProgressBar progressBar;
    String addID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_appoinment);



        aFullName = findViewById(R.id.fullName);
        aNic = findViewById(R.id.nic);
        aPhone = findViewById(R.id.phone);
        aEmail = findViewById(R.id.email);
        aDate = findViewById(R.id.date);
        aTime=findViewById(R.id.time);
        aPassword = findViewById(R.id.password);
        aBookBtn = findViewById(R.id.bookBtn);




        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressBar);





        aBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = aEmail.getText().toString().trim();
                String password = aPassword.getText().toString().trim();
                final String fullName = aFullName.getText().toString();
                final String nic = aNic.getText().toString();
                final String phone = aPhone.getText().toString();
                final String time = aTime.getText().toString();
                final String date = aDate.getText().toString();



                if(TextUtils.isEmpty(email)){
                    aEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    aPassword.setError("Password is Required.");
                    return;
                }


                if(password.length() < 8 ){
                    aPassword.setError("Password Must be more than 8 characters and less than 15 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                //register the patients in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(GetAppoinment.this, " Registered.", Toast.LENGTH_SHORT).show();
                            addID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("addvertiesement").document(addID);
                            Map<String,Object> user = new HashMap<>();

                            user.put("fullName",fullName);
                            user.put("nic",nic);
                            user.put("phone",phone);
                            user.put("email",email);
                            user.put("date",date);
                            user.put("time",time);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+ addID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), patientLogin.class));

                        } else {
                            Toast.makeText(GetAppoinment.this, "Patient is not registered!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }


                });
            }
        });


        aBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Appoinment.class));
            }
        });


    }


}