package com.utd.mxp165130.steps;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static SensorManager mSensorManager;
    private static Sensor mStepDetector;
    private int initialCount = 0;
    private TextView counter;
    private TextView dateDisplay;
    private DataProcessing dataObject;
    //private ArrayList<StepCounterInstance>stepCounterData;
    private boolean state;
    private Date currentDate;
    private Date startTime;
    private Date stopTime;
    private RecyclerViewAdapter adapter;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            super.onCreate(savedInstanceState);
        } else {
            super.onCreate(savedInstanceState);
            loadActivity();
        }
        //Sensor
        mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        state = false;
        startTime = null;
        stopTime = null;
        currentDate = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        loadActivity();
    }

    private void loadActivity() {
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Read the data :
        dataObject = new DataProcessing();
        dataObject.readFromStepCounterDataFile(getBaseContext());
        ArrayList<StepCounterInstance> stepCounterData = dataObject.getStepCounterData();
        dataObject.readFromUserAccountDataFile(getBaseContext());

        this.counter = (TextView) findViewById(R.id.txtStepsDisplay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        dataObject.writeToStepCounterDataFile(getBaseContext());
        dataObject.writeToUserAccountDataFile(getBaseContext());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "onSensorChanged: --------------------" + String.valueOf((int) event.values[0]));
        this.initialCount += (int) event.values[0];
        counter.setText(String.valueOf(initialCount));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setAdapter(RecyclerViewAdapter radapter){
        adapter = radapter;
    }

    public void setAdapterUpdate(){adapter.updateAdapterData();}
    //Play - pause method click

    public void startRec(View v) {
        state = ! state;
        //play is clicked
        if(state){
            startTime = new Date();
            currentDate = new Date();
            dateDisplay = ((View) v.getParent()).findViewById(R.id.txtDateDisplay);
            dateDisplay.setText(dataObject.ConvertDateToString(currentDate,dataObject.getUserData().getDateFormat()));
            FloatingActionButton Btn_play_pause = (FloatingActionButton) findViewById(R.id.Btn_play_pause);
            Btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
            counter = ((View) v.getParent()).findViewById(R.id.txtStepsDisplay);
            counter.setVisibility(View.VISIBLE);
            dateDisplay.setVisibility(View.VISIBLE);
            this.initialCount = 0;
            counter.setText("0");

        }
        //pause is clicked
        else{
            dateDisplay.setVisibility(View.INVISIBLE);
            stopTime = new Date();
            FloatingActionButton Btn_play_pause = (FloatingActionButton) findViewById(R.id.Btn_play_pause);
            Btn_play_pause.setImageResource(android.R.drawable.ic_media_play);

            //store the data
            StepCounterInstance instance = new StepCounterInstance(currentDate,startTime,stopTime,initialCount);
            dataObject.setStepCounterData(instance);
            adapter.updateAdapterData();
            Intent i = new Intent(getBaseContext(),SummaryActivity.class);
            i.putExtra("UserObj",dataObject.getUserData());
            i.putExtra("StepCounterObj",instance);
            startActivity(i);
        }

    }


//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.textView8);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private static final String TAG = "MyActivity";

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "getItem: --------------------" + position + "--------------------");
            if (position == 0) {
                return TabFragment1.newInstance();
            } else if (position == 1) {
                return TabFragment2.newInstance(dataObject.getStepCounterData(),dataObject.getUserData(),dataObject);
            } else {
                return TabFragment3.newInstance(dataObject);
            }
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);
        // return TabFragment1.newInstance();

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
