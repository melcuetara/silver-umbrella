package com.example.rentaland.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rentaland.R;
import com.example.rentaland.databinding.FragmentHomeBinding;
import com.example.rentaland.model.BookModel;
import com.example.rentaland.model.FarmModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private FirebaseDatabase rootNode;
    private DatabaseReference referenceAccepted;
    private DatabaseReference reference;
    private DatabaseReference referenceBook;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FarmAdapter farmAdapter;
    private FarmAdapter.RecyclerViewClickListener listener;
    private FragmentHomeBinding binding;
    public LinearLayoutManager HorizontalLayout;

    private Button mBookButton;
    private BookModel mBook;
    private ArrayList<String> acceptedList = new ArrayList<>();
    private ArrayList<Boolean> mIsBooking = new ArrayList<>();
    private ArrayList<FarmModel> farmModels = new ArrayList<>();
    private ArrayList<String> mAcceptedList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("farmland");
        referenceBook = rootNode.getReference().child("book_request");
        referenceAccepted = rootNode.getReference().child("book_accepted");

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvFarmland.setLayoutManager(new LinearLayoutManager(getContext()));
        HorizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        binding.rvFarmland.setLayoutManager(HorizontalLayout);
        populateRecyclerView();
        setOnClickListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateRecyclerView() {
        Log.d("FARMLAND", "Populate RecyclerView");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmModels.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        FarmModel farmerModel = dataSnapshot.getValue(FarmModel.class);
                        farmModels.add(farmerModel);
                        checkRequest(dataSnapshot.getKey());
                    }
                } else {
                    Toast.makeText(getContext(), "No Farmlands Available!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void setOnClickListener() {
        listener = new FarmAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position, FarmModel farmModel) {
                mBookButton = v.findViewById(R.id.btn_book);
                if (v.getId() == mBookButton.getId()) {
                    bookFarm(farmModel);
                    return;
                }
            }
        };
    }

    public void bookFarm(FarmModel farmModel) {
        Log.d("FARMLAND", "Bookfarm");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (farmModel.getValue().equals(dataSnapshot.getValue(FarmModel.class).getValue())) {
                        mBook = new BookModel(user.getUid(), dataSnapshot.getKey(), getTimeStamp());
                        referenceBook.push().setValue(mBook).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                mBookButton.setEnabled(false);
                                mBookButton.setText("Book Request Sent");
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getTimeStamp() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month + "-" + day + "-" + year;
    }

    private void checkRequest(String key) {
        Log.d("FARMLAND", "Check Request");
        referenceBook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChildren()) {
                    mIsBooking.add(false);
                } else if (snapshot.exists()) {
                    long i = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("farmerId").getValue().toString().equals(key) &&
                                dataSnapshot.child("investorId").getValue().toString().equals(user.getUid())) {
                            mIsBooking.add(true);

                            break;
                        } else if (i == snapshot.getChildrenCount() - 1) {
                            mIsBooking.add(false);
                        }
                        i++;
                    }
                }
                farmAdapter = new FarmAdapter(getContext(), farmModels, listener, mIsBooking);
                binding.rvFarmland.setAdapter(farmAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "DatabaseError" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}