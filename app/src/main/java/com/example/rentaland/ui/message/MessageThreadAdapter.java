package com.example.rentaland.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.FarmModel;
import com.example.rentaland.model.UserModel;
import com.example.rentaland.ui.home.FarmAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageThreadAdapter extends RecyclerView.Adapter<MessageThreadAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<UserModel> mUserList;
    private RecyclerViewClickListener mListener;
    public MessageThreadAdapter (Context context, ArrayList<UserModel> userList,
                                 MessageThreadAdapter.RecyclerViewClickListener listener) {

        mContext = context;
        mUserList = userList;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_thread_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageThreadAdapter.ViewHolder holder, int position) {
        holder.name.setText(mUserList.get(position).getFullName());
        Picasso.get().load(mUserList.get(position).getImageUrl()).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView userImage;
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.iv_thread_image);
            name = itemView.findViewById(R.id.tv_user_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition(), mUserList.get(getAdapterPosition()));
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View v, int position, UserModel userModel);

    }
}
