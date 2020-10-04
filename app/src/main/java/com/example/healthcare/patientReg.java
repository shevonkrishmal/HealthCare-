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

public class patientReg extends AppCompatActivity {

    public static final String TAG = "TAG";

    FirebaseFirestore fStore;

    EditText pFullName,pAddress,pAge,pNic,pPhone,pEmail,pDob,pPassword;
    Button pSignUpBtn;
    TextView pLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg);



        pFullName = findViewById(R.id.fullName);
        pAddress = findViewById(R.id.address);
        pAge = findViewById(R.id.age);
        pNic = findViewById(R.id.nic);
        pPhone = findViewById(R.id.phone);
        pEmail = findViewById(R.id.email);
        pDob = findViewById(R.id.dob);
        pPassword = findViewById(R.id.password);
        pSignUpBtn = findViewById(R.id.signupbtn);
        pLoginBtn = findViewById(R.id.lgn);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressBar);





        pSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = pEmail.getText().toString().trim();
                String password = pPassword.getText().toString().trim();
                final String fullName = pFullName.getText().toString();
                final String address = pAddress.getText().toString();
                final String age = pAge.getText().toString();
                final String nic = pNic.getText().toString();
                final String phone = pPhone.getText().toString();
                final String dob = pDob.getText().toString();


                if(TextUtils.isEmpty(email)){
                    pEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    pPassword.setError("Password is Required.");
                    return;
                }


                if(password.length() < 8 ){
                    pPassword.setError("Password Must be more than 8 characters and less than 15 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                //register the patients in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(patientReg.this, "Patient Registered.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("patient").document(userID);
                            Map<String,Object> user = new HashMap<>();

                            user.put("fullName",fullName);
                            user.put("address",address);
                            user.put("age",age);
                            user.put("nic",nic);
                            user.put("phone",phone);
                            user.put("email",email);
                            user.put("dob",dob);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), patientLogin.class));

                        } else {
                            Toast.makeText(patientReg.this, "Patient is not registered!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }


                });
            }
        });


        pLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),patientLogin.class));
            }
        });


    }


}