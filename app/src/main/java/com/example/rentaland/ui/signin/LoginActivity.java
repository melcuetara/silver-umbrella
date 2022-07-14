package com.example.rentaland.ui.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentaland.MainActivity;
import com.example.rentaland.SmsGateway;
import com.example.rentaland.ui.signup.SignUpActivity;
import com.example.rentaland.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    private final String TAG = "LOGIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            startMain();
        } else {
            setContentView(view);
        }
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasEmptyFields(binding.etEmail, binding.etEmailLayout) || (hasEmptyFields(binding.etPassword, binding.etPasswordLayout))) {
                    return;
                }
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                signIn(email, password);
            }
        });
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });


    }

    private void startMain() {
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentMain);
    }

    private void startSignUp() {
        Intent intentSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intentSignUp);
    }

    private boolean hasEmptyFields(EditText etField, TextInputLayout etLayout) {
        if (etField.getText().toString().trim().length() == 0) {
            etLayout.setError("Required");
            etField.requestFocus();
            return true;
        }
        etLayout.setError(null);
        return false;
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmailAndPassword:success");
                            startMain();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}