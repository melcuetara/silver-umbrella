package com.example.rentaland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.viewFarmerGovModel;

import java.util.List;

public class viewFarmerAdapter extends RecyclerView.Adapter<viewFarmerAdapter.MyViewHolder> {
    itemOnClick listener;
    List<viewFarmerGovModel> list;
    public viewFarmerAdapter(List<viewFarmerGovModel> list,itemOnClick listener)
    {
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public viewFarmerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewFarmerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_farmer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewFarmerAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemDelete(list.get(position));
            }
        });
        holder.editFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemEditFarmer(list.get(position));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemClick(list.get(position));
            }
        });
    }
    public interface itemOnClick{
        void itemClick(viewFarmerGovModel model);
        void itemDelete(viewFarmerGovModel model);
        void itemEditFarmer(viewFarmerGovModel model);
    }
    @Override
    public int getItemCount() {
        return list.size() ;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName,tvAddress,tvContact;
        Button editFarmer,deleteBtn;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_farmer);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address_farmer);
            tvContact = (TextView) itemView.findViewById(R.id.tv_contact_farmer);
            editFarmer = (Button) itemView.findViewById(R.id.btn_edit_farmer);
            deleteBtn = (Button) itemView.findViewById(R.id.btn_delete_manage);
        }
    }
}
