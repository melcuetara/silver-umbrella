package com.example.rentaland.ui.message;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.rentaland.R;
import com.example.rentaland.databinding.ActivityMessageBinding;
import com.example.rentaland.model.MessageModel;
import com.example.rentaland.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
    private ArrayList<MessageModel> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mMessages = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRootNode = FirebaseDatabase.getInstance();
        mCurrentUser = getIntent().getParcelableExtra("currentUser");
        mOtherUser = getIntent().getParcelableExtra("otherUser");
        mOtherUserId = getIntent().getStringExtra("userId");
        mMessages = getIntent().getParcelableExtra("messages");
        Log.d(TAG, "onCreate: " + mCurrentUser.getFullName());
        Log.d(TAG, "onCreate: " + mOtherUser.getFullName());
        Log.d(TAG, "onCreate: " + mOtherUserId);
    }
}