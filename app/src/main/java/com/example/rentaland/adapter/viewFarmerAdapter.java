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
        return new viewFarmerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_farmer_and_farmland_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewFarmerAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvBudget.setText(list.get(position).getFarmBudget());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvAge.setText(list.get(position).getAge());
        holder.tvGender.setText(list.get(position).getGender());
        holder.tvFarmName.setText(list.get(position).getFarmlandName());
        holder.tvfarmAddress.setText(list.get(position).getFarmlandAddress());
        holder.tvBudget.setText(list.get(position).getFarmBudget());
        holder.tvArea.setText(list.get(position).getFarmArea());
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
        holder.editFarmland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemEditFarmland(list.get(position));
            }
        });
    }
    public interface itemOnClick{
        void itemClick(viewFarmerGovModel model);
        void itemDelete(viewFarmerGovModel model);
        void itemEditFarmer(viewFarmerGovModel model);
        void itemEditFarmland(viewFarmerGovModel model);
    }
    @Override
    public int getItemCount() {
        return list.size() ;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName,tvAddress,tvContact,tvAge,tvGender,tvFarmName,tvfarmAddress,tvBudget,tvArea;
        ImageView profile,farmlandImg;
        Button editFarmer,editFarmland,deleteBtn;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvArea = (TextView) itemView.findViewById(R.id.tv_farmlandArea__single);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_single);
            tvAddress=(TextView) itemView.findViewById(R.id.tv_address_single);
            tvContact=(TextView) itemView.findViewById(R.id.tv_contact_single);
            tvAge=(TextView) itemView.findViewById(R.id.tv_age_single);
            tvGender=(TextView) itemView.findViewById(R.id.tv_gender_single);
            tvFarmName=(TextView) itemView.findViewById(R.id.tv_farmlandName_single);
            tvfarmAddress=(TextView) itemView.findViewById(R.id.tv_farmlandAddress_single);
            tvBudget = (TextView) itemView.findViewById(R.id.tv_farmlandBudget_single);
            editFarmer = (Button) itemView.findViewById(R.id.brn_editProfile_single);
            editFarmland = (Button) itemView.findViewById(R.id.btn_editFarmland_single);
            profile = (ImageView) itemView.findViewById(R.id.img_profile_single);
            farmlandImg = (ImageView) itemView.findViewById(R.id.img_farmland_single);
            deleteBtn = (Button) itemView.findViewById(R.id.btn_delete_single);
        }
    }
}
