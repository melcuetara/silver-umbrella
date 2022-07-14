package com.example.rentaland.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.ChatModel;
import com.example.rentaland.model.MessageModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private static final int ITEM_TYPE_SENDER = 0;
    private static final int ITEM_TYPE_RECEIVER = 1;

    private ArrayList<ChatModel> chats;
    private String mCurrentUserId;
    private Context context;

    public MessageListAdapter(Context context, ArrayList<ChatModel> chats, String currentUserId) {

        this.chats = chats;
        mCurrentUserId = currentUserId;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_TYPE_SENDER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_sender_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_receiver_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (chats.get(position).getSender().equals(mCurrentUserId)) {
            holder.body.setText(chats.get(position).getBody());
            holder.date.setText(chats.get(position).getDate());
        } else {
            holder.body.setText(chats.get(position).getBody());
            holder.date.setText(chats.get(position).getDate());
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView body;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            body = itemView.findViewById(R.id.tv_message_body);
            date = itemView.findViewById(R.id.tv_date_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).getSender().equals(mCurrentUserId)) {
            return ITEM_TYPE_SENDER;
        } else {
            return ITEM_TYPE_RECEIVER;
        }
    }
}
