package com.example.rentaland.ui.gallery;

import android.os.Bundle;
import android.os.Message;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rentaland.R;
import com.example.rentaland.SmsGateway;
import com.example.rentaland.databinding.FragmentGalleryBinding;
import com.example.rentaland.model.BookModel;
import com.example.rentaland.model.MessageModel;
import com.example.rentaland.model.MessageThreadModel;
import com.example.rentaland.model.UserModel;
import com.example.rentaland.ui.message.MessageThreadAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private final String TAG = "GALLERY_FRAGMENT";

    private FragmentGalleryBinding binding;

    private FirebaseDatabase rootNode;
    private DatabaseReference referenceUser;
    private DatabaseReference referenceBook;
    private DatabaseReference referenceThread;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private NotificationAdapter.RecyclerViewClickListener mListener;
    private MessageThreadModel threadModel;
    private UserModel mUserModel;
    private BookModel mBookModel;
    private NotificationAdapter mAdapter;
    private ArrayList<UserModel> mUserList = new ArrayList<>();
    private ArrayList<BookModel> mBookList = new ArrayList<>();
    private ArrayList<String> keyList = new ArrayList<>();
    private ArrayList<BookModel> mBookedList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        threadModel = new MessageThreadModel();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        referenceUser = rootNode.getReference().child("user_investor");
        referenceBook = rootNode.getReference().child("book_request");
        reference = rootNode.getReference().child("book_accepted");
        referenceThread = rootNode.getReference().child("message_thread");

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
        Log.d(TAG, "investorId: " + investorId);
        referenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d(TAG, "datasnapshot id: " + dataSnapshot.getKey());
                        if (dataSnapshot.getKey().equals(investorId)) {
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
            public void onClick(View v, String position, int adapterPosition, int btnDecline, int btnAccept, int id) {
                try {
                    Log.d(TAG, "view" + id);
                    Log.d(TAG, "accept" + btnAccept);
                    Log.d(TAG, "decline" + btnDecline);
                    if (v.getId() == btnAccept) {
                        reference.push().setValue(mBookList.get(adapterPosition));
                        Toast.makeText(getContext(), "Accept", Toast.LENGTH_SHORT).show();
                        referenceBook.child(position).removeValue();
                        threadModel.setFarmerId(user.getUid());
                        threadModel.setInvestorId(mBookList.get(adapterPosition).getInvestorId());
                        threadModel.setMessage(new ArrayList<MessageModel>() {
                            {
                                add(new MessageModel("I accepted your Book Request", user.getUid(), "14/07/2022"));
                            }

                        });
                        referenceThread.push().setValue(threadModel);
                        sendSms(mUserList.get(adapterPosition).getContactNumber());

                    }
                    if (v.getId() == btnDecline) {
                        referenceBook.child(position).removeValue();
                        Toast.makeText(getContext(), "Decline", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        };
    }

    public void sendSms(String phone) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://sms.teamssprogram.com/api/send?key=3c9d7f0e9cecb23c4504d3a031a228125e1d8251&phone=" + phone + "&message=Rentaland%3A+a+farmer+accepted+your+booking+request%2C+start+chatting+in+the+application+now.",
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