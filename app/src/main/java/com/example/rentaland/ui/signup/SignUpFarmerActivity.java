package com.example.rentaland.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentaland.MainActivity;
import com.example.rentaland.databinding.ActivitySignUpFarmlandBinding;
import com.example.rentaland.model.FarmModel;
import com.example.rentaland.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpFarmerActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST_TITLE = 2;

    private ActivitySignUpFarmlandBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference storageTitleRef;
    private FirebaseUser mUser;

    private Uri titleImageUrl;
    private Uri imageUri;
    private FarmModel farmModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpFarmlandBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("user_farmer").child(user.getUid());
        storage = FirebaseStorage.getInstance();

        binding.ivFarmImage.setOnClickListener(v -> openFileChooser());
        binding.ivLandTitle.setOnClickListener(v -> openFileChooser());

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasEmptyField(binding.etFarmAddress, binding.etFarmAddressLayout) ||
                        hasEmptyField(binding.etFarmName, binding.etFarmNameLayout) ||
                        hasEmptyField(binding.etFarmArea, binding.etFarmAreaLayout) ||
                        hasEmptyField(binding.etFarmBudget, binding.etFarmBudgetLayout)) {
                    return;
                }
                if (imageUri == null) {
                    Toast.makeText(SignUpFarmerActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                String farmAddress = binding.etFarmAddress.getText().toString().trim();
                String farmName = binding.etFarmName.getText().toString().trim();
                double farmArea = Double.parseDouble(binding.etFarmArea.getText().toString().trim());
                double farmingBudget = Double.parseDouble(binding.etFarmBudget.getText().toString().trim());
                farmModel = new FarmModel(farmAddress, farmArea, farmName, farmingBudget);
                saveCredentials(farmModel, mUser.getUid());
            }
        });
    }

    private void saveCredentials(FarmModel farmModel, String userId) {
        storageRef = storage.getReference("Farm Images").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        reference = database.getReference().child("farmland").child(userId);
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(SignUpFarmerActivity.this, "Upload Success!", Toast.LENGTH_SHORT).show();
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                farmModel.setFarmImageUrl(uri.toString());
                                reference.setValue(farmModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        startMain();
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpFarmerActivity.this, "Database Input Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpFarmerActivity.this, "Upload Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.ivFarmImage.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void startMain() {
        Intent intentMain = new Intent(SignUpFarmerActivity.this, MainActivity.class);
        startActivity(intentMain);
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
}