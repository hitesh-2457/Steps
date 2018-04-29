package com.utd.mxp165130.steps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DataProcessing dataObject;
    private RecyclerViewAdapter adapter;
    private SensorFragment sensorFragment;


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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        loadActivity();
    }

    private void loadActivity() {
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);


        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Read the data :
        dataObject = new DataProcessing();
        dataObject.readFromStepCounterDataFile(getBaseContext());
        ArrayList<StepCounterInstance> stepCounterData = dataObject.getStepCounterData();
        dataObject.readFromUserAccountDataFile(getBaseContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        dataObject.writeToStepCounterDataFile(getBaseContext());
        dataObject.writeToUserAccountDataFile(getBaseContext());
    }

    public void setAdapter(RecyclerViewAdapter recyclerAdapter) {
        adapter = recyclerAdapter;
    }

    public void setAdapterUpdate() {
        adapter.updateAdapterData();
    }

    public void startRec(View v){
        sensorFragment.startRec(v);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private static final String TAG = "MyActivity";

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "getItem: --------------------" + position + "--------------------");
            if (position == 0) {
                sensorFragment = SensorFragment.newInstance(dataObject);
                return sensorFragment;
            } else if (position == 1) {
                return HistoryFragment.newInstance(dataObject.getStepCounterData(), dataObject.getUserData(), dataObject);
            } else {
                return AccountFragment.newInstance(dataObject);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
