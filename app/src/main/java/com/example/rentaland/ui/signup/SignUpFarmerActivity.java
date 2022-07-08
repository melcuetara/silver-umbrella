package com.example.rentaland.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rentaland.databinding.ActivitySignUpFarmerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFarmerActivity extends AppCompatActivity {

    private ActivitySignUpFarmerBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpFarmerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("user_farmer").child(user.getUid());


    }
}