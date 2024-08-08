package com.example.gilis_day_care.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.R;
import com.example.gilis_day_care.Fragments.Kid;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class KidAdapter extends RecyclerView.Adapter<KidAdapter.KidViewHolder> {

    private Context context;
    private ArrayList<Kid> allKidsList;


    public KidAdapter(Context context, ArrayList<Kid> allKidsList) {
        this.context = context;
        this.allKidsList = allKidsList;

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
            holder.DayCare_kidData_LBL_parent1.setText(kid.getParent1() + kid.getPhone1());
            holder.DayCare_kidData_LBL_parent2.setText(kid.getParent2() + kid.getPhone2());
            holder.DayCare_kidData_LBL_email.setText(kid.getEmail());
            holder.DayCare_kidData_LBL_allergies.setText(kid.getAllergies());
            holder.DayCare_kidData_LBL_SOS.setText(kid.getSos());
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

        public KidViewHolder(@NonNull View itemView) {
            super(itemView);

            DayCare_kidData_LBL_name = itemView.findViewById(R.id.DayCare_kidData_LBL_name);
            DayCare_kidData_LBL_class = itemView.findViewById(R.id.DayCare_kidData_LBL_class);
            DayCare_kidData_LBL_birthYear = itemView.findViewById(R.id.DayCare_kidData_LBL_birthYear);
            DayCare_kidData_LBL_email = itemView.findViewById(R.id.DayCare_kidData_LBL_email);
            DayCare_kidData_LBL_allergies = itemView.findViewById(R.id.DayCare_kidData_LBL_allergies);
            DayCare_kidData_LBL_address = itemView.findViewById(R.id.DayCare_kidData_LBL_address);
            DayCare_kidData_LBL_parent1 = itemView.findViewById(R.id.DayCare_kidData_LBL_parent1);
            DayCare_kidData_LBL_parent2 = itemView.findViewById(R.id.DayCare_kidData_LBL_parent2);
            DayCare_kidData_LBL_SOS = itemView.findViewById(R.id.DayCare_kidData_LBL_SOS);
        }
    }

}
