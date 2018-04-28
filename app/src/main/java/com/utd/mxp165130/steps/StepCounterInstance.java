package com.utd.mxp165130.steps;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("ParcelCreator")
public class StepCounterInstance implements Parcelable{

    private Date stepCounterInstanceDate;
    private Date startTime;
    private Date endTime;
    private int noOfSteps;
    private String Datepattern;
    private final double INCHES_TO_MILES = 63360;
    private final double INCHES_TO_KILOMETER = 39370.1;

    StepCounterInstance(){

    }
    StepCounterInstance(Date date, Date startTime, Date endTime, int noOfSteps){
        this.stepCounterInstanceDate = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.noOfSteps = noOfSteps;
    }

    public StepCounterInstance(Parcel in) {
        try {
            this.stepCounterInstanceDate = this.ConvertStringToDate(in.readString());
            this.startTime = this.ConvertStringToDate(in.readString());
            this.endTime = this.ConvertStringToDate(in.readString());
            this.noOfSteps = in.readInt();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setStepCounterInstanceDate(String date) throws ParseException {
        this.stepCounterInstanceDate = ConvertStringToDate(date);
    }

    public void setStartTime(String startTime) throws ParseException {
        this.startTime = ConvertStringToDate(startTime);

    }

    public void setEndTime(String endTime) throws ParseException {
        this.endTime = ConvertStringToDate(endTime);
    }

//    public void setStepCounterInstanceDate(Date date)  {
//        this.stepCounterInstanceDate = date;
//    }
//
//    public void setStartTime(Date startTime)  {
//        this.startTime = startTime;
//
//    }
//
//    public void setEndTime(Date endTime)  {
//        this.endTime = endTime;
//    }

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

    public double getDistance(int unit, double noOfInchesPerStep){
        //1 = miles
        if(unit ==1){
            return (this.noOfSteps*noOfInchesPerStep)/INCHES_TO_MILES;
        }
        //inches to kilometer
        else {
            return (this.noOfSteps*noOfInchesPerStep)/INCHES_TO_KILOMETER;
        }
    }


    @Override
    public String toString() {
        String pattern = "dd-MM-yyyy HH:mm:ss";
        return ConvertDateToString(this.stepCounterInstanceDate,pattern) + "\t" + ConvertDateToString(this.startTime,pattern)
                + "\t" + ConvertDateToString(this.endTime,pattern) + "\t" + this.noOfSteps;
    }


    private Date ConvertStringToDate(String dateString) throws ParseException {
        DateFormat format= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.parse(dateString);
    }

    public String ConvertDateToString(Date date, String pattern){
        //String pattern1 = "dd-MM-yyyy HH:mm:ss";
        DateFormat format= new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public String ConvertDateToDateString(Date date,String pattern){
        String datepattern = pattern.split(" ")[0];
        //String pattern1 = "dd-MM-yyyy";
        DateFormat format= new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public String ConvertDateToTimeString(Date date,String pattern){
        //String datepattern = pattern.split(" ")[1];
        String pattern1 = "HH:mm:ss";
        DateFormat format= new SimpleDateFormat(pattern1);
        return format.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ConvertDateToString(this.stepCounterInstanceDate,"dd-MM-yyyy HH:mm:ss"));
        dest.writeString(this.ConvertDateToString(this.startTime,"dd-MM-yyyy HH:mm:ss"));
        dest.writeString(this.ConvertDateToString(this.endTime,"dd-MM-yyyy HH:mm:ss"));
        dest.writeInt(this.noOfSteps);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StepCounterInstance createFromParcel(Parcel in) {
            return new StepCounterInstance(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };
}
