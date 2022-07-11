package com.example.rentaland.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.BookModel;
import com.example.rentaland.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<BookModel> mBookList;
    private ArrayList<UserModel> mUserList;
    private RecyclerViewClickListener mListener;
    private ArrayList<String> keyList;

    public NotificationAdapter(Context context, ArrayList<BookModel> bookList,
                               RecyclerViewClickListener listener, ArrayList<UserModel> userList,
                               ArrayList<String> keyList) {
        mContext = context;
        mBookList = bookList;
        mUserList = userList;
        mListener = listener;
        this.keyList = keyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Picasso.get().load(mUserList.get(position).getImageUrl()).into(holder.mProfileImage);
        holder.mFullName.setText(mUserList.get(position).getFullName());
        holder.mAddress.setText(mUserList.get(position).getAddress());
        holder.mContact.setText(mUserList.get(position).getContactNumber());
        holder.mDate.setText(mBookList.get(position).getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mProfileImage;
        private TextView mFullName;
        private TextView mAddress;
        private TextView mContact;
        private TextView mDate;
        private Button mAccept;
        private Button mDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mProfileImage = itemView.findViewById(R.id.iv_image_by_noti);
            mFullName = itemView.findViewById(R.id.tv_name_notification);
            mAddress = itemView.findViewById(R.id.tv_address_notification);
            mContact = itemView.findViewById(R.id.tv_contact_notification);
            mDate = itemView.findViewById(R.id.tv_date_notification);
            mAccept = itemView.findViewById(R.id.btn_accept_notification);
            mDecline = itemView.findViewById(R.id.btn_decline_notification);

            mAccept.setOnClickListener(this);
            mDecline.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, keyList.get(getAdapterPosition()), getAdapterPosition());
            if (v.getId() == mAccept.getId())
            mBookList.remove(getAdapterPosition());
            mUserList.remove(getAdapterPosition());
            keyList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), mBookList.size());
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View v, String position, int adapterPosition);
    }
}
