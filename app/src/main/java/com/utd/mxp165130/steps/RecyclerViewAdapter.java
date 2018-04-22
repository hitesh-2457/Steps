package com.utd.mxp165130.steps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    //private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView3, textView4, textView5, textView6, textView7;

        public MyViewHolder(View view) {
            super(view);
            textView3 = (TextView) view.findViewById(R.id.textView3);
            textView4 = (TextView) view.findViewById(R.id.textView4);
            textView5 = (TextView) view.findViewById(R.id.textView5);
            textView6 = (TextView) view.findViewById(R.id.textView6);
            textView7 = (TextView) view.findViewById(R.id.textView7);
        }
    }


    public RecyclerViewAdapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Movie movie = moviesList.get(position);
        holder.textView3.setText("DATE");
        holder.textView4.setText("Start Time");
        holder.textView5.setText("End Time");
        holder.textView6.setText("Steps");
        holder.textView7.setText("Distance");
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}