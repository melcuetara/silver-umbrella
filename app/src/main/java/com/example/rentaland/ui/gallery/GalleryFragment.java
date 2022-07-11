package com.example.rentaland.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rentaland.R;
import com.example.rentaland.databinding.FragmentGalleryBinding;
import com.example.rentaland.model.BookModel;
import com.example.rentaland.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private final String TAG = "GALLERY_FRAGMENT";

    private FragmentGalleryBinding binding;

    private FirebaseDatabase rootNode;
    private DatabaseReference referenceUser;
    private DatabaseReference referenceBook;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private NotificationAdapter.RecyclerViewClickListener mListener;
    private UserModel mUserModel;
    private BookModel mBookModel;
    private NotificationAdapter mAdapter;
    private ArrayList<UserModel> mUserList = new ArrayList<>();
    private ArrayList<BookModel> mBookList = new ArrayList<>();
    private ArrayList<String> keyList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        referenceUser = rootNode.getReference().child("user_investor");
        referenceBook = rootNode.getReference().child("book_request");
        reference = rootNode.getReference().child("book_accepted");

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        populateRecyclerView();
        setOnClickListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateRecyclerView() {
        populateBookList();
    }

    private void populateBookList() {
        keyList.clear();
        mBookList.clear();
        mUserList.clear();
        referenceBook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("farmerId").getValue().toString().equals(user.getUid())) {
                            Log.d(TAG, "FOUND");
                            mBookModel = dataSnapshot.getValue(BookModel.class);
                            mBookList.add(mBookModel);
                            keyList.add(dataSnapshot.getKey());
                            populateUserList(mBookModel.getInvestorId());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateUserList(String investorId) {
        Log.d(TAG, "investorId: " +investorId);
        referenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d(TAG, "datasnapshot id: " + dataSnapshot.getKey());
                        if (dataSnapshot.getKey().equals(investorId)) {
                            Toast.makeText(getContext(), "Found INVESTOR", Toast.LENGTH_SHORT).show();
                            mUserModel = dataSnapshot.getValue(UserModel.class);
                            mUserList.add(mUserModel);
                            break;
                        }
                    }
                }
                mAdapter = new NotificationAdapter(getContext(), mBookList, mListener, mUserList, keyList);
                binding.rvNotification.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setOnClickListener() {
        mListener = new NotificationAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, String key, int position) {
                if (v.getId() == v.findViewById(R.id.btn_accept_notification).getId()) {
                    reference.push().setValue(mBookList.get(position));
                    referenceBook.child(key).removeValue();
                }
            }
        };
    }
}