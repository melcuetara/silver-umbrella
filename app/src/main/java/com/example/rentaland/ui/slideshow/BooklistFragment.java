package com.example.rentaland.ui.slideshow;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rentaland.databinding.FragmentSlideshowBinding;
import com.example.rentaland.model.BookModel;
import com.example.rentaland.model.FarmModel;
import com.example.rentaland.model.UserModel;
import com.example.rentaland.ui.gallery.NotificationAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BooklistFragment extends Fragment {

    private static final String TAG = "BOOKLIST_FRAGMENT";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mBooklistReference;
    private DatabaseReference mFarmlandReference;

    private ArrayList<UserModel> mBookedList = new ArrayList<>();
    private ArrayList<FarmModel> mFarmlandList = new ArrayList<>();
    private UserModel mUserModel;
    private FarmModel mFarmModel;
    private BookListAdapter.RecyclerViewClickListener mListener;
    private BookListAdapter mAdapter;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRootNode = FirebaseDatabase.getInstance();
        mBooklistReference = mRootNode.getReference().child("book_accepted");
        mFarmlandReference = mRootNode.getReference().child("farmland");
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvBooklist.setLayoutManager(new LinearLayoutManager(getContext()));
        populateRecyclerView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        mListener = new BookListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position, FarmModel farmModel) {

            }
        };
    }

    private void populateRecyclerView() {
        Log.d(TAG, "populateRecyclerView: Execute");
        mBooklistReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + mUser.getUid());
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            if (dataSnapshot.child("investorId").getValue().toString().equals(mUser.getUid())) {
                                Log.d(TAG, "onDataChange: " + dataSnapshot.child("investorId"));
                                populateFarmlandList(dataSnapshot.child("farmerId").getValue().toString());
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

    private void populateFarmlandList(String farmerId) {
        Log.d(TAG, "populateFarmlandList: Here");
        mFarmlandReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(farmerId)) {
                        mFarmModel = dataSnapshot.getValue(FarmModel.class);
                        mFarmlandList.add(mFarmModel);
                        break;
                    }
                }
                mAdapter = new BookListAdapter(getContext(), mFarmlandList, mListener);
                binding.rvBooklist.setAdapter(mAdapter);
                Log.d(TAG, "onDataChange: " + mAdapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}