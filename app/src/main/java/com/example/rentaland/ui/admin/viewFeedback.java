package com.example.rentaland.ui.admin;

import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.adapter.viewFeedbackAdapter;
import com.example.rentaland.adapter.viewReportAdapter;
import com.example.rentaland.model.manageFeedbackModel;
import com.example.rentaland.model.viewReportModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xml.sax.SAXNotRecognizedException;

import java.util.ArrayList;
import java.util.List;

public class viewFeedback extends AppCompatActivity {

    RecyclerView recyclerView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    List<manageFeedbackModel> list = new ArrayList<manageFeedbackModel>();
    viewFeedbackAdapter adapter ;
    manageFeedbackModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_feedback);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        recyclerView = (RecyclerView) findViewById(R.id.rv_manage_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference.child("feedback").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String UID = snapshot.getKey();
                Log.d("UID", UID);
                reference.child("feedback").child(UID).orderByKey().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String id = snapshot.getKey();
                        Log.d("ID",id);
                        String feedback = snapshot.child("body").getValue().toString();
                        reference.child("user_farmer").orderByKey().equalTo(id).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String name = snapshot.child("firstName").getValue().toString()+" "+snapshot.child("lastName").getValue().toString();
                                String address = snapshot.child("address").getValue().toString();
                                String num = snapshot.child("contactNumber").getValue().toString();
                                String userType = "Farmer";
                                model = new manageFeedbackModel(name,address, num, userType,id,feedback);
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
                        reference.child("user_investor").orderByKey().equalTo(id).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String name = snapshot.child("firstName").getValue().toString()+" "+snapshot.child("lastName").getValue().toString();
                                String address = snapshot.child("address").getValue().toString();
                                String num = snapshot.child("contactNumber").getValue().toString();
                                String userType = "Investor";
                                model = new manageFeedbackModel(name,address, num, userType,id,feedback);
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
                        reference.child("user_government").orderByKey().equalTo(id).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String name = snapshot.child("firstName").getValue().toString()+" "+snapshot.child("lastName").getValue().toString();
                                String address = snapshot.child("barangayName").getValue().toString();
                                String num = snapshot.child("contactNumber").getValue().toString();
                                String userType = "Government";
                                model = new manageFeedbackModel(name,address, num, userType,id,feedback);
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
        adapter = new viewFeedbackAdapter(list, new viewReportAdapter.itemClickListener() {
            @Override
            public void itemOnClick(viewReportModel model) {

            }
        });
    }
}
