package com.utd.mxp165130.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryViewHolder> {

    private ArrayList<StepCounterInstance> stepCounterInstancesList;

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView StepCounterDate, StarTime, EndTime, noOfSteps, Distance;

        public HistoryViewHolder(View view) {
            super(view);
            StepCounterDate = (TextView) view.findViewById(R.id.textView3);
            StarTime = (TextView) view.findViewById(R.id.textView4);
            EndTime = (TextView) view.findViewById(R.id.textView5);
            noOfSteps = (TextView) view.findViewById(R.id.textView6);
            Distance = (TextView) view.findViewById(R.id.textView7);
        }
    }


    public RecyclerViewAdapter(ArrayList<StepCounterInstance> stepCounterInstancesList) {
        this.stepCounterInstancesList = stepCounterInstancesList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);

        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.StepCounterDate.setText("DATE");
        holder.StarTime.setText("Start Time");
        holder.EndTime.setText("End Time");
        holder.noOfSteps.setText("Steps");
        holder.Distance.setText("Distance");
    }

    @Override
    public int getItemCount() {
        return stepCounterInstancesList.size() ;
    }
}
