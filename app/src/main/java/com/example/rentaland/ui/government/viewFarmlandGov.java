package com.example.rentaland.ui.government;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentaland.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewFarmlandGov extends AppCompatActivity {
    Bundle bundle;
    String UID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmland);
        bundle = getIntent().getExtras();
        UID = bundle.getString("UID");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }
}
