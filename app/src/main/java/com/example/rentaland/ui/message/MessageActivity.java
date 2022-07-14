package com.example.rentaland.ui.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.rentaland.R;
import com.example.rentaland.databinding.ActivityMessageBinding;
import com.example.rentaland.model.ChatModel;
import com.example.rentaland.model.MessageModel;
import com.example.rentaland.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    private final String TAG = "MESSAGE_ACTIVITY";

    private ActivityMessageBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mMessagesRef;
    private FirebaseUser mUser;
    private MessageListAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    LocalDateTime now;
    DateTimeFormatter dtf;
    InputMethodManager inputManager;
    private UserModel mCurrentUser;
    private UserModel mOtherUser;
    private String mOtherUserId;
    private String threadId;
    private ArrayList<ChatModel> chats;
    private ChatModel mChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRootNode = FirebaseDatabase.getInstance();

        inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        chats = new ArrayList<>();
        mCurrentUser = getIntent().getParcelableExtra("currentUser");
        mOtherUser = getIntent().getParcelableExtra("otherUser");
        mOtherUserId = getIntent().getStringExtra("userId");
        threadId = getIntent().getStringExtra("threadId");
        mMessagesRef = mRootNode.getReference("message_thread").child(threadId).child("message");
        Log.d(TAG, "onCreate: " + mCurrentUser.getFullName());
        Log.d(TAG, "onCreate: " + mOtherUser.getFullName());
        Log.d(TAG, "onCreate: " + mOtherUserId);
        Log.d(TAG, "onCreate: " + threadId);


        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.rcChatUserChat.setLayoutManager(linearLayoutManager);
        populateRecyclerView();

        binding.btnSendUserChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now = LocalDateTime.now();
                String date = dtf.format(now);
                String body = binding.etMessageUserChat.getText().toString();
                mChat = new ChatModel(body, date, mUser.getUid());
                mMessagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        mMessagesRef.child("" + (snapshot.getChildrenCount() + 1)).setValue(mChat);
                        binding.etMessageUserChat.setText("");

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private void populateRecyclerView() {
        mMessagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            mChat = dataSnapshot.getValue(ChatModel.class);
                            chats.add(mChat);
                        }
                    }
                }
                mAdapter = new MessageListAdapter(getApplicationContext(), chats, mUser.getUid());
                binding.rcChatUserChat.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}