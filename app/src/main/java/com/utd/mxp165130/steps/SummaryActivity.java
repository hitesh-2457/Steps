package com.utd.mxp165130.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateView();
    }

    private void populateView() {
        Intent i = getIntent();
        UserAccount userData = (UserAccount) i.getExtras().getParcelable("UserObj");
        String pattern = userData.getDateFormat();
        StepCounterInstance dataObj = (StepCounterInstance) i.getExtras().getParcelable("StepCounterObj");

        ((TextView) findViewById(R.id.summaryDate)).setText(dataObj.ConvertDateToString(dataObj.getStepCounterInstanceDate(), pattern).split(" ")[0]);
        ((TextView) findViewById(R.id.summaryStart)).setText(dataObj.ConvertDateToString(dataObj.getStartTime(), pattern).split(" ")[1]);
        ((TextView) findViewById(R.id.summaryEnd)).setText(dataObj.ConvertDateToString(dataObj.getEndTime(), pattern).split(" ")[1]);

        ((TextView) findViewById(R.id.summarySteps)).setText(dataObj.getNoOfSteps());
        ((TextView) findViewById(R.id.summaryMiles)).setText(new DecimalFormat("0.##").format(dataObj.getDistance(userData.getMetric(), userData.getInches_per_step())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
