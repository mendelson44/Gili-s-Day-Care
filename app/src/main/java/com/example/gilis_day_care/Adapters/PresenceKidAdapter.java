package com.example.gilis_day_care.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Model.Kid;
import com.example.gilis_day_care.Interface.KidCallBack;
import com.example.gilis_day_care.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Comparator;

public class PresenceKidAdapter extends RecyclerView.Adapter<PresenceKidAdapter.KidViewHolder> {

    private Context context;
    private ArrayList<Kid> allKidsList;
    private KidCallBack kidCallBack;
    private int currentDay;


    public PresenceKidAdapter(Context context, ArrayList<Kid> allKidsList, int currentDay) {
        this.context = context;
        this.allKidsList = allKidsList;
        this.currentDay = currentDay;

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
        if (kid.getState() == 0)
            holder.DayCare_rvPresence_IMG_check.setImageResource(R.drawable.daycare_empty_check);

        else
            holder.DayCare_rvPresence_IMG_check.setImageResource(R.drawable.daycare_green_check);

        if (kid.isLate())
            holder.DayCare_rvPresence_LBL_time.setTextColor(ContextCompat.getColor(context, R.color.red));

        else
            holder.DayCare_rvPresence_LBL_time.setTextColor(ContextCompat.getColor(context, R.color.kidGirlChecked));


        for (String day : kid.getDays()) {
            // Split the day string by the delimiter
            String[] parts = day.split("#");

            // Check if the first part of the split contains the day value
            if (parts.length > 0) {
                String dayNum = parts[0];
                String time = parts[1];
                if(dayNum.equals(String.valueOf(currentDay)))
                    holder.DayCare_rvPresence_LBL_time.setText(time);
            }
        }
        holder.DayCare_rvPresence_LBL_name.setText(kid.getName());
    }

    @Override
    public int getItemCount() {
        return allKidsList == null ? 0 : allKidsList.size();

    }

    private Kid getItem(int position) {

        return allKidsList.get(position);
    }

    public void sortKidsByTime(int currentDay) {
        if (allKidsList != null) {
            // Sort the list by time
            allKidsList.sort(new Comparator<Kid>() {
                @Override
                public int compare(Kid k1, Kid k2) {
                    String time1 = getTimeForDay(k1, currentDay);
                    String time2 = getTimeForDay(k2, currentDay);

                    // Compare based on time
                    return time1.compareTo(time2);
                }

                private String getTimeForDay(Kid kid, int day) {
                    for (String dayTime : kid.getDays()) {
                        String[] parts = dayTime.split("#");
                        if (parts.length > 1 && Integer.parseInt(parts[0]) == day) {
                            return parts[1];
                        }
                    }
                    return "00:00"; // Default time if not found
                }
            });
            notifyDataSetChanged(); // Notify adapter after sorting
        }
    }

    public void setKidCallback(KidCallBack kidCallBack) {
        this.kidCallBack = kidCallBack;
    }

    public class KidViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView DayCare_rvPresence_LBL_name;
        private MaterialTextView DayCare_rvPresence_LBL_time;
        private ShapeableImageView DayCare_rvPresence_IMG_check;

        public KidViewHolder(@NonNull View itemView) {
            super(itemView);

            DayCare_rvPresence_LBL_name = itemView.findViewById(R.id.DayCare_rvPresence_LBL_name);
            DayCare_rvPresence_LBL_time = itemView.findViewById(R.id.DayCare_rvPresence_LBL_time);
            DayCare_rvPresence_IMG_check = itemView.findViewById(R.id.DayCare_rvPresence_IMG_check);

            DayCare_rvPresence_IMG_check.setOnClickListener(v -> {
                if (kidCallBack != null)
                    kidCallBack.presentButtonClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });

        }
    }

}
