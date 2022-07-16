package com.example.rentaland.ui.government;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.adapter.viewFarmlandGovAdapter;
import com.example.rentaland.model.viewFarmlandGovModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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
        reference.child("farmland").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                reference.child("farmland").child(snapshot.getKey()).orderByValue().equalTo(UID).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String farmlandName,address,budget,farmlandArea,farmlandUrl;
                        farmlandArea = snapshot.child("farmArea").getValue().toString();
                        farmlandName = snapshot.child("farmName").getValue().toString();
                        address = snapshot.child("farmAddress").getValue().toString();
                        budget = snapshot.child("farmingBudget").getValue().toString();
                        farmlandUrl =snapshot.child("farmImageUrl").getValue().toString();
                        model = new viewFarmlandGovModel(farmlandName,address,budget,farmlandArea,farmlandUrl);
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
        adapter = new viewFarmlandGovAdapter(list, new viewFarmlandGovAdapter.itemOnClick() {
            @Override
            public void itemDelete(viewFarmlandGovModel model) {

            }

            @Override
            public void itemEditFarmland(viewFarmlandGovModel model) {

            }
        });

    }
}
