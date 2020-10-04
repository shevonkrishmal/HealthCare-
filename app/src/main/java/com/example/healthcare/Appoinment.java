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

public class Appoinment extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023;
    TextView fullName, nic, phone, email, date, time;
    String addID;


    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment);

        fullName = findViewById(R.id.fullName);
        nic = findViewById(R.id.nic);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        /*StorageReference profileRef = storageReference.child("addvertiesement/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });*/


        addID = fAuth.getCurrentUser().getUid();


        final DocumentReference documentReference = fStore.collection("addvertiesement").document(addID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot.exists()) {
                    fullName.setText(documentSnapshot.getString("fullName"));
                    nic.setText(documentSnapshot.getString("nic"));
                    phone.setText(documentSnapshot.getString("phone"));
                    email.setText(documentSnapshot.getString("email"));
                    date.setText(documentSnapshot.getString("date"));
                    time.setText(documentSnapshot.getString("time"));
                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }

        });
    }
}
