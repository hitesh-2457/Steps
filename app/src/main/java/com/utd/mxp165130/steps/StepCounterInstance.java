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
 * This is the user-defined data type for the step counter instance
 *
 * This class contains the getter, setters and the helper methods for the
 * step counter fields.
 *
 * fields of the class :
 *      stepCounterInstanceDate;
 *      startTime;
 *      endTime;
 *      noOfSteps;
 *
 ******************************************************************************/

package com.utd.mxp165130.steps;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("ParcelCreator")
public class StepCounterInstance implements Parcelable, Comparable<StepCounterInstance> {

    private Date stepCounterInstanceDate;
    private Date startTime;
    private Date endTime;
    private int noOfSteps;
    private final double INCHES_TO_MILES = 63360;
    private final double INCHES_TO_KILOMETER = 39370.1;

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Overloaded  empty constructor
     **********/
    StepCounterInstance() {

    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Constructor to set the fields
     * @param date
     * @param startTime
     * @param endTime
     * @param noOfSteps
     *
     **********/

    StepCounterInstance(Date date, Date startTime, Date endTime, int noOfSteps) {
        this.stepCounterInstanceDate = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.noOfSteps = noOfSteps;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * The constructor to be implemented for the
     * Parcelable interface
     *@param in
     **********/

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

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Setter for StepCounterInstanceDate
     * @param date: date string
     * @throws ParseException
     **********/

    public void setStepCounterInstanceDate(String date) throws ParseException {
        this.stepCounterInstanceDate = ConvertStringToDate(date);
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Setter for start time
     * @param startTime: time string
     * @throws ParseException
     **********/

    public void setStartTime(String startTime) throws ParseException {
        this.startTime = ConvertStringToDate(startTime);

    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Setter for end time
     * @param endTime: time string
     * @throws ParseException
     **********/

    public void setEndTime(String endTime) throws ParseException {
        this.endTime = ConvertStringToDate(endTime);
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Getter for StepCounterInstanceDate
     * @return stepCounterInstanceDate : date object
     **********/

    public Date getStepCounterInstanceDate() {
        return this.stepCounterInstanceDate;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Setter for number of steps
     * @param steps
     **********/

    public void setNoOfSteps(String steps) {
        this.noOfSteps = Integer.parseInt(steps);
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Getter for start time
     * @return startTime : date object
     **********/

    public Date getStartTime() {
        return this.startTime;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Getter for end time
     * @return endTime : date object
     **********/

    public Date getEndTime() {
        return this.endTime;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Getter for number of steps
     * @return noOfSteps : int number of steps
     **********/

    public int getNoOfSteps() {
        return this.noOfSteps;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Getter for the distance travelled.
     * Calculated the distance based on the unit of measurement and the
     * inches per step value entered by the user.
     * @return distance : double distance
     **********/

    public double getDistance(int unit, double noOfInchesPerStep) {
        //1 = miles
        if (unit == 1) {
            return (this.noOfSteps * noOfInchesPerStep) / INCHES_TO_MILES;
        }
        //inches to kilometer
        else {
            return (this.noOfSteps * noOfInchesPerStep) / INCHES_TO_KILOMETER;
        }
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Overridden toString method to display all the fields tab separated
     *
     **********/

    @Override
    public String toString() {
        String pattern = "dd-MM-yyyy HH:mm:ss";
        return ConvertDateToString(this.stepCounterInstanceDate, pattern) + "\t" + ConvertDateToString(this.startTime, pattern)
                + "\t" + ConvertDateToString(this.endTime, pattern) + "\t" + this.noOfSteps;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Helper method to convert the given string to a date object
     * @param dateString
     * @throws ParseException
     * @return date object
     *
     **********/

    private Date ConvertStringToDate(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.parse(dateString);
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Helper method to convert the given date object to date time string of
     * the specified format
     * @param date
     * @param pattern : string pattern
     * @return string date time
     **********/

    public String ConvertDateToString(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Helper method to convert the given date object to a date string of
     * the specified format
     * @param date
     * @param pattern : string pattern
     * @return string date
     **********/

    public String ConvertDateToDateString(Date date, String pattern) {
        String datepattern = pattern.split(" ")[0];
        DateFormat format = new SimpleDateFormat(datepattern);
        return format.format(date);
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Helper method to convert the given date object to a time string of
     * the specified format
     * @param date
     * @return string time
     **********/

    public String ConvertDateToTimeString(Date date) {
        String pattern = "HH:mm:ss";
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**********
     * Overridden describeContents, Unused
     *
     **********/

    @Override
    public int describeContents() {
        return 0;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Overridden writeToParcel
     * @param dest
     * @param flags
     **********/

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ConvertDateToString(this.stepCounterInstanceDate, "dd-MM-yyyy HH:mm:ss"));
        dest.writeString(this.ConvertDateToString(this.startTime, "dd-MM-yyyy HH:mm:ss"));
        dest.writeString(this.ConvertDateToString(this.endTime, "dd-MM-yyyy HH:mm:ss"));
        dest.writeInt(this.noOfSteps);
    }

    /**********
     * Overridden Parcelable.Creator
     **********/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StepCounterInstance createFromParcel(Parcel in) {
            return new StepCounterInstance(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Overridden compareTo method to compare based on the date
     * Returns 1 if this date is before the other objects date,
     * -1 if its after and 0 if they are equal.
     * @param o : The StepCounterInstance to be compared with.
     **********/

    @Override
    public int compareTo(@NonNull StepCounterInstance o) {
        if (this.stepCounterInstanceDate.after(o.stepCounterInstanceDate)) {
            return -1;
        } else if (this.stepCounterInstanceDate.before(o.stepCounterInstanceDate)) {
            return 1;
        } else {
            return 0;
        }
    }
}
