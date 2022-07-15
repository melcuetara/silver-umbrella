package com.example.rentaland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.manageFeedbackModel;
import com.example.rentaland.model.viewReportModel;

import java.util.List;

public class viewFeedbackAdapter extends RecyclerView.Adapter<viewFeedbackAdapter.MyViewHolder> {
    List<manageFeedbackModel> list;
    viewReportAdapter.itemClickListener listener;
    public viewFeedbackAdapter(List<manageFeedbackModel> list, viewReportAdapter.itemClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public viewFeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewFeedbackAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_report,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
