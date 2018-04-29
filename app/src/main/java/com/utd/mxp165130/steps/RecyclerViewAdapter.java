/**************************
 * Step Counter program
 * Class : CS6326.001
 * Spring 2018
 *
 * Coder 1:
 * 	Hitesh Gupta Tumsi Ramesh
 *   netId: hxg170230
 * Coder 2:
 * 	Meghana Pochiraju
 * 	netId: mxp165130
 *
 * Recycler View Adapter:
 * - Handles Adapter, View Holder and Binders.
 * - Contains HistoryViewHolder class.
 *
 * HistoryViewHolder class:
 * - View holders of the Recycler View's Entries.
 *
 **************************/
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

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * Instantiates a new Recycler view adapter.
     *
     * @param stepCounterInstancesList : the step counter instances list
     * @param user : the user
     **************************/
    public RecyclerViewAdapter(ArrayList<StepCounterInstance> stepCounterInstancesList, UserAccount user) {
        this.items = stepCounterInstancesList;
        this.userAccount = user;
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * onCreateViewHolder Handler:
     *  handles creating instance of entry in recycler view.
     *
     * @param parent : the view group to which the entries are bound.
     * @param viewType : the type of view
     *
     * @return HistoryViewHolder : instance of HistoryViewHolder to be added.
     **************************/
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);
        return new HistoryViewHolder(itemView);
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * onBindViewHolder handler, Binds the view holder with the data.
     *
     * @param holder : the HistoryViewHolder object corresponding to the entry
     * @param position : the position of the entry
     **************************/
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        StepCounterInstance instance = items.get(position);
        holder.StepCounterDate.setText(String.format("Date: %s", instance.ConvertDateToDateString(instance.getStepCounterInstanceDate(), userAccount.getDateFormat())));
        holder.StarTime.setText(String.format("Start Time: %s", instance.ConvertDateToTimeString(instance.getStartTime())));
        holder.EndTime.setText(String.format("End Time: %s", instance.ConvertDateToTimeString(instance.getEndTime())));
        holder.noOfSteps.setText(String.format("Number of Steps: %s", String.valueOf(instance.getNoOfSteps())));
        holder.Distance.setText(String.format("Distance Travelled: %s %s", String.valueOf(new DecimalFormat("0.##").format(instance.getDistance(userAccount.getMetric(), userAccount.getInches_per_step()))), userAccount.getMetricUnit()));
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * Getter to fetch the count of items in the Recycler View.
     **************************/
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * Updates adapter data with sorted list of items.
     **************************/
    public void updateAdapterData() {
        Collections.sort(this.items);
        this.notifyDataSetChanged();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView StepCounterDate, StarTime, EndTime, noOfSteps, Distance;

        /**************************
         * Instantiates a new History view holder.
         *
         * @param view the view
         **************************/
        public HistoryViewHolder(View view) {
            super(view);
            StepCounterDate = view.findViewById(R.id.txtHistoryDate);
            StarTime = view.findViewById(R.id.txtHistoryStartTime);
            EndTime = view.findViewById(R.id.txtHistoryStopTime);
            noOfSteps = view.findViewById(R.id.txtHistoryNoOfSteps);
            Distance = view.findViewById(R.id.txtHistoryDistance);
        }
    }
}
