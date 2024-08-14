package com.example.gilis_day_care.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Interface.EventDeleteCallBack;
import com.example.gilis_day_care.Interface.KidDeleteCallBack;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.model.Event;
import com.example.gilis_day_care.model.Kid;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class KidAdapter extends RecyclerView.Adapter<KidAdapter.KidViewHolder> {

    private Context context;
    private ArrayList<Kid> allKidsList;
    private KidDeleteCallBack kidDeleteCallBack;
    private final OnItemClickListener listener;



    public void removeItem(int position) {
        if (position >= 0 && position < allKidsList.size()) {
            allKidsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, allKidsList.size()); // Update positions of remaining items
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public KidAdapter(Context context, ArrayList<Kid> allKidsList, OnItemClickListener listener) {
        this.context = context;
        this.allKidsList = allKidsList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public KidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kid_card, parent, false);
        return new KidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KidViewHolder holder, int position) {
            Kid kid = getItem(position);
            holder.DayCare_kidData_LBL_name.setText(kid.getName());
            holder.DayCare_kidData_LBL_address.setText(kid.getAddress());
            holder.DayCare_kidData_LBL_class.setText(kid.getClassName());
            holder.DayCare_kidData_LBL_birthYear.setText(kid.getBirthYear());
            holder.DayCare_kidData_LBL_parent1.setText(kid.getParent1() + "," + kid.getPhone1());
            holder.DayCare_kidData_LBL_parent2.setText(kid.getParent2() + "," + kid.getPhone2());
            holder.DayCare_kidData_LBL_email.setText(kid.getEmail());
            holder.DayCare_kidData_LBL_allergies.setText(kid.getAllergies());
            holder.DayCare_kidData_LBL_SOS.setText(kid.getSos());

        for (String day : kid.getDays()) {
            // Split the day string by the delimiter
            String[] parts = day.split("#");

            // Check if the first part of the split contains the day value
            if (parts.length > 0) {
                String dayNum = parts[0];
                String time = parts[1];

                if (dayNum.contains("1")) {
                    holder.DayCare_kidData_CV_day1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
                    holder.DayCare_kidData_LBL_time1.setText(time);
                }
                if (dayNum.contains("2")) {
                    holder.DayCare_kidData_CV_day2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
                    holder.DayCare_kidData_LBL_time2.setText(time);
                }
                if (dayNum.contains("3")) {
                    holder.DayCare_kidData_CV_day3.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
                    holder.DayCare_kidData_LBL_time3.setText(time);
                }
                if (dayNum.contains("4")) {
                    holder.DayCare_kidData_CV_day4.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
                    holder.DayCare_kidData_LBL_time4.setText(time);
                }
                if (dayNum.contains("5")) {
                    holder.DayCare_kidData_CV_day5.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
                    holder.DayCare_kidData_LBL_time5.setText(time);
                }
            }

        }

        if(kid.isGirl()) {
            holder.DayCare_kidData_LAY.setBackgroundColor(ContextCompat.getColor(context, R.color.kidGirl));
            holder.DayCare_kidData_IMG_logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.girl));
        }
        if(!kid.isGirl()) {
            holder.DayCare_kidData_LAY.setBackgroundColor(ContextCompat.getColor(context, R.color.kidBoy));
            holder.DayCare_kidData_IMG_logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.kid));
        }


        holder.DayCare_kidData_IMG_delete.setImageResource(R.drawable.daycare_delete);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));

    }

    @Override
    public int getItemCount() {
        return allKidsList == null ? 0 : allKidsList.size();

    }

    private Kid getItem(int position) {

        return allKidsList.get(position);
    }

    public int getItemPosition(Kid kid) {
        if (kid == null) {
            return -1;
        }

        for (int i = 0; i < allKidsList.size(); i++) {
            if (kid.equals(allKidsList.get(i))) {
                return i;
            }
        }

        return -1; // Return -1 if the item was not found
    }

    public void setKidDeleteCallBack(KidDeleteCallBack kidDeleteCallBack) {
        this.kidDeleteCallBack = kidDeleteCallBack;
    }

    public class KidViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView DayCare_kidData_LBL_name;
        private MaterialTextView DayCare_kidData_LBL_class;
        private MaterialTextView DayCare_kidData_LBL_birthYear;
        private MaterialTextView DayCare_kidData_LBL_email;
        private MaterialTextView DayCare_kidData_LBL_allergies;
        private MaterialTextView DayCare_kidData_LBL_address;
        private MaterialTextView DayCare_kidData_LBL_parent1;
        private MaterialTextView DayCare_kidData_LBL_parent2;
        private MaterialTextView DayCare_kidData_LBL_SOS;
        private CardView DayCare_kidData_CV_day1;
        private CardView DayCare_kidData_CV_day2;
        private CardView DayCare_kidData_CV_day3;
        private CardView DayCare_kidData_CV_day4;
        private CardView DayCare_kidData_CV_day5;
        private RelativeLayout DayCare_kidData_LAY;
        private ShapeableImageView DayCare_kidData_IMG_logo;
        private ShapeableImageView DayCare_kidData_IMG_delete;

        private MaterialTextView DayCare_kidData_LBL_time1;
        private MaterialTextView DayCare_kidData_LBL_time2;
        private MaterialTextView DayCare_kidData_LBL_time3;
        private MaterialTextView DayCare_kidData_LBL_time4;
        private MaterialTextView DayCare_kidData_LBL_time5;

        public KidViewHolder(@NonNull View itemView) {
            super(itemView);

            DayCare_kidData_LAY = itemView.findViewById(R.id.DayCare_kidData_LAY);
            DayCare_kidData_LBL_name = itemView.findViewById(R.id.DayCare_kidData_LBL_name);
            DayCare_kidData_LBL_class = itemView.findViewById(R.id.DayCare_kidData_LBL_class);
            DayCare_kidData_LBL_birthYear = itemView.findViewById(R.id.DayCare_kidData_LBL_birthYear);
            DayCare_kidData_LBL_email = itemView.findViewById(R.id.DayCare_kidData_LBL_email);
            DayCare_kidData_LBL_allergies = itemView.findViewById(R.id.DayCare_kidData_LBL_allergies);
            DayCare_kidData_LBL_address = itemView.findViewById(R.id.DayCare_kidData_LBL_address);
            DayCare_kidData_LBL_parent1 = itemView.findViewById(R.id.DayCare_kidData_LBL_parent1);
            DayCare_kidData_LBL_parent2 = itemView.findViewById(R.id.DayCare_kidData_LBL_parent2);
            DayCare_kidData_LBL_SOS = itemView.findViewById(R.id.DayCare_kidData_LBL_SOS);
            DayCare_kidData_CV_day1 = itemView.findViewById(R.id.DayCare_kidData_CV_day1);
            DayCare_kidData_CV_day2 = itemView.findViewById(R.id.DayCare_kidData_CV_day2);
            DayCare_kidData_CV_day3 = itemView.findViewById(R.id.DayCare_kidData_CV_day3);
            DayCare_kidData_CV_day4 = itemView.findViewById(R.id.DayCare_kidData_CV_day4);
            DayCare_kidData_CV_day5 = itemView.findViewById(R.id.DayCare_kidData_CV_day5);
            DayCare_kidData_IMG_logo = itemView.findViewById(R.id.DayCare_kidData_IMG_logo);
            DayCare_kidData_IMG_delete = itemView.findViewById(R.id.DayCare_kidData_IMG_delete);

            DayCare_kidData_LBL_time1 = itemView.findViewById(R.id.DayCare_kidData_LBL_time1);
            DayCare_kidData_LBL_time2 = itemView.findViewById(R.id.DayCare_kidData_LBL_time2);
            DayCare_kidData_LBL_time3 = itemView.findViewById(R.id.DayCare_kidData_LBL_time3);
            DayCare_kidData_LBL_time4 = itemView.findViewById(R.id.DayCare_kidData_LBL_time4);
            DayCare_kidData_LBL_time5 = itemView.findViewById(R.id.DayCare_kidData_LBL_time5);

            DayCare_kidData_IMG_delete.setOnClickListener(v -> {
                if (kidDeleteCallBack != null)
                    kidDeleteCallBack.kidDeleteButtonClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });
        }
    }

}
