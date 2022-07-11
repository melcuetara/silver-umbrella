package com.example.rentaland.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaland.R;
import com.example.rentaland.model.FarmModel;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.ViewHolder> {

    Context context;
    ArrayList<FarmModel> farmModels;
    ArrayList<Boolean> isBooking;
    private RecyclerViewClickListener listener;

    public FarmAdapter(Context context, ArrayList<FarmModel> farmModels,
                       RecyclerViewClickListener listener, ArrayList<Boolean> isBooking) {
        this.context = context;
        this.farmModels = farmModels;
        this.isBooking = isBooking;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_farmland_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FarmAdapter.ViewHolder holder, int position) {
        String farmArea = "" + farmModels.get(position).getFarmArea();
        String farmBudget = "" + farmModels.get(position).getFarmingBudget();
        holder.farmName.setText(farmModels.get(position).getFarmName());
        holder.farmAddress.setText(farmModels.get(position).getFarmAddress());
        holder.farmArea.setText(farmArea);
        holder.farmBudget.setText(farmBudget);
        Picasso.get().load(farmModels.get(position).getFarmImageUrl()).into(holder.farmImage);
        if (isBooking.get(position)) {
            holder.btnBook.setText("Book Request Sent");
            holder.btnBook.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return farmModels.size();
    }

    public interface RecyclerViewClickListener {

        void onClick(View v, int position, FarmModel farmModel);
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition(), farmModels.get(getAdapterPosition()));
        }
    }
}
