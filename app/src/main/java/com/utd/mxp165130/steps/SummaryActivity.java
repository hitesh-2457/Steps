/******************************************************************************
 * Step Counter program
 * Class : CS6326.001
 * Spring 2018
 *
 * Coder 1:
 * 	 Hitesh Gupta Tumsi Ramesh
 *   netId: hxg170230
 * Coder 2:
 * 	 Meghana Pochiraju
 * 	 netId: mxp165130
 *
 * This is activity class to display the StepCounterInstance in more detail
 *
 * It has methods to set the text fields for the date, start time, end time,
 * number of steps and the distance traveled.
 *
 * It has a back button to go back to the activity that clalled it.
 *
 ******************************************************************************/

package com.utd.mxp165130.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SummaryActivity extends AppCompatActivity {

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * OnCreate method to inflate the view.
     * It adds the back button to the action bar.
     * @param savedInstanceState
     **********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        populateView();
    }

    /**********
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Helper method to populate the textview with the data for the fields:
     *  date, start time, end time, number of steps and distance travelled
     **********/

    private void populateView() {
        Intent i = getIntent();
        UserAccount userData = i.getExtras().getParcelable("UserObj");
        String pattern = userData.getDateFormat();
        StepCounterInstance dataObj = i.getExtras().getParcelable("StepCounterObj");

        ((TextView) findViewById(R.id.summaryDate)).setText(String.format("Date: %s", dataObj.ConvertDateToString(dataObj.getStepCounterInstanceDate(), pattern).split(" ")[0]));
        ((TextView) findViewById(R.id.summaryStart)).setText(String.format("Start Time: %s", dataObj.ConvertDateToString(dataObj.getStartTime(), pattern).split(" ")[1]));
        ((TextView) findViewById(R.id.summaryEnd)).setText(String.format("End Time: %s", dataObj.ConvertDateToString(dataObj.getEndTime(), pattern).split(" ")[1]));

        ((TextView) findViewById(R.id.summarySteps)).setText(String.format("Steps: %s", String.valueOf(dataObj.getNoOfSteps())));
        ((TextView) findViewById(R.id.summaryMiles)).setText(String.format("Distance: %s %s", new DecimalFormat("0.##").format(dataObj.getDistance(userData.getMetric(), userData.getInches_per_step())), userData.getMetricUnit()));
    }

    /**********
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Event handler called when the back button is pressed
     * It passes the control back to the activity that invoked
     * this activity
     **********/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
