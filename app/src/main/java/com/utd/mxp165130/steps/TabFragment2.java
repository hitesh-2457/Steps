package com.utd.mxp165130.steps;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.utd.mxp165130.steps.R;

import java.util.ArrayList;

import static android.widget.GridLayout.HORIZONTAL;
import static android.widget.GridLayout.VERTICAL;

public  class TabFragment2 extends Fragment {

    private static String STEP_COUNTER_DATA = "stepCounterData";
    private static String USER = "user";
    private ArrayList<StepCounterInstance> stepCounterData;
    private UserAccount user;

    public static TabFragment2 newInstance(ArrayList<StepCounterInstance> stepCounterData, UserAccount user) {
        TabFragment2 fragment = new TabFragment2();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEP_COUNTER_DATA, stepCounterData);
        args.putParcelable(USER,user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // View rootView = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        //return rootView
        stepCounterData = getArguments().getParcelableArrayList(STEP_COUNTER_DATA);
        user = getArguments().getParcelable(USER);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(stepCounterData,user);
        recyclerView.setAdapter(adapter);
        ((MainActivity) getActivity()).setAdapter(adapter);

        return recyclerView;
    }
}