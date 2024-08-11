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

import com.example.gilis_day_care.R;
import com.example.gilis_day_care.model.Kid;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class KidAdapter extends RecyclerView.Adapter<KidAdapter.KidViewHolder> {

    private Context context;
    private ArrayList<Kid> allKidsList;


    public KidAdapter(Context context, ArrayList<Kid> allKidsList) {
        this.context = context;
        this.allKidsList = allKidsList;

    }

    public void setKidList(ArrayList<Kid> kidList) {
        this.allKidsList = kidList;
        notifyDataSetChanged();
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

        if(kid.getDays().contains(1))
            holder.DayCare_kidData_CV_day1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
        if(kid.getDays().contains(2))
            holder.DayCare_kidData_CV_day2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
        if(kid.getDays().contains(3))
            holder.DayCare_kidData_CV_day3.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
        if(kid.getDays().contains(4))
            holder.DayCare_kidData_CV_day4.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));
        if(kid.getDays().contains(5))
            holder.DayCare_kidData_CV_day5.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buttonDaysColorChecked));

        if(kid.isGirl())
            holder.DayCare_kidData_LAY.setBackgroundColor(ContextCompat.getColor(context, R.color.kidGirl));




    }

    @Override
    public int getItemCount() {
        return allKidsList == null ? 0 : allKidsList.size();

    }

    private Kid getItem(int position) {

        return allKidsList.get(position);
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
        }
    }

}
