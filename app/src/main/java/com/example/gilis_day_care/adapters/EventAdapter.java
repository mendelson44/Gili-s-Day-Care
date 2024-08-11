package com.example.gilis_day_care.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Interface.KidCallBack;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.model.Event;
import com.example.gilis_day_care.model.Kid;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private ArrayList<Event> eventList;


    public EventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;

    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            Event event = getItem(position);

        holder.DayCare_rvEvent_LBL_name.setText(event.getName());
        holder.DayCare_rvEvent_LBL_txt.setText(event.getText());
        holder.DayCare_rvEvent_LBL_time.setText(event.getTime());
        holder.DayCare_rvEvent_LBL_date.setText(event.getDate());

    }

    @Override
    public int getItemCount() {
        return eventList == null ? 0 : eventList.size();

    }

    private Event getItem(int position) {

        return eventList.get(position);
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView DayCare_rvEvent_LBL_name;
        private MaterialTextView DayCare_rvEvent_LBL_txt;
        private MaterialTextView DayCare_rvEvent_LBL_time;
        private MaterialTextView DayCare_rvEvent_LBL_date;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            DayCare_rvEvent_LBL_name = itemView.findViewById(R.id.DayCare_rvEvent_LBL_name);
            DayCare_rvEvent_LBL_txt = itemView.findViewById(R.id.DayCare_rvEvent_LBL_txt);
            DayCare_rvEvent_LBL_time = itemView.findViewById(R.id.DayCare_rvEvent_LBL_time);
            DayCare_rvEvent_LBL_date = itemView.findViewById(R.id.DayCare_rvEvent_LBL_date);


        }
    }

}
