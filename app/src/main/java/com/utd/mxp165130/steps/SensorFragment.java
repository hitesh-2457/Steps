package com.utd.mxp165130.steps;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;

import static android.content.Context.SENSOR_SERVICE;

public class SensorFragment extends Fragment implements SensorEventListener {

    private static SensorManager mSensorManager;
    private static Sensor mStepDetector;
    private int initialCount = 0;
    private TextView counter;
    private TextView distance;
    private TextView dateDisplay;
    private boolean state;
    private Date currentDate;
    private Date startTime;
    private Date stopTime;
    private DataProcessing dataObject;
    private static String DATA_OBJECT = "dataObject";
    private View rootView;

    public static SensorFragment newInstance(DataProcessing dataObject) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATA_OBJECT, dataObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.sensor_fragment, container, false);

        dataObject = getArguments().getParcelable(DATA_OBJECT);
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        this.state = false;
        this.startTime = null;
        this.stopTime = null;
        this.currentDate = null;

        return rootView;
    }

    public void startRec(View v) {
        state = !state;
        //play is clicked
        if (state) {
            mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
            startTime = new Date();
            currentDate = new Date();
            dateDisplay = ((View) v.getParent()).findViewById(R.id.txtDateDisplay);
            dateDisplay.setText(dataObject.ConvertDateToString(currentDate, dataObject.getUserData().getDateFormat()));
            FloatingActionButton Btn_play_pause = rootView.findViewById(R.id.Btn_play_pause);
            Btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
            counter = ((View) v.getParent()).findViewById(R.id.txtStepsDisplay);
            distance = ((View) v.getParent()).findViewById(R.id.txtDistanceDisplay);
            distance.setText("0");
            counter.setVisibility(View.VISIBLE);
            dateDisplay.setVisibility(View.VISIBLE);
            distance.setVisibility(View.VISIBLE);
            this.initialCount = 0;
            counter.setText("0");

        }
        //pause is clicked
        else {
            mSensorManager.unregisterListener(this);
            dateDisplay.setVisibility(View.INVISIBLE);
            stopTime = new Date();
            FloatingActionButton Btn_play_pause = rootView.findViewById(R.id.Btn_play_pause);
            Btn_play_pause.setImageResource(android.R.drawable.ic_media_play);

            //store the data
            StepCounterInstance instance = new StepCounterInstance(currentDate, startTime, stopTime, initialCount);
            dataObject.setStepCounterData(instance);
            ((MainActivity) getActivity()).setAdapterUpdate();
            Intent i = new Intent(getContext(), SummaryActivity.class);
            i.putExtra("UserObj", dataObject.getUserData());
            i.putExtra("StepCounterObj", instance);
            startActivity(i);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        UserAccount user = dataObject.getUserData();
        this.initialCount += (int) event.values[0];
        this.counter.setText(String.valueOf(initialCount));
        this.distance.setText(String.format("%s %s", new DecimalFormat("0.##").format(dataObject.getDistance(user.getMetric(), user.getInches_per_step(), initialCount)), user.getMetricUnit()));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}