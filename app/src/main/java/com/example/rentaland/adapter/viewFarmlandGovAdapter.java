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
import com.example.rentaland.model.viewFarmlandGovModel;

import java.util.List;

public class viewFarmlandGovAdapter extends RecyclerView.Adapter<viewFarmlandGovAdapter.MyViewHolder> {

    List<viewFarmlandGovModel> list;
    itemOnClick listener;
    public viewFarmlandGovAdapter(List<viewFarmlandGovModel> list, itemOnClick listener)
    {
        this. list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public viewFarmlandGovAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewFarmlandGovAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_farmland_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewFarmlandGovAdapter.MyViewHolder holder, int position) {
        holder.tvFarmlandSize.setText(list.get(position).getFarmlandArea());
        holder.tvFarmlandBudget.setText(list.get(position).getFarmlandBudget());
        holder.tvFarmlandName.setText(list.get(position).getFarmlandName());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemEditFarmland(list.get(position));

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemEditFarmland(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface itemOnClick{
        void itemDelete(viewFarmlandGovModel model);
        void itemEditFarmland(viewFarmlandGovModel model);
    }
    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvFarmlandName,tvFarmlandBudget,tvFarmlandSize,tvAddress;
        ImageView imageView;
        Button deleteBtn;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvFarmlandName = (TextView) itemView.findViewById(R.id.tv_farmlandName_single);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_farmlandAddress_single);
            tvFarmlandBudget = (TextView) itemView.findViewById(R.id.tv_farmlandBudget_single);
            tvFarmlandSize = (TextView) itemView.findViewById(R.id.tv_farmlandArea__single);
            imageView = (ImageView) itemView.findViewById(R.id.img_farmland_single);
            deleteBtn = (Button) itemView.findViewById(R.id.btn_delete_single);
        }
    }
}
