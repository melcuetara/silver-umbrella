package com.example.rentaland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.viewReportModel;

import java.util.List;

public class viewReportAdapter extends RecyclerView.Adapter<viewReportAdapter.MyViewHolder> {
    List<viewReportModel> list;
    itemClickListener listener;
    public viewReportAdapter(List<viewReportModel> list,itemClickListener listener)
    {
       this.list = list;
       this.listener = listener;
    }
    @NonNull
    @Override
    public viewReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_report,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull viewReportAdapter.MyViewHolder holder, int position) {
        holder.etName.setText(list.get(position).getName());
        holder.etMessage.setText(list.get(position).getBody());
        holder.etNumber.setText(list.get(position).getContactNumber());
        holder.etUserType.setText(list.get(position).getUserType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface itemClickListener{
        void itemOnClick(viewReportModel model);
    }
    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView etName,etNumber,etUserType,etMessage;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            etName = (TextView) itemView.findViewById(R.id.tv_name_report);
            etNumber = (TextView) itemView.findViewById(R.id.tv_contact_report);
            etUserType = (TextView) itemView.findViewById(R.id.tv_user_type_report);
            etMessage = (TextView) itemView.findViewById(R.id.tv_message_report);
        }
    }
}
