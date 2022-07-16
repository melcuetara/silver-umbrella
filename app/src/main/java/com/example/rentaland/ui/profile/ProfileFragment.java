package com.example.rentaland.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rentaland.R;
import com.example.rentaland.databinding.FragmentHomeBinding;
import com.example.rentaland.databinding.FragmentProfileBinding;
import com.example.rentaland.model.FarmModel;
import com.example.rentaland.model.UserModel;
import com.example.rentaland.ui.home.FarmAdapter;
import com.example.rentaland.ui.signup.SignUpFarmerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mCredentialsRef;

    private FarmlandAdapter.RecyclerViewClickListener mListener;
    private UserModel mUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        mRootNode = FirebaseDatabase.getInstance();
        mCredentialsRef = mRootNode.getReference("user_farmer").child(mAuth.getUid());
        getUser();

        binding.btnProfileFarm.setOnClickListener(this);
        setOnClickListener();
        return binding.getRoot();
    }

    private void getUser() {
        mCredentialsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser = snapshot.getValue(UserModel.class);
                binding.tvProfileAddress.setText(mUser.getAddress());
                binding.tvProfileName.setText(mUser.getFullName());
                binding.tvProfileContact.setText(mUser.getContactNumber());
                binding.tvProfileAge.setText("" + mUser.getAge());
                binding.tvProfileGender.setText(mUser.getGender());
                Picasso.get().load(mUser.getImageUrl()).into(binding.ivProfileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setOnClickListener() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnProfileFarm.getId()) {
            Toast.makeText(getContext(), "This", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), SignUpFarmerActivity.class);
            startActivity(intent);
            return;
        }
    }
}