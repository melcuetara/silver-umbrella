package com.example.rentaland.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rentaland.R;
import com.example.rentaland.SearchActivity;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private static final String TAG = "HOME_FRAGMENT";
    private FirebaseDatabase rootNode;
    private DatabaseReference referenceAccepted;
    private DatabaseReference reference;
    private DatabaseReference referenceBook;
    private DatabaseReference referenceUser;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FarmAdapter farmAdapter;
    private FarmAdapter.RecyclerViewClickListener listener;
    private FragmentHomeBinding binding;
    public LinearLayoutManager HorizontalLayout;

    private Button mBookButton;
    private BookModel mBook;
    private ArrayList<Boolean> mIsBooking = new ArrayList<>();
    private ArrayList<FarmModel> farmModels = new ArrayList<>();
    private ArrayList<BookModel> mBookedList = new ArrayList<>();
    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        referenceUser = rootNode.getReference("user_farmer");
        reference = rootNode.getReference().child("farmland");
        referenceBook = rootNode.getReference().child("book_request");
        referenceAccepted = rootNode.getReference().child("book_accepted");
        bundle = new Bundle();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnHomeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        binding.rvFarmland.setLayoutManager(new LinearLayoutManager(getContext()));
        HorizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        binding.rvFarmland.setLayoutManager(HorizontalLayout);
        populateBookedList();
        setOnClickListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data.getExtras() != null) {
                bundle = data.getExtras();
                Log.d(TAG, "onActivityResult: " + bundle);
                populateRecyclerView();
            }
        } catch (Exception e) {

        }

    }

    private void populateRecyclerView() {

        mIsBooking.clear();
        farmModels.clear();
        Log.d(TAG, "Populate RecyclerView");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmModels.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (!containsId(mBookedList, dataSnapshot.getKey())) {
                            FarmModel farmModel = dataSnapshot.getValue(FarmModel.class);
                            if (!bundle.isEmpty()) {
                                if (bundle.containsKey("keyword") && bundle.containsKey("areaMin") && bundle.containsKey("budgetMin")) {
                                    if (farmModel.getFarmName().equals(bundle.getString("keyword")) &&
                                            farmModel.getFarmingBudget() >= bundle.getDouble("budgetMin") &&
                                            farmModel.getFarmingBudget() <= bundle.getDouble("budgetMax") &&
                                            farmModel.getFarmArea() >= bundle.getDouble("areaMin") &&
                                            farmModel.getFarmArea() <= bundle.getDouble("areaMax")) {
                                        farmModels.add(farmModel);
                                        checkRequest(dataSnapshot.getKey());
                                    }
                                } else if (bundle.containsKey("keyword") && bundle.containsKey("areaMin")) {
                                    if (farmModel.getFarmName().equals(bundle.getString("keyword")) &&
                                            farmModel.getFarmArea() >= bundle.getDouble("budgetMin") &&
                                            farmModel.getFarmArea() <= bundle.getDouble("budgetMax")) {
                                        farmModels.add(farmModel);
                                        checkRequest(dataSnapshot.getKey());
                                    }
                                } else if (bundle.containsKey("keyword") && bundle.containsKey("budgetMin")) {
                                    if (farmModel.getFarmName().equals(bundle.getString("keyword")) &&
                                            farmModel.getFarmingBudget() >= bundle.getDouble("budgetMin") &&
                                            farmModel.getFarmingBudget() <= bundle.getDouble("budgetMax")) {
                                        farmModels.add(farmModel);
                                        checkRequest(dataSnapshot.getKey());
                                    }
                                } else if (bundle.containsKey("budgetMin") && bundle.containsKey("areaMin")) {
                                    if (farmModel.getFarmingBudget() >= bundle.getDouble("budgetMin") &&
                                            farmModel.getFarmingBudget() <= bundle.getDouble("budgetMax") &&
                                            farmModel.getFarmArea() >= bundle.getDouble("areaMin") &&
                                            farmModel.getFarmArea() <= bundle.getDouble("areaMax")) {
                                        farmModels.add(farmModel);
                                        checkRequest(dataSnapshot.getKey());
                                    }
                                } else if (bundle.containsKey("keyword")) {
                                    if (farmModel.getFarmName().equals(bundle.getString("keyword"))) {
                                        farmModels.add(farmModel);
                                        checkRequest(dataSnapshot.getKey());
                                    }
                                } else if (bundle.containsKey("budgetMin")) {
                                    if (farmModel.getFarmingBudget() >= bundle.getDouble("budgetMin") &&
                                            farmModel.getFarmingBudget() <= bundle.getDouble("budgetMax")) {
                                        farmModels.add(farmModel);
                                        checkRequest(dataSnapshot.getKey());
                                    }
                                } else if (bundle.containsKey("areaMin")) {
                                    if (farmModel.getFarmArea() >= bundle.getDouble("areaMin") &&
                                            farmModel.getFarmArea() <= bundle.getDouble("areaMax")) {
                                        farmModels.add(farmModel);
                                        checkRequest(dataSnapshot.getKey());
                                    }
                                }
                            } else if (bundle.isEmpty()) {
                                farmModels.add(farmModel);
                                checkRequest(dataSnapshot.getKey());
                            }
                        }
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

    public void populateBookedList() {
        mBookedList.clear();
        referenceAccepted.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            mBookedList.add(dataSnapshot.getValue(BookModel.class));
                        }
                    }
                }
                Log.d(TAG, "onDataChange: " + mBookedList.size());
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean containsId(ArrayList<BookModel> mBookedList, String key) {
        for (int i = 0; i < mBookedList.size(); i++) {
            Log.d(TAG, "onDataChange: key " + key);
            Log.d(TAG, "onDataChange: Booklist id" + mBookedList.get(i).getFarmerId());
            if (mBookedList.get(i).getFarmerId().equals(key) && mBookedList.get(i).getInvestorId().equals(user.getUid())) {
                return true;
            }
        }
        return false;
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
                                referenceUser.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.d(TAG, "onDataChange: " + snapshot.child("contactNumber").getValue().toString());
                                        sendSmsFarmer(snapshot.child("contactNumber").getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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

    public void sendSmsFarmer(String phone) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://sms.teamssprogram.com/api/send?key=f48388598105a1516e489c527b15a94c46252cf3&phone=" + phone + "&message=Rentaland%3an+investor+sent+you+a+booking+request+just+now%21+%0D%0AAccept+booking+request+to+start+chatting",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(objectRequest);
    }
}