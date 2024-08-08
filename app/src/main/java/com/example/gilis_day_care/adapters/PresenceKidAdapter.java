package com.example.gilis_day_care.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Fragments.Kid;
import com.example.gilis_day_care.Interface.KidCallBack;
import com.example.gilis_day_care.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PresenceKidAdapter extends RecyclerView.Adapter<PresenceKidAdapter.KidViewHolder> {

    private Context context;
    private ArrayList<Kid> allKidsList;
    private KidCallBack kidCallBack;


    public PresenceKidAdapter(Context context, ArrayList<Kid> allKidsList) {
        this.context = context;
        this.allKidsList = allKidsList;

    }

    @NonNull
    @Override
    public KidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_presence_kid, parent, false);
        return new KidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KidViewHolder holder, int position) {
            Kid kid = getItem(position);
        if (kid.isPresent())
            holder.DayCare_rvPresence_IMG_check.setImageResource(R.drawable.daycare_green_check);
        else
            holder.DayCare_rvPresence_IMG_check.setImageResource(R.drawable.daycare_empty_check);


        holder.DayCare_rvPresence_LBL_name.setText(kid.getName());
    }

    @Override
    public int getItemCount() {
        return allKidsList == null ? 0 : allKidsList.size();

    }

    private Kid getItem(int position) {

        return allKidsList.get(position);
    }

    public void setKidCallback(KidCallBack kidCallBack) {
        this.kidCallBack = kidCallBack;
    }

    public class KidViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView DayCare_rvPresence_LBL_name;
        private ShapeableImageView DayCare_rvPresence_IMG_check;

        public KidViewHolder(@NonNull View itemView) {
            super(itemView);

            DayCare_rvPresence_LBL_name = itemView.findViewById(R.id.DayCare_rvPresence_LBL_name);
            DayCare_rvPresence_IMG_check = itemView.findViewById(R.id.DayCare_rvPresence_IMG_check);

            DayCare_rvPresence_IMG_check.setOnClickListener(v -> {
                if (kidCallBack != null)
                    kidCallBack.presentButtonClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });

        }
    }

}
