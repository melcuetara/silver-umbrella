package com.example.rentaland.ui.profile;

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
import com.example.rentaland.model.FarmModel;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class FarmlandAdapter extends RecyclerView.Adapter<FarmlandAdapter.ViewHolder> {

    private ArrayList<FarmModel> mFarms;
    private ArrayList<String> mFarmKeys;
    private Context mContext;
    private RecyclerViewClickListener mListener;

    public FarmlandAdapter (Context context, ArrayList<FarmModel> farms, ArrayList<String> farmKeys, RecyclerViewClickListener listener) {
        mContext = context;
        mFarms = farms;
        mFarmKeys = farmKeys;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_farmland_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmlandAdapter.ViewHolder holder, int position) {
        String farmArea = "" + mFarms.get(position).getFarmArea();
        String farmBudget = "" + mFarms.get(position).getFarmingBudget();
        holder.farmName.setText(mFarms.get(position).getFarmName());
        holder.farmAddress.setText(mFarms.get(position).getFarmAddress());
        holder.farmArea.setText(farmArea);
        holder.farmBudget.setText(farmBudget);
        Picasso.get().load(mFarms.get(position).getFarmImageUrl()).into(holder.farmImage);
        holder.btnBook.setText("Edit Farmland");
    }


    @Override
    public int getItemCount() {
        return mFarmKeys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView farmImage;
        public TextView farmName;
        public TextView farmAddress;
        public TextView farmBudget;
        public TextView farmArea;
        public Button btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            farmImage = itemView.findViewById(R.id.iv_farm_image);
            farmName = itemView.findViewById(R.id.tv_farm_name);
            farmAddress = itemView.findViewById(R.id.tv_farm_address);
            farmBudget = itemView.findViewById(R.id.tv_farm_budget);
            farmArea = itemView.findViewById(R.id.tv_farm_area);
            btnBook = itemView.findViewById(R.id.btn_book);

            btnBook.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }


    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
