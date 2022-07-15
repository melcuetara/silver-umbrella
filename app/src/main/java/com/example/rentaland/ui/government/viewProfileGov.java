package com.example.rentaland.ui.government;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rentaland.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class viewProfileGov extends AppCompatActivity {
    TextView tvBarangayName,tvName,tvContactNum,tvGender,tvAge;
    ImageView imageView;
    Button editBtn;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    String barangayName,name,contactNum,gender,age;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_profile);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        editBtn = (Button) findViewById(R.id.btn_edit_profilegov);
        tvBarangayName = (TextView) findViewById(R.id.tv_barangayNameProfilegov);
        tvName = (TextView) findViewById(R.id.tv_personel_nameprofilegov);
        tvContactNum = (TextView) findViewById(R.id.tv_contactNum_profilegov);
        tvGender = (TextView) findViewById(R.id.tv_gender_profilegov);
        tvAge = (TextView) findViewById(R.id.tv_age_profilegov);
        imageView = (ImageView) findViewById(R.id.iv_user_image_proflegov);
        reference.child("user_government").orderByKey().equalTo(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                barangayName = snapshot.child("barangayName").getValue().toString();
                name = snapshot.child("firstName").getValue().toString()+" "+snapshot.child("lastName").getValue().toString();
                contactNum = snapshot.child("contactNumber").getValue().toString();
                gender = snapshot.child("gender").getValue().toString();
                age = snapshot.child("age").getValue().toString();
                tvBarangayName.setText(barangayName);
                tvName.setText(name);
                tvContactNum.setText(contactNum);
                tvGender.setText(gender);
                tvAge.setText(age);
                Picasso.get().load(snapshot.child("imageUrl").getValue().toString()).into(imageView);

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
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }
        });

    }
    public void openEditProfile()
    {
        Intent intent = new Intent(this,editProfileGov.class);
        startActivity(intent);
    }
}
