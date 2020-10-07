package com.example.healthcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Doctor_EditProfile extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 100;
    public static final String TAG = "TAG";
    public Uri imageUri = null;
    EditText profileFullName,profileEmail,profilePhone,profilegmc,profilesplzn;


    ImageView profileImageView;
    Button saveBtn;
    Button deleteBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser doctor;
    StorageReference storageReference;
    private String DoctorID;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__edit_profile);

        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");
        String gmc = data.getStringExtra("gmc");
        String specialization = data.getStringExtra("Specialization");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        doctor = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();


        profileFullName = findViewById(R.id.fullName);
        profileEmail = findViewById(R.id.Email);
        profilePhone = findViewById(R.id.phone);
        profilegmc = findViewById(R.id.GMC);
        profilesplzn =findViewById(R.id.Specialization);

        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveProfileInfo);
        deleteBtn =findViewById(R.id.deletbtn);


        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("Doctor");


//getdata from database
        DoctorID = fAuth.getCurrentUser().getUid();
        doctor = fAuth.getCurrentUser();


        final DocumentReference documentReference = fStore.collection("Doctors").document(DoctorID);

        documentReference.addSnapshotListener(Doctor_EditProfile.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                profileFullName.setText(documentSnapshot.getString("fullname"));
                profileEmail.setText(documentSnapshot.getString("email"));
                profilePhone.setText(documentSnapshot.getString("phone"));
                profilegmc.setText(documentSnapshot.getString("gmc"));
                profilesplzn.setText(documentSnapshot.getString("Specialization"));





            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();

            }
        });


        final StorageReference pofileRef = storageReference.child("Doctors/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
        pofileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
                choosePicture();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatefunction();
              //  uploadImage(imageUri);

            }
        });

        profileEmail.setText(email);
        profileFullName.setText(fullName);
        profilePhone.setText(phone);
        profilegmc.setText(gmc);
        profilesplzn.setText(specialization);

        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + phone+ " "+ gmc + " " +specialization+ " ");


    }

    private void updatefunction() {
        if(profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()){
            Toast.makeText(Doctor_EditProfile.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String email = profileEmail.getText().toString();
        doctor.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DocumentReference docRef = fStore.collection("Doctors").document(doctor.getUid());
                Map<String, Object> edited = new HashMap<>();
                edited.put("email",email);
                edited.put("fullname",profileFullName.getText().toString());
                edited.put("phone",profilePhone.getText().toString());
                edited.put("gmc",profilegmc.getText().toString());
                edited.put("Specialization",profilesplzn.getText().toString());


                //update realtime db
                mDatabase.child(DoctorID).updateChildren(edited);

                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Doctor_EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Doctor_Profile.class));
                        finish();
                    }
                });
                Toast.makeText(Doctor_EditProfile.this, "Email is changed.", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Doctor_EditProfile.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteUser() {

            // [START delete_user]
        doctor = fAuth.getCurrentUser();
     fStore.collection("Doctors").document(doctor.getUid()).delete();
    // String value = mDatabase.child("Doctor").getKey();


        doctor.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Doctor_EditProfile.this,"Your profile Deleted",Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), Doctor_Login.class));
                            Log.d(TAG, "User account deleted.");

                        }
                    }
                });

            // [END delete_user]

    }


    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);

        }
    }

    private void uploadImage(Uri imageUri) {

        final StorageReference doctor = storageReference.child("Doctors/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
        doctor.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Doctor_EditProfile.this,"Image uploaded",Toast.LENGTH_LONG).show();
                doctor.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       // String url = uri.toString();
                        Picasso.get().load(uri).into(profileImageView);

                      //doctor.child("image").setValue(url);
                        //mDatabase.push().child(DoctorID).setValue(doctor);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Doctor_EditProfile.this, "Image upload failed", Toast.LENGTH_LONG).show();
            }
        });

    }

}