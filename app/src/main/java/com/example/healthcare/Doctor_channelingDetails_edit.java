package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Doctor_channelingDetails_edit extends AppCompatActivity {
    public static final String TAG = "TAG";


    private TextView E_Roomnum,E_Atime,E_Adate,E_Dfee,profileEmail;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private String DoctorID;
    private FirebaseUser doctor;
    private Button saveBtn;


    private FirebaseDatabase database;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_channeling_details_edit);

        Intent data = getIntent();

        final String roomnum = data.getStringExtra("room number");
        String Atime = data.getStringExtra("Appointmenttime");
        String Adate = data.getStringExtra("Appointmentdate");
        String channelingfee = data.getStringExtra("Doctorfee");
        String email = data.getStringExtra("email");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        doctor = fAuth.getCurrentUser();

        E_Roomnum = findViewById(R.id.roomnum);
        E_Adate = findViewById(R.id.Adate);
        E_Atime =findViewById(R.id.Atime);
        E_Dfee = findViewById(R.id.channelingfee);
        profileEmail = findViewById(R.id.Email);

        saveBtn = findViewById(R.id.saveProfileInfo);


        DoctorID = fAuth.getCurrentUser().getUid();
        doctor = fAuth.getCurrentUser();


        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("Doctor");

        final DocumentReference documentReference = fStore.collection("Doctors").document(DoctorID);

        documentReference.addSnapshotListener(Doctor_channelingDetails_edit.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                E_Roomnum.setText(documentSnapshot.getString("room number"));
                E_Adate.setText(documentSnapshot.getString("Appointmentdate"));
                E_Atime.setText(documentSnapshot.getString("Appointmenttime"));
                E_Dfee.setText(documentSnapshot.getString("Doctorfee"));
                profileEmail.setText(documentSnapshot.getString("email"));


            }
        });











        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = profileEmail.getText().toString();
                doctor.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("Doctors").document(doctor.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("room number",E_Roomnum.getText().toString());
                        edited.put("Appointmentdate",E_Adate.getText().toString());
                        edited.put("Appointmenttime",E_Atime.getText().toString());
                        edited.put("Doctorfee",E_Dfee.getText().toString());

                        mDatabase.child(DoctorID).updateChildren(edited);

                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Doctor_channelingDetails_edit.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Doctor_channelingDetails.class));
                                finish();
                            }
                        });
                       // Toast.makeText(Doctor_channelingDetails_edit.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Doctor_channelingDetails_edit.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        profileEmail.setText(email);
        E_Roomnum.setText(roomnum);
        E_Adate.setText(Adate);
        E_Atime.setText(Atime);
        E_Dfee.setText(channelingfee);

        Log.d(TAG, "onCreate: " + email +" "+ roomnum + " " + Adate + " " + Atime+ " "+ channelingfee );












    }
}