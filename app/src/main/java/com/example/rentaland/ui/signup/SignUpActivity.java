package com.example.rentaland.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentaland.MainActivity;
import com.example.rentaland.databinding.ActivitySignUpBinding;
import com.example.rentaland.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private final String TAG = "SIGN_UP_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasEmptyField(binding.etEmail, binding.etEmailLayout) ||
                        hasEmptyField(binding.etPassword, binding.etPasswordLayout)) {
                    return;
                }
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                signUp(email, password);
            }
        });
    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            startUserType();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(SignUpActivity.this, "Password must be at least 6 characters!",
                                        Toast.LENGTH_SHORT).show();
                                binding.etPassword.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(SignUpActivity.this, "Email entered is already in use.",
                                        Toast.LENGTH_SHORT).show();
                                binding.etPassword.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                });
    }

    private boolean hasEmptyField(EditText etField, TextInputLayout etLayout) {

        if (etField.getText().toString().trim().length() == 0) {
            etLayout.setError("Required");
            etField.requestFocus();
            return true;
        }
        etLayout.setError(null);
        return false;
    }

    private void startUserType() {
        Intent intent = new Intent(SignUpActivity.this, UserTypeActivity.class);
        startActivity(intent);
    }
}

//        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (hasEmptyField(binding.etEmail, binding.etEmailLayout) ||
//                        hasEmptyField(binding.etPassword, binding.etPasswordLayout) ||
//                        hasEmptyField(binding.etFirstName, binding.etFirstNameLayout) ||
//                        hasEmptyField(binding.etLastName, binding.etLastNameLayout) ||
//                        hasEmptyField(binding.etAge, binding.etAgeLayout) ||
//                        hasEmptyField(binding.etGender, binding.etGenderLayout) ||
//                        hasEmptyField(binding.etContactNumber, binding.etContactNumberLayout) ||
//                        hasEmptyField(binding.etAddress, binding.etAddressLayout)) {
//                    return;
//                }
//                String email = binding.etEmail.getText().toString().trim();
//                String password = binding.etPassword.getText().toString().trim();
//                signUp(email, password);
//            }
//        });
//    }
//
//    private boolean hasEmptyField(EditText etField, TextInputLayout etLayout) {
//        if (etField.getText().toString().trim().length() == 0) {
//            etLayout.setError("Required");
//            etField.requestFocus();
//            return true;
//        }
//        etLayout.setError(null);
//        return false;
//    }
//
//    private void signUp(String email, String password) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            String firstName = binding.etFirstName.getText().toString().trim();
//                            String lastName = binding.etLastName.getText().toString().trim();
//                            int age = Integer.parseInt(binding.etAge.getText().toString().trim());
//                            String gender = binding.etGender.getText().toString().trim();
//                            String contactNumber = binding.etContactNumber.getText().toString().trim();
//                            String address = binding.etAddress.getText().toString().trim();
//                            String userType;
//                            UserModel userModel = new UserModel(firstName, lastName, age, gender, contactNumber, address);
//
//                            if (binding.rbFarmer.isChecked()) {
//                                userType = "user_farmer";
//                            } else {
//                                userType = "user_investor";
//                            }
//                            reference.child(userType).child(user.getUid()).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(SignUpActivity.this, "Account Creation Success!", Toast.LENGTH_SHORT).show();
//                                    if (userType.equals("user_farmer")) {
//                                        startSignUpFarmer();
//                                    }
//                                    startSignUpInvestor();
//                                }
//                            });
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(SignUpActivity.this, "Account Creation failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    private void startSignUpFarmer() {
//        Intent intentFarmer = new Intent(SignUpActivity.this, SignUpFarmerActivity.class);
//        startActivity(intentFarmer);
//    }
//
//    private void startSignUpInvestor() {
//      //  Intent intentInvestor = new Intent(SignUpActivity.this,)
//    }
//  }