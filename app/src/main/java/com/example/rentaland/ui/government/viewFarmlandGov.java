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
import com.example.rentaland.adapter.viewFarmlandGovAdapter;
import com.example.rentaland.model.viewFarmlandGovModel;
import com.example.rentaland.ui.signup.SignUpFarmerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SNIHostName;

public class viewFarmlandGov extends AppCompatActivity {
    Bundle bundle;
    String UID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    List<viewFarmlandGovModel> list = new ArrayList<viewFarmlandGovModel>();
    viewFarmlandGovAdapter adapter;
    viewFarmlandGovModel model;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmland);
        bundle = getIntent().getExtras();
        UID = bundle.getString("UID");
        database = FirebaseDatabase.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.rv_viewFarmland_gov);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = database.getReference();
        ImageButton imageButton = findViewById(R.id.btn_addFarmlandBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFarmland(UID);
            }
        });
        reference.child("farmland").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id = snapshot.getKey();
                Log.d("id",id);
                if(snapshot.child("farmerId").getValue().toString().contains(UID))
                {
                    String farmlandName,address,budget,farmlandArea,farmlandUrl;
                    farmlandArea = snapshot.child("farmArea").getValue().toString();
                    farmlandName = snapshot.child("farmName").getValue().toString();
                    address = snapshot.child("farmAddress").getValue().toString();
                    budget = snapshot.child("farmingBudget").getValue().toString();
                    farmlandUrl =snapshot.child("farmImageUrl").getValue().toString();
                    model = new viewFarmlandGovModel(farmlandName,address,budget,farmlandArea,farmlandUrl,id);
                    list.add(model);
                    recyclerView.setAdapter(adapter);
                }
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
        adapter = new viewFarmlandGovAdapter(list, new viewFarmlandGovAdapter.itemOnClick() {
            @Override
            public void itemDelete(viewFarmlandGovModel model) {
                reference.child("farmland").orderByKey().equalTo(model.getUID()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        reference.child(snapshot.getKey()).removeValue();
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
            public void itemEditFarmland(viewFarmlandGovModel model) {
                    openEditFarmland(model.getUID());
            }
        });

    }
    public void openEditFarmland(String UID)
    {   Bundle bundle = new Bundle();
        Intent intent = new Intent(this, editFarmlandGov.class);
        bundle.putString("UID",UID);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void openFarmland(String UID)
    {
        Intent intent = new Intent(this, SignUpFarmerActivity.class);
        intent.putExtra("key",UID);
        startActivity(intent);
    }

}
