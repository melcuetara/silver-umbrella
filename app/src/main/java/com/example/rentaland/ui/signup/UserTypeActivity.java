package com.example.rentaland.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rentaland.databinding.ActivityUserTypeBinding;

public class UserTypeActivity extends AppCompatActivity {

    private ActivityUserTypeBinding binding;

    private String userType;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = new Bundle();

        if (binding.rbFarmer.isChecked()) {
            userType = "user_farmer";
        } else {
            userType = "user_investor";
        }
        bundle.putString("userType", userType);
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.cbAgree.isChecked()) {
                    Toast.makeText(UserTypeActivity.this, "Please Agree to The Terms and Conditions!", Toast.LENGTH_SHORT).show();
                    binding.cbAgree.setError("");
                    return;
                }
                Intent intent = new Intent(UserTypeActivity.this, SignUpCredentialsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}