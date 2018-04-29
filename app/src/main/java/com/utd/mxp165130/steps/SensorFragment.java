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
 * This is a Fragment that is designed to handle
 * Sensor and updating the number of steps on the UI.
 *
 * This Fragment has two main functionality,
 *  1. Registering and De-registering sensor on click of play/pause button
 *  2. Update the data received from the sensor to the UI.
 *
 * The User Details and Preferences are stored in the DataProcessing object (dataObj).
 **************************/
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
    private static String DATA_OBJECT = "dataObject";
    private int initialCount = 0;
    private TextView counter;
    private TextView distance;
    private TextView dateDisplay;
    private boolean state;
    private Date currentDate;
    private Date startTime;
    private Date stopTime;
    private DataProcessing dataObject;
    private View rootView;

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Bundles the data to be passed to the Fragment
     * and returns the fragment back to the invoking function.
     *
     * @param dataObject : DataProcessing object.
     *
     * @return SensorFragment : an instance of SensorFragment
     **************************/
    public static SensorFragment newInstance(DataProcessing dataObject) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATA_OBJECT, dataObject);
        fragment.setArguments(args);
        return fragment;
    }

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * onCreateView Handler, inflates the view and handles fetching data from the bundle.
     *
     * @param inflater : LayoutInflater
     * @param container : View that is the parent of generated view
     * @param savedInstanceState : Bundle object containing arguments.
     *
     * @return View : the fragment's view.
     **************************/
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

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Function Handles the on click event on play/pause button,
     *  this function is invoked from the main activity function that handles the click event.
     *  Registers and De-registers the sensor on the button click.
     *      The sensors are registered in this function and not on onResume and onPause
     *      to be able to continue detecting step even if the activity is not visible.
     *
     * Toggles states between Play and Pause on click of the button.
     *
     * @param view : view of the click event generator.
     **************************/
    public void startRec(View view) {
        state = !state;
        //play is clicked
        if (state) {
            mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
            startTime = new Date();
            currentDate = new Date();
            dateDisplay = ((View) view.getParent()).findViewById(R.id.txtDateDisplay);
            dateDisplay.setText(dataObject.ConvertDateToString(currentDate, dataObject.getUserData().getDateFormat()));
            FloatingActionButton Btn_play_pause = rootView.findViewById(R.id.Btn_play_pause);
            Btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
            counter = ((View) view.getParent()).findViewById(R.id.txtStepsDisplay);
            distance = ((View) view.getParent()).findViewById(R.id.txtDistanceDisplay);
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

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * onSensorChanged Handler, handles updates from the Step Detector sensor and updating UI.
     *
     * @param event : Step Detector's SensorEvent object
     **************************/
    @Override
    public void onSensorChanged(SensorEvent event) {
        UserAccount user = dataObject.getUserData();
        this.initialCount += (int) event.values[0];
        this.counter.setText(String.valueOf(initialCount));
        this.distance.setText(String.format("%s %s", new DecimalFormat("0.##").format(dataObject.getDistance(user.getMetric(), user.getInches_per_step(), initialCount)), user.getMetricUnit()));
    }

    /**************************
     * onRequestDisallowInterceptTouchEvent handler, Unused
     *
     * @param sensor : sensor object.
     * @param accuracy : accuracy of the sensor.
     **************************/
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}