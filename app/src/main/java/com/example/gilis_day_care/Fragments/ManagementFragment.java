package com.example.gilis_day_care.Fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gilis_day_care.Activities.MainActivity;
import com.example.gilis_day_care.R;
import com.example.gilis_day_care.Utilities.MyFireBase;
import com.example.gilis_day_care.adapters.EventAdapter;
import com.example.gilis_day_care.adapters.PresenceKidAdapter;
import com.example.gilis_day_care.model.Event;
import com.example.gilis_day_care.model.Kid;
import com.example.gilis_day_care.model.Manager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class ManagementFragment extends Fragment {

    private CardView DayCare_management_CV_background;
    private MaterialButton DayCare_management_BTN_event;
    private MaterialButton DayCare_management_BTN_activity;
    private RecyclerView DayCare_event_RV;
    private ArrayList<Event> eventsList;
    private EventAdapter adapter;

    private MaterialTextView DayCare_management_progressBar_LBL_countEvents;
    private ProgressBar DayCare_management_progressBar_event;
    private MaterialTextView DayCare_management_progressBar_LBL_countActivities;
    private ProgressBar DayCare_management_progressBar_activity;
    private OnItemRemovedCallback onItemRemovedCallback;
    private MyFireBase fireBase;
    private Manager manager;

    public ManagementFragment () {

        this.manager = Manager.getInstance();
        this.fireBase = new MyFireBase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_management, container, false);
        this.eventsList = manager.getEventsList();
        findViews(view);
        initRecyclerView();  // Initialize RecyclerView
        UpdateEventProgressBar();

        DayCare_management_BTN_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animator animator;

                if(DayCare_management_BTN_event.isChecked()) {
                    // For slide down
                    animator = AnimatorInflater.loadAnimator(v.getContext(), R.animator.slide_down);
                    animator.setTarget(DayCare_management_CV_background);  // Set the target view for the animation
                    animator.start();  // Start the animation
                }
                else {
                    // For slide down
                    animator = AnimatorInflater.loadAnimator(v.getContext(), R.animator.slide_up);
                    animator.setTarget(DayCare_management_CV_background);  // Set the target view for the animation
                    animator.start();  // Start the animation
                }

            }

        });

        // Set up click listener for slide button food layout
        DayCare_management_BTN_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animator animator;

                if(DayCare_management_BTN_activity.isChecked()) {
                    // For slide down
                    animator = AnimatorInflater.loadAnimator(v.getContext(), R.animator.slide_down);
                    animator.setTarget(DayCare_management_CV_background);  // Set the target view for the animation
                    animator.start();  // Start the animation
                }
                else {
                    // For slide down
                    animator = AnimatorInflater.loadAnimator(v.getContext(), R.animator.slide_up);
                    animator.setTarget(DayCare_management_CV_background);  // Set the target view for the animation
                    animator.start();  // Start the animation
                }
            }

        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void findViews(View view) {

        DayCare_management_CV_background = view.findViewById(R.id.DayCare_management_CV_background);
        DayCare_management_BTN_event = view.findViewById(R.id.DayCare_management_BTN_event);
        DayCare_management_BTN_activity = view.findViewById(R.id.DayCare_management_BTN_activity);
        DayCare_event_RV = view.findViewById(R.id.DayCare_event_RV);

        DayCare_management_progressBar_LBL_countEvents = view.findViewById(R.id.DayCare_management_progressBar_LBL_countEvents);
        DayCare_management_progressBar_event = view.findViewById(R.id.DayCare_management_progressBar_event);
        DayCare_management_progressBar_LBL_countActivities = view.findViewById(R.id.DayCare_management_progressBar_LBL_countActivities);
        DayCare_management_progressBar_activity = view.findViewById(R.id.DayCare_management_progressBar_activity);

    }

    private void initRecyclerView() {

        adapter = new EventAdapter(getActivity().getApplicationContext(), eventsList, position -> {
            // Trigger swipe animation on item click
            RecyclerView.ViewHolder viewHolder = DayCare_event_RV.findViewHolderForAdapterPosition(position);
            if (viewHolder != null) {
                View view = viewHolder.itemView;
                Animator animator;
                if(view.getTranslationX() == 0) {
                    animator = AnimatorInflater.loadAnimator(this.getContext(), R.animator.slide_left);
                    view.findViewById(R.id.DayCare_rvEvent_IMG_delete).setVisibility(View.VISIBLE);
                }

                else {
                    animator = AnimatorInflater.loadAnimator(this.getContext(), R.animator.slide_right);
                    view.findViewById(R.id.DayCare_rvEvent_IMG_delete).setVisibility(View.INVISIBLE);
                }
                animator.setTarget(view);  // Set the target view for the animation
                animator.start();  // Start the animation

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DayCare_event_RV.setLayoutManager(linearLayoutManager);
        DayCare_event_RV.setAdapter(adapter);
        adapter.setEventDeleteCallBack((event, position) -> {
            // Get the ViewHolder for the given position
            RecyclerView.ViewHolder viewHolder = DayCare_event_RV.findViewHolderForAdapterPosition(position);

            if (viewHolder != null) {
                View view = viewHolder.itemView;

                // Load and set the animation
                Animator animator = AnimatorInflater.loadAnimator(this.getContext(), R.animator.slide_right_delete);
                animator.setTarget(view);
                animator.start();

                // Add a listener to remove the item after the animation ends
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Remove the item from the adapter's data source
                        Log.d("ManagementFragment", "event remove" + event.toString() + " , position : " +  position );
                        if (onItemRemovedCallback != null) {
                            onItemRemovedCallback.onItemRemoved(eventsList.get(position),position);
                        }
                        adapter.removeItem(position);
                        UpdateEventProgressBar();
                    }
                });
            } else {
                // Handle the case where the ViewHolder is null
                //adapter.removeItem(position);
            }
        });

        Log.d("ManagementFragment", "RecyclerView initialized with adapter.");
    }

    private void UpdateEventProgressBar() {

        DayCare_management_progressBar_event.setProgress(( 100 - eventsList.size()));
        DayCare_management_progressBar_LBL_countEvents.setText(String.valueOf(eventsList.size()));
    }

    public void setOnItemRemovedCallback(OnItemRemovedCallback callback) {
        this.onItemRemovedCallback = callback;
    }

    public interface OnItemRemovedCallback {
        void onItemRemoved(Event event,int pos);
    }

}


