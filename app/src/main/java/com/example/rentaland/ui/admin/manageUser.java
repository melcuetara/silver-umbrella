package com.example.rentaland.ui.admin;

import android.os.Bundle;
import android.provider.Contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.adapter.manageUserAdapter;
import com.example.rentaland.model.manageUserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class manageUser extends AppCompatActivity {
    public List<manageUserModel> modelList = new ArrayList<manageUserModel>();
    public manageUserModel model;
    public manageUserAdapter adapter;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        recyclerView = (RecyclerView) findViewById(R.id.rv_manage_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference.child("user_farmer").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String UID = snapshot.getKey();
                String name = snapshot.child("firstName").getValue().toString() +" "+snapshot.child("lastName").getValue().toString();
                String num = snapshot.child("contactNumber").getValue().toString();
                String address = snapshot.child("address").getValue().toString();
                String userType = "Farmer";
                String imageUrl = snapshot.child("imageUrl").toString().toString();
                model = new manageUserModel(name,address,num,userType,imageUrl,UID);
                modelList.add(model);
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
        reference.child("user_government").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String UID = snapshot.getKey();
                String name = snapshot.child("firstName").getValue().toString() +" "+snapshot.child("lastName").getValue().toString();
                String num = snapshot.child("contactNumber").getValue().toString();
                String address = snapshot.child("barangayName").getValue().toString();
                String userType = "Government";
                String imageUrl = snapshot.child("imageUrl").toString().toString();
                model = new manageUserModel(name,address,num,userType,imageUrl,UID);
                modelList.add(model);
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
        reference.child("user_investor").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String UID = snapshot.getKey();
                String name = snapshot.child("firstName").getValue().toString() +" "+snapshot.child("lastName").getValue().toString();
                String num = snapshot.child("contactNumber").getValue().toString();
                String address = snapshot.child("address").getValue().toString();
                String userType = "Investor";
                String imageUrl = snapshot.child("imageUrl").toString().toString();
                model = new manageUserModel(name,address,num,userType,imageUrl, UID);
                modelList.add(model);
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
        adapter = new manageUserAdapter(modelList, new manageUserAdapter.itemOnClick() {
            @Override
            public void itemDelete(manageUserModel model, int pos) {
                if(model.userType.contains("Farmer"))
                {
                    reference.child("user_farmer").orderByKey().equalTo(model.getUserUID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            reference.child(snapshot.getKey()).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if(model.userType.contains("Investor"))
                {
                    reference.child("user_investor").orderByKey().equalTo(model.getUserUID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            reference.child(snapshot.getKey()).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if(model.userType.contains("Government"))
                {
                    reference.child("user_government").orderByKey().equalTo(model.getUserUID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            reference.child(snapshot.getKey()).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                adapter.notifyItemRemoved(pos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void itemClicks(manageUserModel model) {

            }
        });


    }
}
