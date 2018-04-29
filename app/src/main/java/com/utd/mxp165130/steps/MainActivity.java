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
 * The Launcher Activity designed and built over the default TabLayout provided by android studio.
 * - Handles permission request to write to the external storage.
 * - Handles the TabLayout and Fragment Binding.
 * - Using FragmentPagerAdapter which save the fragments that are loaded.
 * - Configured it to load all the three fragments.
 *
 * Stores the User data and history in DataProcessing object (dataObject).
 **************************/
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

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * onCrete handler
     *  - Requests for permission and waits before continuing.
     *
     * @param savedInstanceState : Bundle object
     **************************/
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

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * onCrete handler
     *  - Requests for permission and waits before continuing.
     *
     * @param requestCode : requestCode, expected return code.
     * @param permissions : Array of permissions requested
     * @param grantResults : Array of permissions granted
     **************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        loadActivity();
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * - The function that is invoked once permissions are received inflates the view to the Main Activity.
     * - Reads data from File and handles setting the adapter to the tabLayout.
     **************************/
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

        //Read data from file
        dataObject = new DataProcessing();
        dataObject.readFromStepCounterDataFile(getBaseContext());
        ArrayList<StepCounterInstance> stepCounterData = dataObject.getStepCounterData();
        dataObject.readFromUserAccountDataFile(getBaseContext());
    }

    /**************************
     * onRequestDisallowInterceptTouchEvent handler, Unused
     *
     * @param menu : Menu object.
     **************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * onRequestDisallowInterceptTouchEvent handler, Unused
     **************************/
    @Override
    public void onStop() {
        super.onStop();
        dataObject.writeToStepCounterDataFile(getBaseContext());
        dataObject.writeToUserAccountDataFile(getBaseContext());
    }

    /****************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * Sets adapter.
     *
     * @param recyclerAdapter the recycler adapter
     ****************************/
    public void setAdapter(RecyclerViewAdapter recyclerAdapter) {
        adapter = recyclerAdapter;
    }

    /****************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * Sets adapter update.
     ****************************/
    public void setAdapterUpdate() {
        adapter.updateAdapterData();
    }

    /****************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Click Handler on the play/pause button
     *
     * @param view the view on which the handler is called.
     ****************************/
    public void startRec(View view) {
        sensorFragment.startRec(view);
    }

    /****************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * A FragmentPagerAdapter that returns a fragment corresponding to the tab.
     ****************************/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final String TAG = "MyActivity";

        /****************************
         * Coder: Meghana Pochiraju (mxp165130)
         *
         * Instantiates a new Sections pager adapter.
         *
         * @param fm the fm
         ****************************/
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /****************************
         * Coder: Meghana Pochiraju (mxp165130)
         *
         * getItem is called to instantiate the fragment for the given page.
         *
         * @param position : the position of the fragment
         * @return Fragment : Return a PlaceholderFragment
         ****************************/
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

        /****************************
         * Coder: Meghana Pochiraju (mxp165130)
         *
         * Getter for the count of fragments.
         * @return Count of Fragments.
         ****************************/
        @Override
        public int getCount() {
            return 3;
        }
    }
}
