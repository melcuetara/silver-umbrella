package com.example.rentaland.ui.admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentaland.R;
import com.example.rentaland.model.IdModel;
import com.example.rentaland.model.UserModel;
import com.example.rentaland.model.registerGovernmentModel;
import com.example.rentaland.ui.signup.SignUpCredentialsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class registerGovernment extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_PROFILE = 1;
    private static final int PICK_IMAGE_REQUEST_ID = 2;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private Uri idImageUrl;
    private Uri imageUri;
    private IdModel idModel;
    private DatePickerDialog datePickerDialog;
    EditText etBarangayName, etFirstName, etLastName, etAge, etGender, etContactNum, etEmail, etPassword;
    Button registerBtn;
    ImageView ivImage;
    private StorageReference storageRef;
    private StorageReference storageRefId;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        setContentView(R.layout.activity_register_government);
        etBarangayName = (EditText) findViewById(R.id.et_barangay_name_gov);
        etFirstName = (EditText) findViewById(R.id.et_first_name);
        etLastName = (EditText) findViewById(R.id.et_last_name);
        etAge = (EditText) findViewById(R.id.et_age);
        etGender = (EditText) findViewById(R.id.et_gender_gov);
        registerBtn = (Button) findViewById(R.id.btn_register);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etContactNum = (EditText) findViewById(R.id.et_contact_number_gov);
        ivImage = (ImageView) findViewById(R.id.iv_user_image_gov);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser(PICK_IMAGE_REQUEST_PROFILE);
            }
        });
        initDatePicker();
        etAge.setText("Select Birth Date: " + getTodayDate());
        etAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker() == false) {
                    createUser(etEmail.getText().toString(), etPassword.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_PROFILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivImage.setImageURI(imageUri);
        } else if (requestCode == PICK_IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null && data.getData() != null) {
            idImageUrl = data.getData();
            ivImage.setImageURI(idImageUrl);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    public Boolean checker() {
        Boolean check;
        if (etBarangayName.getText().toString().isEmpty()) {
            etBarangayName.setError("Please Enter Barangay Name");
            return check = true;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Please Enter Password");
            return check = true;
        } else if (etContactNum.getText().toString().isEmpty()) {
            etContactNum.setError("Please Enter Contact Number");
            return check = true;
        } else if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError("Please Enter First Name");
            return check = true;
        } else if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Please Enter Last Name");
            return check = true;
        } else if (etAge.getText().toString().isEmpty()) {
            etAge.setError("Please Enter Age");
            return check = true;
        } else if (etGender.getText().toString().isEmpty()) {
            etGender.setError("Please Enter Gender");
            return check = true;
        } else if (etAge.getText().toString().isEmpty()) {

            etAge.setError("Please Enter Age");
            return check = true;
        } else
            etBarangayName.setError(null);
        etGender.setError(null);
        etLastName.setError(null);
        etFirstName.setError(null);
        etContactNum.setError(null);
        etPassword.setError(null);
        etEmail.setError(null);
        etAge.setError(null);
        return check = false;
    }

    public void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveCredentials();
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {

                            } catch (FirebaseAuthUserCollisionException e) {

                            } catch (Exception e) {

                            }

                        }
                    }
                });

    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                etAge.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.setTitle("Date of Birth");

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private void saveCredentials() {
        storageRef = storage.getReference("User Images").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                idModel = new IdModel(mAuth.getUid());
                                idModel.setIdImageUrl(uri.toString());
                                registerGovernmentModel model = new registerGovernmentModel(etBarangayName.getText().toString(), etFirstName.getText().toString(), etLastName.getText().toString(), etAge.getText().toString(), etGender.getText().toString(), etContactNum.getText().toString(),idModel.getIdImageUrl());
                                FirebaseDatabase.getInstance().getReference().child("user_government").child(mAuth.getUid()).setValue(model);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}
