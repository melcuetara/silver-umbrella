package com.example.rentaland.ui.message;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rentaland.R;
import com.example.rentaland.databinding.FragmentMessageBinding;
import com.example.rentaland.model.FarmModel;
import com.example.rentaland.model.UserModel;
import com.example.rentaland.ui.home.FarmAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MessageThreadFragment extends Fragment {

    private final String TAG = "MESSAGE_FRAGMENT";

    public LinearLayoutManager linearLayoutManager;
    private FragmentMessageBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootNode;
    private FirebaseUser mUser;
    private DatabaseReference mMessageReference;
    private DatabaseReference mFarmerRef;
    private DatabaseReference mInvestorRef;
    private MessageThreadAdapter mAdapter;
    private MessageThreadAdapter.RecyclerViewClickListener mListener;

    private String mOtherUserType;
    private String mUserType;
    private ArrayList<UserModel> mUserList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        mRootNode = FirebaseDatabase.getInstance();
        mUser = mAuth.getCurrentUser();
        mMessageReference = mRootNode.getReference().child("message");
        mFarmerRef = mRootNode.getReference().child("user_farmer");
        mInvestorRef = mRootNode.getReference().child("user_investor");
        mUserList = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        binding.rvMessage.setLayoutManager(linearLayoutManager);
        populateRecyclerView();
        setOnClickListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateRecyclerView() {
        mMessageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUserList.clear();
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Log.d(TAG, "onDataChange: " + dataSnapshot.getValue().toString());
                            for (DataSnapshot dataSnapshotDetails: dataSnapshot.getChildren()) {
                                if (dataSnapshotDetails.getValue().equals(mUser.getUid())) {
                                    if (dataSnapshotDetails.getKey().equals("investorId")) {
                                        populateUserList("user_farmer", dataSnapshot.child("farmerId").getValue().toString());
                                    } else {
                                        populateUserList("user_investor", dataSnapshot.child("investorId").getValue().toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMessages() {
        mMessageReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            if (dataSnapshot.child(mUserType).getValue().toString().equals(mUser.getUid())) {

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateUserList(String key, String id) {
        Log.d(TAG, "populateUserList: " +key + " " + id);
        mRootNode.getReference(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(id)) {
                        mUserList.add(dataSnapshot.getValue(UserModel.class));
                        mAdapter = new MessageThreadAdapter(getContext(), mUserList, mListener);
                        binding.rvMessage.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setOnClickListener() {
        mListener = new MessageThreadAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position, UserModel userModel) {
                Log.d(TAG, "onClick: Clicked");
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("userModel", userModel);
                startActivity(intent);
            }
        };
    }
}