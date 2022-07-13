package com.example.rentaland.ui.message;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rentaland.R;
import com.example.rentaland.databinding.ActivityMessageBinding;
import com.example.rentaland.model.UserModel;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding binding;
    private UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mUserModel = getIntent().getParcelableExtra("userModel");
    }
}