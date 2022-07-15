package com.example.rentaland.ui.admin;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.adapter.viewReportAdapter;
import com.example.rentaland.model.viewReportModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class viewReportAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    viewReportModel model;
    viewReportAdapter adapter;
    List<viewReportModel> list = new ArrayList<viewReportModel>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        setContentView(R.layout.activity_view_report);
        recyclerView = (RecyclerView) findViewById(R.id.rv_manage_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference.child("report_user").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id = snapshot.getKey();
                reference.child("report_user").child(id).orderByKey().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String UID = snapshot.getKey();
                        String body = snapshot.child("body").getValue().toString();
                        String reportedId = snapshot.child("reportedId").getValue().toString();
                        reference.child("user_farmer").orderByKey().equalTo(reportedId).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String name = snapshot.child("firstName").getValue().toString()+" "+snapshot.child("lastName").getValue().toString();
                                String userType = "Farmer";
                                String number = snapshot.child("contactNumber").getValue().toString();
                                Log.d("name",name);
                                model = new viewReportModel(name,number,userType,body);
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
        adapter = new viewReportAdapter(list, new viewReportAdapter.itemClickListener() {
            @Override
            public void itemOnClick(viewReportModel model) {

            }
        });
    }
}
