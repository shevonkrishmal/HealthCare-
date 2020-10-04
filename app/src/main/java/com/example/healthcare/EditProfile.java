package com.example.healthcare;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText proFullName , proAddress, proAge, proNic, proPhone, proEmail, proDob;
    ImageView profileImageView;
    Button saveBtn;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        final String address = data.getStringExtra("address");
        String age = data.getStringExtra("age");
        String nic = data.getStringExtra("nic");
        String phone = data.getStringExtra("phone");
        String email = data.getStringExtra("email");
        String dob = data.getStringExtra("dob");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        proFullName = findViewById(R.id.fullName);
        proAddress = findViewById(R.id.address);
        proAge = findViewById(R.id.age);
        proNic = findViewById(R.id.nic);
        proPhone = findViewById(R.id.phone);
        proEmail = findViewById(R.id.email);
        proDob = findViewById(R.id.dob);

        profileImageView = findViewById(R.id.propic);

        saveBtn = findViewById(R.id.save);

        StorageReference profileRef = storageReference.child("patient/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(EditProfile.this,"profile image clicked",Toast.LENGTH_SHORT).show();
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(proFullName.getText().toString().isEmpty() || proEmail.getText().toString().isEmpty() || proAddress.getText().toString().isEmpty() || proAge.getText().toString().isEmpty() || proNic.getText().toString().isEmpty() || proPhone.getText().toString().isEmpty() ||proDob.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfile.this, "One or many filds are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email = proEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("patient").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();

                        edited.put("email",email);
                        edited.put("fullName",proFullName.getText().toString());
                        edited.put("address",proAddress.getText().toString());
                        edited.put("age",proAge.getText().toString());
                        edited.put("nic",proNic.getText().toString());
                        edited.put("phone",proPhone.getText().toString());
                        edited.put("dob",proDob.getText().toString());

                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this,"profile updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),patientProfile.class));
                                finish();
                            }
                        });

                        Toast.makeText(EditProfile.this,"Email is changed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        proEmail.setText(email);
        proFullName.setText(fullName);
        proAddress.setText(address);
        proAge.setText(age);
        proNic.setText(nic);
        proPhone.setText(phone);
        proDob.setText(dob);
        


        Log.d(TAG,"onCreate" + fullName + " " + address + " " + age + " " + nic + " " + phone + " " + email + " " + dob);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){

                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);
            }
        }
    }


    private void uploadImageToFirebase(Uri imageUri) {
        //upload image to firebase storage

        final StorageReference fileRef = storageReference.child("patient/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(patientProfile.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"image upload fail",Toast.LENGTH_SHORT).show();
            }
        });

    }
}