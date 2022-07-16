package com.example.rentaland.ui.government;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentaland.R;
import com.example.rentaland.databinding.ActivitySignUpFarmlandBinding;
import com.example.rentaland.model.FarmModel;
import com.example.rentaland.model.IdModel;
import com.example.rentaland.model.editFarmlandModel;
import com.example.rentaland.model.viewFarmlandGovModel;
import com.example.rentaland.ui.signup.SignUpFarmerActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class editFarmlandGov extends AppCompatActivity {
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

    private Uri imageUri;
    private FarmModel farmModel;
    Boolean isEdited=false;
    EditText etFarmName,etFarmAddress,etFarmBudget,etFarmArea;
    ImageView ivFarmland;
    Button editBtn;
    String UID;
    Bundle bundle;
    String farmlandUrl,farmlandProf,farmId,farmerID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmland);
        bundle = getIntent().getExtras();
        UID = bundle.getString("UID");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("user_farmer").child(user.getUid());
        storage = FirebaseStorage.getInstance();
        etFarmName = (EditText) findViewById(R.id.et_edit_farm_name);
        etFarmAddress = (EditText) findViewById(R.id.et_edit_farm_address);
        etFarmBudget = (EditText) findViewById(R.id.et_edit_farm_budget);
        etFarmArea = (EditText) findViewById(R.id.et_edit_farm_area);
        ivFarmland = (ImageView) findViewById(R.id.iv_farm_edit_image);
        editBtn = (Button) findViewById(R.id.btn_edit);
        Log.d("inputted UID",UID);
        FirebaseDatabase.getInstance().getReference().child("farmland").orderByKey().equalTo(UID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id = snapshot.getKey();
                Log.d("uid",id);
                    farmId = snapshot.getKey();
                    farmlandProf = snapshot.child("farmImageUrl").getValue().toString();
                    farmlandUrl = snapshot.child("farmTitleImageUrl").getValue().toString();
                    etFarmArea.setText(snapshot.child("farmArea").getValue().toString());
                    etFarmName.setText(snapshot.child("farmName").getValue().toString());
                    etFarmAddress.setText(snapshot.child("farmAddress").getValue().toString());
                    etFarmBudget.setText(snapshot.child("farmingBudget").getValue().toString());
                    farmerID = snapshot.child("farmerId").getValue().toString();

                    Picasso.get().load(snapshot.child("farmImageUrl").getValue().toString()).into(ivFarmland);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ivFarmland.setOnClickListener(v -> openFileChooser(PICK_IMAGE_REQUEST));
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdited==true)
                {
                    saveCredentials();
                }
                else
                {
                        editFarmlandModel model = new editFarmlandModel(etFarmAddress.getText().toString(),etFarmAddress.getText().toString(),farmlandProf,etFarmName.getText().toString(),farmlandUrl,Double.parseDouble(farmerID),Double.parseDouble(etFarmBudget.getText().toString()));
                        Log.d("2","enter");
                        FirebaseDatabase.getInstance().getReference().child("farmland").child(farmId).setValue(model);
                }
            }
        });
    }
    
    private void saveCredentials() {
        String key = database.getReference().push().getKey();
        storageRef = storage.getReference("Farm Images").child(UID).child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        reference = database.getReference().child("farmland").child(UID);
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String farmAddress = etFarmAddress.getText().toString().trim();
                                String farmName = etFarmName.getText().toString().trim();
                                double farmArea = Double.parseDouble(etFarmArea.getText().toString().trim());
                                double farmingBudget = Double.parseDouble(etFarmBudget.getText().toString().trim());
                                    IdModel idModel = new IdModel(mAuth.getUid());
                                    idModel.setIdImageUrl(uri.toString());
                                    editFarmlandModel model = new editFarmlandModel(farmAddress,etFarmAddress.getText().toString(),farmlandProf,farmName,idModel.getIdImageUrl(),Double.parseDouble(farmerID),Double.parseDouble(etFarmBudget.getText().toString()));
                                    Log.d("1","enter");
                                    FirebaseDatabase.getInstance().getReference().child("farmland").child(farmId).setValue(model);
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
    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivFarmland.setImageURI(imageUri);
            isEdited = true;
        }

    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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
