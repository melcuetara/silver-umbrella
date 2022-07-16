package com.example.rentaland.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rentaland.R;
import com.example.rentaland.databinding.FragmentHomeBinding;
import com.example.rentaland.databinding.FragmentProfileBinding;
import com.example.rentaland.model.FarmModel;
import com.example.rentaland.model.UserModel;
import com.example.rentaland.ui.home.FarmAdapter;
import com.example.rentaland.ui.signup.SignUpCredentialsActivity;
import com.example.rentaland.ui.signup.SignUpFarmerActivity;
import com.example.rentaland.ui.signup.UserTypeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mCredentialsRef;
    private DatabaseReference mFarmlandRef;

    public LinearLayoutManager HorizontalLayout;
    public FarmlandAdapter adapter;

    private FarmlandAdapter.RecyclerViewClickListener mListener;
    private UserModel mUser;
    private ArrayList<FarmModel> mFarms;
    private ArrayList<String> mKeys;
    private FarmModel mFarm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        mRootNode = FirebaseDatabase.getInstance();
        mCredentialsRef = mRootNode.getReference("user_farmer").child(mAuth.getUid());
        mFarmlandRef = mRootNode.getReference("farmland");
        mFarms = new ArrayList<>();
        mKeys = new ArrayList<>();
        mFarm = new FarmModel();
        getUser();
        getFarmlands();

        binding.btnProfileEdit.setOnClickListener(this);
        binding.btnProfileFarm.setOnClickListener(this);
        setOnClickListener();
        binding.rvProfileFarmland.setLayoutManager(new LinearLayoutManager(getContext()));
        HorizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        binding.rvProfileFarmland.setLayoutManager(HorizontalLayout);
        return binding.getRoot();
    }

    private void getFarmlands() {
        mFarms.clear();
        mKeys.clear();
        mFarmlandRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (dataSnapshot.child("farmerId").getValue().toString().equals(mAuth.getUid())) {
                        mFarm = dataSnapshot.getValue(FarmModel.class);
                        mFarms.add(mFarm);
                        mKeys.add(dataSnapshot.getKey());
                    }
                }
                adapter = new FarmlandAdapter(getContext(), mFarms, mKeys, mListener);
                binding.rvProfileFarmland.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        mListener = new FarmlandAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent editFarmIntent = new Intent(getContext(), SignUpFarmerActivity.class);
                editFarmIntent.putExtra("key", mKeys.get(position));
                editFarmIntent.putExtra("farm", (Serializable) mFarms.get(position));
                startActivity(editFarmIntent);
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnProfileFarm.getId()) {
            Toast.makeText(getContext(), "This", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), SignUpFarmerActivity.class);
            startActivity(intent);
            return;
        }
        if (v.getId() == binding.btnProfileEdit.getId()) {
            Intent intent = new Intent(getContext(), SignUpCredentialsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        if (requestCode == 1) {
            binding.rvProfileFarmland.removeAllViewsInLayout();
            adapter.notifyDataSetChanged();
        }
    }

}