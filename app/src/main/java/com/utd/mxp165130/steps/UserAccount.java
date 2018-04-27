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

    public void setFirstName(String fname){
        this.firstName = fname;
    }

    public void setLastName(String lname){
        this.lastName = lname;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setAge(String age){
        this.age = Integer.parseInt(age);
    }

    public void setInches_per_step(String inches_per_step){
        this.inches_per_step = Double.parseDouble(inches_per_step);
    }

    public void setMetric(String metric){
        this.metric = Integer.parseInt(metric);
    }

    public void setDateFormat(String format){
        this.dateFormat = format;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public int getAge(){
        return this.age;
    }

    public double getInches_per_step(){
        return this.inches_per_step;
    }

    public int getMetric(){
        return this.metric;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    @Override
    public String toString() {
        return this.firstName +"\t"+ this.lastName +"\t"+ this.gender +"\t"+ this.age +"\t"+
                this.inches_per_step +"\t"+ this.metric +"\t"+ this.dateFormat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

