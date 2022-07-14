package com.example.rentaland.ui.message;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.model.MessageModel;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private ArrayList<MessageModel> mMessages;

    public MessageListAdapter (Context context,ArrayList<MessageModel> messages, )
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
