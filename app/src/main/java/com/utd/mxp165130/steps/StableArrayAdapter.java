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
import java.util.EnumSet;
import java.util.List;


public class StableArrayAdapter extends ArrayAdapter<StepCounterInstance>
{
    List<StepCounterInstance> items;
    UserAccount userAccount;
    /**
     * Constructor to initialize the data store
     * @param context
     * @param items
     */
    public StableArrayAdapter(Context context,
                              List<StepCounterInstance> items, UserAccount user)
    {
        super(context, 0, items);
        this.items = items;
        this.userAccount = user;

    }

    /**
     * This overridden function is called for each line in the list.
     * @param position
     * @param cvtView
     * @param parent
     * @return
     */

    @Override
    public View getView(int position, View cvtView, ViewGroup parent)
    {
        TextView StepCounterDate, StarTime, EndTime, noOfSteps, Distance;

        int width = parent.getWidth();
        Context cx = this.getContext();
        LayoutInflater inflater = (LayoutInflater) cx.getSystemService(cx.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.history_row, parent, false);
        StepCounterInstance instance = items.get(position);
        StepCounterDate = (TextView) rowView.findViewById(R.id.textView3);
        StarTime = (TextView) rowView.findViewById(R.id.textView4);
        EndTime = (TextView) rowView.findViewById(R.id.textView5);
        noOfSteps = (TextView) rowView.findViewById(R.id.textView6);
        StepCounterDate.setText(instance.ConvertDateToString(instance.getStepCounterInstanceDate(),userAccount.getDateFormat()));
        StarTime.setText(instance.ConvertDateToString(instance.getStartTime(),userAccount.getDateFormat()));
        EndTime.setText(instance.ConvertDateToString(instance.getEndTime(),userAccount.getDateFormat()));
        noOfSteps.setText(String.valueOf(instance.getNoOfSteps()));

        return rowView;
    }

    /**
     * A utility method to set notifyDatasetchanged event
     * @param items
     */
    public void updateAdapterData(List<StepCounterInstance> items){
        this.items.clear();
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }

}

/**
 * public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryViewHolder> {

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

 */

