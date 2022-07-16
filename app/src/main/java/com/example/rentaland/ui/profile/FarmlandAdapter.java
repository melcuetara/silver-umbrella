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


import java.util.ArrayList;

public class FarmlandAdapter extends RecyclerView.Adapter<FarmlandAdapter.ViewHolder> {

    private ArrayList<FarmModel> mFarms;
    private ArrayList<String> mFarmKeys;
    private Context mContext;
    private RecyclerViewClickListener mListener;

    public FarmlandAdapter (Context context, ArrayList<FarmModel> farms, ArrayList<String> farmKeys) {
        mContext = context;
        mFarms = farms;
        mFarmKeys = farmKeys;
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
