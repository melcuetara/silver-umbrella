package com.example.rentaland.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentaland.MainActivity;
import com.example.rentaland.MainFarmerActivity;
import com.example.rentaland.databinding.ActivitySignUpCredentialsBinding;
import com.example.rentaland.model.IdModel;
import com.example.rentaland.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpCredentialsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_PROFILE = 1;
    private static final int PICK_IMAGE_REQUEST_ID = 2;
    private final String TAG = "SIGN_UP_CREDENTIALS";

    private ActivitySignUpCredentialsBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference referenceId;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference storageRefId;

    private UserModel user;
    private IdModel idModel;
    private Bundle bundle;
    private Uri imageUri;
    private Uri idImageUrl;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpCredentialsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        if (getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            bundle.getString("userType");
            userType = bundle.getString("userType");
        } else {
            userType = "user_farmer";
        }

        Log.d(TAG, "onCreate: " + userType);
        binding.ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST_PROFILE);
            }
        });

        binding.ivId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST_ID);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasEmptyField(binding.etFirstName, binding.etFirstNameLayout) ||
                        hasEmptyField(binding.etLastName, binding.etLastNameLayout) ||
                        hasEmptyField(binding.etAge, binding.etAgeLayout) ||
                        hasEmptyField(binding.etGender, binding.etGenderLayout) ||
                        hasEmptyField(binding.etContactNumber, binding.etContactNumberLayout) ||
                        hasEmptyField(binding.etAddress, binding.etAddressLayout) ||
                        hasEmptyField(binding.etIdLabel, binding.etIdLabelLayout)) {
                    return;
                }
                if (imageUri == null || idImageUrl == null) {
                    Toast.makeText(SignUpCredentialsActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                String firstName = binding.etFirstName.getText().toString().trim();
                String lastName = binding.etLastName.getText().toString().trim();
                int age = Integer.parseInt(binding.etAge.getText().toString().trim());
                String gender = binding.etGender.getText().toString().trim();
                String contactNumber = binding.etContactNumber.getText().toString().trim();
                String address = binding.etAddress.getText().toString().trim();
                String idType = binding.etIdLabel.getText().toString().trim();
                user = new UserModel(firstName, lastName, age, gender, contactNumber, address);
                idModel = new IdModel(idType);
                saveId(idModel, mUser.getUid());
                saveCredentials(user, mUser.getUid());
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

    private void saveCredentials(UserModel user, String userId) {
        storageRef = storage.getReference("User Images").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        reference = database.getReference().child(userType).child(userId);
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "Upload Profile Image Success");
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                user.setImageUrl(uri.toString());
                                reference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if (userType.equals("user_farmer")) {
                                            startMainFarmer();
                                            return;
                                        } else {
                                            startMain();
                                        }
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpCredentialsActivity.this, "Database Input Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpCredentialsActivity.this, "Upload Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveId(IdModel idModel, String userId) {
        storageRefId = storage.getReference("ID Images").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        referenceId = database.getReference().child("user_valid_id").child(userId);
        storageRefId.putFile(idImageUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "Upload ID Image Success");
                        storageRefId.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                idModel.setIdImageUrl(uri.toString());
                                referenceId.setValue(idModel)
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpCredentialsActivity.this, "Database Input Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpCredentialsActivity.this, "Upload Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startMainFarmer() {
        Intent intentFarmer = new Intent(SignUpCredentialsActivity.this, MainFarmerActivity.class);
        startActivity(intentFarmer);
    }

    private void startMain() {
        Intent intentMain = new Intent(SignUpCredentialsActivity.this, MainActivity.class);
        startActivity(intentMain);
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_PROFILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.ivUserImage.setImageURI(imageUri);
        } else if (requestCode == PICK_IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null && data.getData() != null) {
            idImageUrl = data.getData();
            binding.ivId.setImageURI(idImageUrl);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
