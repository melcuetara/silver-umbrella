package com.example.rentaland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.manageUserModel;

import java.util.List;

public class manageUserAdapter extends RecyclerView.Adapter<manageUserAdapter.MyViewHolder> {
    List<manageUserModel> list;
    itemOnClick listener;
    public manageUserAdapter(List<manageUserModel> list,itemOnClick listener)
    {
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public manageUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new manageUserAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_manage_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull manageUserAdapter.MyViewHolder holder, int position) {
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemDelete(list.get(position),position);
            }
        });
        holder.tvName.setText(list.get(position).getName());
        holder.tvUserType.setText(list.get(position).getUserType());
        holder.tvContactNum.setText(list.get(position).getContact());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listener.itemClicks(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface itemOnClick{
        void itemDelete(manageUserModel model,int pos);
        void itemClicks(manageUserModel model);
    }
    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName,tvAddress,tvContactNum,tvUserType;
        Button deleteBtn;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_report);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_contact_report);
            tvContactNum = (TextView) itemView.findViewById(R.id.tv_contact_manage);
            tvUserType =(TextView) itemView.findViewById(R.id.tv_user_type_manage);
            deleteBtn = (Button) itemView.findViewById(R.id.btn_delete_manage);
        }
    }
}
