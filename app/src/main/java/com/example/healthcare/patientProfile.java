package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class patientProfile extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023;
    TextView fullName,address,age,nic,phone,email,dob;
    String userID;
    ImageView profileImage;
    Button changeProfileImage, doctorBtn , testBtn, AddBtn;

    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        fullName = findViewById(R.id.fullName);
        address = findViewById(R.id.address);
        age = findViewById(R.id.age);
        nic = findViewById(R.id.nic);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);

        doctorBtn = findViewById(R.id.docBtn);
        testBtn = findViewById(R.id.testBtn);
        AddBtn = findViewById(R.id.addBtn);

        profileImage = findViewById(R.id.propic);
        changeProfileImage = findViewById(R.id.cp);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("patient/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        userID = fAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fStore.collection( "patient").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if(documentSnapshot.exists()) {
                    fullName.setText(documentSnapshot.getString("fullName"));
                    address.setText(documentSnapshot.getString("address"));
                    age.setText(documentSnapshot.getString("age"));
                    nic.setText(documentSnapshot.getString("nic"));
                    phone.setText(documentSnapshot.getString("phone"));
                    email.setText(documentSnapshot.getString("email"));
                    dob.setText(documentSnapshot.getString("dob"));
                }else{
                    Log.d("tag","onEvent: Document do not exists");
                }
            }

        });


        changeProfileImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // open gallery
                //Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(openGalleryIntent,1000);
                Intent i = new Intent(view.getContext(),EditProfile.class);
                i.putExtra("fullName",fullName.getText().toString());
                i.putExtra("address",address.getText().toString());
                i.putExtra("age",age.getText().toString());
                i.putExtra("nic",nic.getText().toString());
                i.putExtra("phone",phone.getText().toString());
                i.putExtra("email",email.getText().toString());
                i.putExtra("dob",dob.getText().toString());

                startActivity(i);

            }
        });

        doctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewDoctor.class));
            }
        });

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewAdd.class));
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewTest.class));
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),patientLogin.class));
        finish();
    }


}