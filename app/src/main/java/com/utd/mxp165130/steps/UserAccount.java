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
 * This is the user-defined data type for the User account.
 *
 * This class contains the getter, setters and the helper methods for the
 * user object.
 *
 * fields of the class :
 *      firstName;
 *      lastName;
 *      gender;
 *      age;
 *      inches_per_step;
 *      metric;
 *      dateFormat;
 *
 ******************************************************************************/

package com.utd.mxp165130.steps;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccount implements Parcelable {

    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private double inches_per_step;
    private int metric;
    private String dateFormat;

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Overloaded  empty constructor
     **********/

    public UserAccount() {

    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * The constructor to be implemented for the
     * Parcelable interface
     * @param in
     **********/

    public UserAccount(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.gender = in.readString();
        this.age = in.readInt();
        this.inches_per_step = in.readDouble();
        this.metric = in.readInt();
        this.dateFormat = in.readString();
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Setter for first name
     * @param fname
     **********/

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Setter for last name
     * @param lname
     **********/

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Setter for gender
     * @param gender
     **********/

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Setter for age
     * @param age
     **********/

    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Setter for inches_per_step
     * @param inches_per_step
     **********/

    public void setInches_per_step(String inches_per_step) {
        this.inches_per_step = Double.parseDouble(inches_per_step);
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Setter for metric preference
     * @param metric
     **********/

    public void setMetric(String metric) {
        this.metric = Integer.parseInt(metric);
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Setter for date format preference
     * @param format
     **********/

    public void setDateFormat(String format) {
        this.dateFormat = format;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the first name
     * @return firstName
     **********/

    public String getFirstName() {
        return this.firstName;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the last name
     * @return lastName
     **********/

    public String getLastName() {
        return this.lastName;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the gender
     * @return gender
     **********/

    public String getGender() {
        return this.gender;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the age
     * @return age
     **********/

    public int getAge() {
        return this.age;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the inches_per_step
     * @return inches_per_step
     **********/

    public double getInches_per_step() {
        return this.inches_per_step;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the metric unit in string form
     * @return metric unit
     **********/

    public String getMetricUnit() {
        return this.metric == 1 ? "km" : "miles";
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the metric unit in numeric form
     * @return metric unit
     **********/

    public int getMetric() {
        return this.metric;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Getter for the date format
     * @return date format
     **********/

    public String getDateFormat() {
        return dateFormat;
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Overridden toString method to display all the fields tab separated
     *
     **********/

    @Override
    public String toString() {
        return this.firstName + "\t" + this.lastName + "\t" + this.gender + "\t" + this.age + "\t" +
                this.inches_per_step + "\t" + this.metric + "\t" + this.dateFormat;
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
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Overridden writeToParcel
     * @param dest
     * @param flags
     **********/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.gender);
        dest.writeInt(this.age);
        dest.writeDouble(this.inches_per_step);
        dest.writeInt(this.metric);
        dest.writeString(this.dateFormat);
    }

    /**********
     * Overridden Parcelable.Creator
     **********/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserAccount createFromParcel(Parcel in) {
            return new UserAccount(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };
}

