package com.utd.mxp165130.steps;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StepCounterInstance {

    private Date stepCounterInstanceDate;
    private Date startTime;
    private Date endTime;
    private int noOfSteps;
    private final double INCHES_TO_MILES = 63360;
    private final double INCHES_TO_KILOMETER = 39370.1;

    public void setStepCounterInstanceDate(String date) throws ParseException {
        this.stepCounterInstanceDate = ConvertStringToDate(date);
    }

    public void setStartTime(String startTime) throws ParseException {
        this.startTime = ConvertStringToDate(startTime);

    }

    public void setEndTime(String endTime) throws ParseException {
        this.endTime = ConvertStringToDate(endTime);
    }

    public Date getStepCounterInstanceDate(){
        return this.stepCounterInstanceDate;
    }

    public void setNoOfSteps(String steps){
        this.noOfSteps = Integer.parseInt(steps);
    }

    public Date getStartTime(){
        return this.startTime;
    }

    public Date getEndTime(){
        return this.endTime;
    }

    public int getNoOfSteps(){
        return this.noOfSteps;
    }

    public double getDistance(int unit, double stride){
        //1 = miles
        if(unit ==1){
            return (this.noOfSteps*stride)/INCHES_TO_MILES;
        }
        //inches to kilometer
        else {
            return (this.noOfSteps*stride)/INCHES_TO_KILOMETER;
        }
    }

    @Override
    public String toString() {
        return ConvertDateToString(this.stepCounterInstanceDate) + "\t" + ConvertDateToString(this.startTime)
                + "\t" + ConvertDateToString(this.endTime) + "\t" + this.noOfSteps;
    }


    private Date ConvertStringToDate(String dateString) throws ParseException {
        DateFormat format= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.parse(dateString);
    }

    private String ConvertDateToString(Date date){
        DateFormat format= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(date);
    }



}
