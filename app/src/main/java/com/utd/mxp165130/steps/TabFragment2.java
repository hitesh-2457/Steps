package com.utd.mxp165130.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.widget.GridLayout.VERTICAL;

public class TabFragment2 extends Fragment {

    private static String STEP_COUNTER_DATA = "stepCounterData";
    private static String USER = "user";
    private static String DATA_OBJECT = "dataObject";
    private DataProcessing dataObject;
    private ArrayList<StepCounterInstance> stepCounterData;
    private UserAccount user;

    public static TabFragment2 newInstance(ArrayList<StepCounterInstance> stepCounterData, UserAccount user, DataProcessing dataObject) {
        TabFragment2 fragment = new TabFragment2();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEP_COUNTER_DATA, stepCounterData);
        args.putParcelable(USER, user);
        args.putParcelable(DATA_OBJECT, dataObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View rootView = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        //return rootView
        stepCounterData = getArguments().getParcelableArrayList(STEP_COUNTER_DATA);
        user = getArguments().getParcelable(USER);
        dataObject = (DataProcessing) getArguments().getParcelable(DATA_OBJECT);
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.addOnItemTouchListener(
            new ItemClickListener(getContext(), new ItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent i = new Intent(getContext(), SummaryActivity.class);
                    i.putExtra("UserObj", dataObject.getUserData());
                    i.putExtra("StepCounterObj", dataObject.getStepCounterData(position));
                    startActivity(i);
                }
            })
        );
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(stepCounterData, user);
        recyclerView.setAdapter(adapter);
        ((MainActivity) getActivity()).setAdapter(adapter);

        return recyclerView;
    }
}