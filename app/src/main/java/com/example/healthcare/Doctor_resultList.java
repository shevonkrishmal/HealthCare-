package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

public class Doctor_resultList extends AppCompatActivity {
    public static final String EXTRA_DOCTORRESULTID = "DoctorID";
    private RecyclerView doctorList;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private Query query;

    private TextView Sname;

    private FirebaseFirestore fstore;
    private FirebaseAuth auth;
   FirebaseUser doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_result_list);

        Intent intent = getIntent();
        String input = intent.getStringExtra(Doctor_Search.EXTRA_TEXTDOCTOR);
        Log.i("ResultsHotel", input);

        Sname = findViewById(R.id.speDoctor);
        Sname.setText(input);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("Doctor");
        query = mDatabase.orderByChild("Specialization").equalTo(input);
/*
        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        doctor = auth.getCurrentUser();
        query = fstore.collection("Doctors").whereEqualTo("Specialization",input);
*/


        doctorList = (RecyclerView) findViewById(R.id.resultDoctorlist);
        doctorList.setHasFixedSize(true);
        doctorList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<CardData> options = new FirebaseRecyclerOptions.Builder<CardData>().setQuery(query, CardData.class).build();
        FirebaseRecyclerAdapter<CardData, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CardData, CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i, @NonNull CardData cardData) {
                cardViewHolder.setDetails(getApplicationContext(), cardData.getFullname(), cardData.getEmail(), cardData.getImage_url(),cardData.getSpecialization());
                cardViewHolder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dataId = getRef(i).getKey();
                        Log.i("detailsHanderCheck", "Touched" + dataId);
                        Intent intent = new Intent(Doctor_resultList.this,Doctor_profile_PV.class);
                        intent.putExtra(EXTRA_DOCTORRESULTID, dataId);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view_holder, parent, false);
                Log.i("viewData", view.toString());
                return new CardViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        doctorList.setAdapter(firebaseRecyclerAdapter);
    }


}