package com.utd.mxp165130.steps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryViewHolder> {

    private ArrayList<StepCounterInstance> items;
    private UserAccount userAccount;

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView StepCounterDate, StarTime, EndTime, noOfSteps, Distance;

        public HistoryViewHolder(View view) {
            super(view);
            StepCounterDate = view.findViewById(R.id.txtHistoryDate);
            StarTime = view.findViewById(R.id.txtHistoryStartTime);
            EndTime = view.findViewById(R.id.txtHistoryStopTime);
            noOfSteps = view.findViewById(R.id.txtHistoryNoOfSteps);
            Distance = view.findViewById(R.id.txtHistoryDistance);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public RecyclerViewAdapter(ArrayList<StepCounterInstance> stepCounterInstancesList, UserAccount user) {
        this.items = stepCounterInstancesList;
        this.userAccount = user;

    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        StepCounterInstance instance = items.get(position);
        holder.StepCounterDate.setText(String.format("Date: %s", instance.ConvertDateToDateString(instance.getStepCounterInstanceDate(), userAccount.getDateFormat())));
        holder.StarTime.setText(String.format("Start Time: %s", instance.ConvertDateToTimeString(instance.getStartTime())));
        holder.EndTime.setText(String.format("End Time: %s", instance.ConvertDateToTimeString(instance.getEndTime())));
        holder.noOfSteps.setText(String.format("Number of Steps: %s", String.valueOf(instance.getNoOfSteps())));
        holder.Distance.setText(String.format("Distance Travelled: %s %s", String.valueOf(new DecimalFormat("0.##").format(instance.getDistance(userAccount.getMetric(), userAccount.getInches_per_step()))), userAccount.getMetricUnit()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateAdapterData() {
        Collections.sort(this.items);
        this.notifyDataSetChanged();
    }
}






