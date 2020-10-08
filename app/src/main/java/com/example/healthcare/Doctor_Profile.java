package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Doctor_Profile extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE = 1023;
    private static final int GALLERY_REQUEST = 100;
    private TextView R_fullname, R_email, R_phone, R_gmc,R_splzn, R_ProfilePic;
    private ImageView ProfilePic;
    // private String Email,Password;

    public Uri imageUri = null;
    private Button Updatebtn;
    private  Button ChannelDetails;
    private  Button Appoinments;


    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Button Addpic;

    // private FirebaseDatabase database;
    // private DatabaseReference dataReff;

    private FirebaseAuth auth;

    //private FirebaseStorage storage;
    private StorageReference storageReference;

    private FirebaseFirestore fstore;

    private String DoctorID;
    private FirebaseUser doctor;

    private Button resetpasswordbtn;
String pic;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__profile);

        R_fullname = findViewById(R.id.fullName);
        R_email = findViewById(R.id.Email);
       ProfilePic = findViewById(R.id.profilePic);
      //  Addpic = findViewById(R.id.addpic);
        R_phone = findViewById(R.id.phone);
        R_gmc = findViewById(R.id.GMC);
        R_splzn = findViewById(R.id.Specialization);
        //resetpasswordbtn = findViewById(R.id.resetpaswordbtn);
        Updatebtn = findViewById(R.id.update_profile);
        ChannelDetails =findViewById(R.id.chbtn);
        Appoinments = findViewById(R.id.abtn);
       // R_ProfilePic =findViewById(R.id.profilePic);



        storageReference = FirebaseStorage.getInstance().getReference().child("doctor_profilePic");
        //Authentication
        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        DoctorID = auth.getCurrentUser().getUid();
        doctor = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("Doctor").child(DoctorID);


      /*  final StorageReference pofileRef = storageReference.child("Doctors/" + auth.getCurrentUser().getUid() + "profile.jpg");
        pofileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(ProfilePic);
            }
        });
*/

        DoctorID = auth.getCurrentUser().getUid();
        doctor = auth.getCurrentUser();

        final DocumentReference documentReference = fstore.collection("Doctors").document(DoctorID);

        documentReference.addSnapshotListener(Doctor_Profile.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                R_fullname.setText(documentSnapshot.getString("fullname"));
                R_email.setText(documentSnapshot.getString("email"));
                R_phone.setText(documentSnapshot.getString("phone"));
                R_gmc.setText(documentSnapshot.getString("gmc"));
                R_splzn.setText(documentSnapshot.getString("Specialization"));
               // ProfilePic.setImageURI(Uri.parse(documentSnapshot.getString("image_url")));

                //Picasso.get().load(String.valueOf(ProfilePic)).into(ProfilePic);


            }
        });



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pic = snapshot.child("image_url").getValue().toString().trim();

                Picasso.get().load(pic).into(ProfilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*
        Addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
                choosePicture();
            }
        });


        /*resetpasswordbtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){


                final EditText resetPassword = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        doctor.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Doctor_Profile.this, "Password Resset Successfully.", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Doctor_Profile.this, "Password Reset Failed.", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog

                    }
                });

                passwordResetDialog.create().show();

            }
        });



*/
        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Doctor_EditProfile.class);
                startActivity(intent);
            }
        });

        ChannelDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Doctor_channelingDetails.class);
                startActivity(intent);
            }
        });

        Appoinments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Doctor_profile_PV.class);
                startActivity(intent);
            }
        });


    }

   /* private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(requestCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //ProfilePic.setImageURI(imageUri);
                uploadImage(imageUri);
            }
        }

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            ProfilePic.setImageURI(imageUri);
            uploadImage(imageUri);
        }
    }

    private void uploadImage(Uri imageUri) {

        final StorageReference fileRef = storageReference.child("Doctors/" + auth.getCurrentUser().getUid() + "profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Doctor_Profile.this,"Image uploaded",Toast.LENGTH_LONG).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(ProfilePic);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Doctor_Profile.this, "Image upload failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }




}