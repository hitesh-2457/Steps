package com.utd.mxp165130.steps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utd.mxp165130.steps.R;

public  class TabFragment2 extends Fragment {

    private static String DATA_OBJECT = "dataObject";
    private DataProcessing dataObject;

    public static TabFragment2 newInstance(DataProcessing dataObject) {
        TabFragment2 fragment = new TabFragment2();
        Bundle args = new Bundle();
        args.putSerializable(DATA_OBJECT, dataObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataObject = (DataProcessing) getArguments().getSerializable(DATA_OBJECT);
        recyclerView.setAdapter(new RecyclerViewAdapter(dataObject.getStepCounterData()));
        return recyclerView;

    }
}