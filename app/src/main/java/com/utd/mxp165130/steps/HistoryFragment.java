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
 * This is a Fragment that is designed to handle the History.
 *
 * This Fragment handles the Recycler View.
 *  - The Recycler View is created on the fly and does not have any xml.
 *  - Binds custom on touch listener as on item click listener to the recycler view.
 *
 * The History is stored as an array list of StepCounterInstance in stepCounterData
 **************************/
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
import java.util.Collections;

import static android.widget.GridLayout.VERTICAL;

public class HistoryFragment extends Fragment {

    private static String STEP_COUNTER_DATA = "stepCounterData";
    private static String USER = "user";
    private static String DATA_OBJECT = "dataObject";
    private DataProcessing dataObject;
    private ArrayList<StepCounterInstance> stepCounterData;
    private UserAccount user;

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * Bundles the data to be passed to the Fragement
     * and returns the fragment back to the invoking function.
     *
     * @param stepCounterData : ArrayList of StepCounterInstances.
     * @param user : UserAccount object containing User details and Preferences.
     * @param dataObject : DataProcessing object.
     *
     * @return HistoryFragment : an instance of HistoryFragment
     **************************/
    public static HistoryFragment newInstance(ArrayList<StepCounterInstance> stepCounterData, UserAccount user, DataProcessing dataObject) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEP_COUNTER_DATA, stepCounterData);
        args.putParcelable(USER, user);
        args.putParcelable(DATA_OBJECT, dataObject);
        fragment.setArguments(args);
        return fragment;
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * onCreateView Handler,
     *  It creates Recycler view on fly and populates the view.
     *  It binds custom ItemClickListener as onItemTouchListener.
     *
     * @param inflater : LayoutInflater
     * @param container : View that is the parent of generated view
     * @param savedInstanceState : Bundle object containing arguments.
     *
     * @return View : the fragment's view.
     **************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stepCounterData = getArguments().getParcelableArrayList(STEP_COUNTER_DATA);
        user = getArguments().getParcelable(USER);
        dataObject = getArguments().getParcelable(DATA_OBJECT);
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
        Collections.sort(stepCounterData);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(stepCounterData, user);
        recyclerView.setAdapter(adapter);
        ((MainActivity) getActivity()).setAdapter(adapter);

        return recyclerView;
    }
}