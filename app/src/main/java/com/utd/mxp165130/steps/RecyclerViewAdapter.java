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
            StepCounterDate = (TextView) view.findViewById(R.id.txtHistoryDate);
            StarTime = (TextView) view.findViewById(R.id.txtHistoryStartTime);
            EndTime = (TextView) view.findViewById(R.id.txtHistoryStopTime);
            noOfSteps = (TextView) view.findViewById(R.id.txtHistoryNoOfSteps);
            Distance = (TextView) view.findViewById(R.id.txtHistoryDistance);

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

//        holder.StepCounterDate.setText("DATE");
//        holder.StarTime.setText("Start Time");
//        holder.EndTime.setText("End Time");
//        holder.noOfSteps.setText("Steps");
        StepCounterInstance instance = items.get(position);
        // holder.Distance.setText("Distance");
        holder.StepCounterDate.setText("Date: " + instance.ConvertDateToDateString(instance.getStepCounterInstanceDate(), userAccount.getDateFormat()));
        holder.StarTime.setText("Start Time: " + instance.ConvertDateToTimeString(instance.getStartTime(), userAccount.getDateFormat()));
        holder.EndTime.setText("End Time: " + instance.ConvertDateToTimeString(instance.getEndTime(), userAccount.getDateFormat()));
        holder.noOfSteps.setText("Number of Steps: " + String.valueOf(instance.getNoOfSteps()));
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






