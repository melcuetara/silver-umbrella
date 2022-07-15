package com.example.rentaland.ui.government;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.adapter.viewFarmerAdapter;
import com.example.rentaland.model.viewFarmerGovModel;
import com.example.rentaland.ui.signup.SignUpCredentialsActivity;
import com.example.rentaland.ui.signup.UserTypeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class viewFarmer extends AppCompatActivity {
    ImageButton addFarmerBtn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    RecyclerView recyclerView;
    private DatabaseReference reference;
    List<viewFarmerGovModel> list = new ArrayList<viewFarmerGovModel>();
    viewFarmerAdapter adapter;
    viewFarmerGovModel model;
    String govUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmer);
        recyclerView = (RecyclerView) findViewById(R.id.rv_viewFarmer_gov);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        addFarmerBtn = (ImageButton) findViewById(R.id.logoutGov);
        addFarmerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateID();
            }
        });

        reference.child("user_government").child(user.getUid()).child("registeredFarmer").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("id",snapshot.getKey());
                String ids = snapshot.getKey();
                reference.child("user_farmer").orderByKey().equalTo(ids).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String id = snapshot.getKey();
                        String name = snapshot.child("firstName").getValue().toString()+" "+snapshot.child("lastName").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();
                        String contactNumber = snapshot.child("contactNumber").getValue().toString();
                        String age = snapshot.child("age").getValue().toString();
                        String gender = snapshot.child("gender").getValue().toString();
                        String imageUrl = snapshot.child("imageUrl").getValue().toString();
                        model = new viewFarmerGovModel(name,address,contactNumber,age,gender,imageUrl,id);
                        list.add(model);
                        recyclerView.setAdapter(adapter);
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
        adapter = new viewFarmerAdapter(list, new viewFarmerAdapter.itemOnClick() {
            @Override
            public void itemClick(viewFarmerGovModel model) {
                openEditFarmland(model.getUID());
            }

            @Override
            public void itemDelete(viewFarmerGovModel model) {

            }

            @Override
            public void itemEditFarmer(viewFarmerGovModel model) {
                openEditFarmer(model.getUID());
            }
        });
        }
    public void generateID()
    {
        govUser = user.getUid();
        Random rnd = new Random();
        int user = rnd.nextInt(9999999);
        int password = rnd.nextInt(9999999);
        mAuth.createUserWithEmailAndPassword(""+user+"@gmail.com", ""+password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            reference.child("user_government").child(govUser).child("registeredFarmer").child(mAuth.getUid()).setValue("");
                            openCredential();
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
    public void openEditFarmland(String UID)
    {
        Intent intent = new Intent(this, editFarmlandGov.class);
        startActivity(intent);
    }
    public void openEditFarmer(String UID)
    {
        Bundle bundle = new Bundle();
        bundle.putString("UID",UID);
        Intent intent = new Intent(this, editFarmerGov.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void openCredential()
    {
        Bundle bundle = new Bundle();
        bundle.putString("userType", "user_farmer");
        Intent intent = new Intent(this, SignUpCredentialsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
