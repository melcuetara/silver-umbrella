package com.example.rentaland.ui.message;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.rentaland.R;
import com.example.rentaland.databinding.ActivityMessageBinding;
import com.example.rentaland.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageActivity extends AppCompatActivity {

    private final String TAG = "MESSAGE_ACTIVITY";

    private ActivityMessageBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mMessagesRef;
    private FirebaseUser mUser;

    private UserModel mCurrentUser;
    private UserModel mOtherUser;
    private String mOtherUserId;
    private String mMessageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRootNode = FirebaseDatabase.getInstance();
        mMessagesRef = mRootNode.getReference("message").child(mMessageId);
        mCurrentUser = getIntent().getParcelableExtra("currentUser");
        mOtherUser = getIntent().getParcelableExtra("otherUser");
        mOtherUserId = getIntent().getStringExtra("userId");
        Log.d(TAG, "onCreate: " + mCurrentUser.getFullName());
        Log.d(TAG, "onCreate: " + mOtherUser.getFullName());
        Log.d(TAG, "onCreate: " + mOtherUserId);
    }
}