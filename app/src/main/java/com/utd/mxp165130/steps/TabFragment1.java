package com.utd.mxp165130.steps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utd.mxp165130.steps.R;

public  class TabFragment1 extends Fragment {

    public static TabFragment1 newInstance() {
        TabFragment1 fragment = new TabFragment1();
        Bundle args = new Bundle();
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}