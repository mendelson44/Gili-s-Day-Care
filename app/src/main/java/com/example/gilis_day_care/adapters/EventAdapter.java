package com.example.gilis_day_care.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Interface.EventDeleteCallBack;
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
    private EventDeleteCallBack eventDeleteCallBack;
    private final OnItemClickListener listener;

    public void removeItem(int position) {
        if (position >= 0 && position < eventList.size()) {
            eventList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, eventList.size()); // Update positions of remaining items
        }
    }



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public EventAdapter(Context context, ArrayList<Event> eventList, OnItemClickListener listener) {
        this.context = context;
        this.eventList = eventList;
        this.listener = listener;

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

        holder.DayCare_rvEvent_IMG_delete.setImageResource(R.drawable.daycare_delete);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));

    }

    @Override
    public int getItemCount() {
        return eventList == null ? 0 : eventList.size();

    }


    private Event getItem(int position) {

        return eventList.get(position);
    }

    public int getItemPosition(Event event) {
        if (event == null) {
            return -1;
        }

        for (int i = 0; i < eventList.size(); i++) {
            if (event.equals(eventList.get(i))) {
                return i;
            }
        }

        return -1; // Return -1 if the item was not found
    }

    public void setEventDeleteCallBack(EventDeleteCallBack eventDeleteCallBack) {
        this.eventDeleteCallBack = eventDeleteCallBack;
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView DayCare_rvEvent_LBL_name;
        private MaterialTextView DayCare_rvEvent_LBL_txt;
        private MaterialTextView DayCare_rvEvent_LBL_time;
        private MaterialTextView DayCare_rvEvent_LBL_date;
        private ShapeableImageView DayCare_rvEvent_IMG_delete;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            DayCare_rvEvent_LBL_name = itemView.findViewById(R.id.DayCare_rvEvent_LBL_name);
            DayCare_rvEvent_LBL_txt = itemView.findViewById(R.id.DayCare_rvEvent_LBL_txt);
            DayCare_rvEvent_LBL_time = itemView.findViewById(R.id.DayCare_rvEvent_LBL_time);
            DayCare_rvEvent_LBL_date = itemView.findViewById(R.id.DayCare_rvEvent_LBL_date);
            DayCare_rvEvent_IMG_delete = itemView.findViewById(R.id.DayCare_rvEvent_IMG_delete);

            DayCare_rvEvent_IMG_delete.setOnClickListener(v -> {
                if (eventDeleteCallBack != null)
                    eventDeleteCallBack.eventDeleteButtonClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });


        }
    }

}
