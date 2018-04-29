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
 * This class handles all the data processing functions.
 *
 * The sensor data is stored in a ArrayList. This list is populated by
 * reading from a data file "StepCounterData.txt" when the program starts up.
 * As the sensor is used, new items are added to this list using the getter and
 * setter methods.
 *
 * The User data is also stored in the UserAccount Object. This object is
 * populated by reading the data from the "UserData.txt" file. Modifications made
 * to the user data will also be updated through the getter and setters in this
 * program.
 *
 * The program creates and reads/writes to two text files : StepCounterData.txt
 * and UserData.txt
 *
 * This class is Parcelable so it can be passed as arguments to the fragments.
 *
 ******************************************************************************/

package com.utd.mxp165130.steps;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;


/**
 * Utility class to process the data
 */

@SuppressLint("ParcelCreator")
public class DataProcessing extends ArrayList<StepCounterInstance> implements Parcelable {

    private final double INCHES_TO_MILES = 63360;
    private final double INCHES_TO_KILOMETER = 39370.1;

    private static final String TAG = "DataProcessing";
    private final ArrayList<StepCounterInstance> stepCounterData;
    private final UserAccount userData;
    private final String STEP_COUNTER_DATA_FILENAME = Environment.getExternalStorageDirectory() + "/StepCounterData.txt";
    private final String User_DATA_FILENAME = Environment.getExternalStorageDirectory() + "/UserData.txt";

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * The constructor to initialize the class variables like
     * the stepCounterData ArrayList and the UserAccount object;
     **********/

    public DataProcessing() {
        stepCounterData = new ArrayList<>();
        userData = new UserAccount();
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Overloaded getter method to get the StepCounterData list.
     * This method does not take any parameters
     * @return ArrayList<StepCounterInstance>: the list containing the step counter data
     **********/

    public ArrayList<StepCounterInstance> getStepCounterData() {
        return stepCounterData;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Overloaded getter method to get the one step counter instance
     * given its position in the list.
     * @param position : the position of the instance in the step
     *                 counter list
     * @return StepCounterInstance: instance of the step counter list
     **********/

    public StepCounterInstance getStepCounterData(int position) {
        return stepCounterData.get(position);
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Sets the stepCounterInstance object with the data provided. It sets the
     * date, start time, stop time and the number of steps.
     * @param stepCounterDataStrings : List of strings containing the date,
     *                               start time, stop time and the number of steps
     **********/

    public StepCounterInstance setStepCounterInstance(String[] stepCounterDataStrings) {

        StepCounterInstance object = new StepCounterInstance();
        try {
//            if (stepCounterDataStrings.length == 4) {

            object.setStepCounterInstanceDate(stepCounterDataStrings[0]);
            object.setStartTime(stepCounterDataStrings[1]);
            object.setEndTime(stepCounterDataStrings[2]);
            object.setNoOfSteps(stepCounterDataStrings[3]);
//            }
        } catch (ParseException e) {
            //do not add the instance to the list. Corrupt instance.
        }
        Log.i(TAG, "readFromStepCounterDataFile: " + object.toString());
        return object;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Add a stepCounterInstance object to the stepCounterData list
     * @param instance : StepCounterInstance instance to be added
     *                 to the list
     **********/

    public void setStepCounterData(StepCounterInstance instance) {
        stepCounterData.add(instance);
        Log.i(TAG, "Size: " + stepCounterData.size());
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Add a step counter data  to the stepCounterData list
     * by calling the setStepCounterData method to create a
     * stepCounterInstance object and then adding it to the list
     *
     * @param stepCounterDataStrings : List of strings containing the date,
     *                          start time, stop time and the number of steps
     **********/

    public void setStepCounterData(String[] stepCounterDataStrings) {
        setStepCounterData(setStepCounterInstance(stepCounterDataStrings));
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Reads the historic records of the step counter data stored in the
     * StepCounterData.txt on the phone.
     * Reads each line from the file, splits it on tab and call the setStepCounterData
     * method to store it in the ArrayList
     * @param context : the app context
     **********/

    public void readFromStepCounterDataFile(Context context) {

        try {
            File file = new File(STEP_COUNTER_DATA_FILENAME);
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    this.setStepCounterData(line.split("\t"));
                    Log.i(TAG, "readFromStepCounterDataFile: " + line);
                }
                bufferedReader.close();
                fileReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Writes the step counter records data from the stepCounterData list to the
     * StepCounterData.txt file on the phone.
     * Iterates through the list and writes each StepCounterInstance as a string to
     * the file.
     * @param context : the app context
     **********/

    public void writeToStepCounterDataFile(Context context) {
        try {
            File file = new File(STEP_COUNTER_DATA_FILENAME);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            StringBuilder dataToWrite = new StringBuilder();
            for (StepCounterInstance instance : stepCounterData) {
                dataToWrite.append(instance.toString()).append("\n");
            }
            fileWriter.write(dataToWrite.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Getter for the UserAccount object
     * @return userData: UserAccount object
     **********/

    public UserAccount getUserData() {
        return userData;
    }

    /**********
     * Coder: Meghana Pochiraju( mxp165130)
     *
     * Setter for the UserAccount object
     * Takes the user account details like name,gender, age, no of inches per step
     * , unit system preference and date preference as an array if strings and sets
     * the UserAccount object userData
     * @param  userDataStrings: string array containing the user data
     **********/

    public void setUserData(String[] userDataStrings) {
        if (userDataStrings.length == 7) {
            userData.setFirstName(userDataStrings[0]);
            userData.setLastName(userDataStrings[1]);
            userData.setGender(userDataStrings[2]);
            userData.setAge(userDataStrings[3]);
            userData.setInches_per_step(userDataStrings[4]);
            if (userDataStrings[5].equals("Metric System") || userDataStrings[5].equals("1")) {
                userData.setMetric("1");
            } else {
                userData.setMetric("2");
            }
            userData.setDateFormat(userDataStrings[6]);
        }
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Reads the user account data like name,gender, age, no of inches per step, unit system
     * preference and date preference stored in the UserData.txt on the phone.
     * Reads each line from the file, splits it on tab and call the setUserData
     * method to store it in the UserAccount object
     * @param context : the app context
     **********/

    public void readFromUserAccountDataFile(Context context) {
        try {
            File file = new File(User_DATA_FILENAME);
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();
                this.setUserData(line.split("\t"));
                Log.i(TAG, "readFromStepCounterDataFile: " + line);

                bufferedReader.close();
                fileReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**********
     * Coder:  Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Writes the user account data from the userData object to the
     * UserData.txt file on the phone.
     * Iterates through the list and writes each StepCounterInstance as a string to
     * the file.
     * @param context : the app context
     **********/

    public void writeToUserAccountDataFile(Context context) {
        try {
            File file = new File(User_DATA_FILENAME);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            StringBuilder dataToWrite = new StringBuilder();
            dataToWrite.append(userData.toString()).append("\n");
            fileWriter.write(dataToWrite.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**********
     * Coder:
     *
     * Helper method to convert the date object to a string of the
     * given format.
     * @param date: date object to be converted
     * @param pattern: pattern string to convert date into
     * @return formatted date string
     **********/

    public String ConvertDateToString(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**********
     * Coder:
     *
     * Helper method to get distance given the number of steps, number
     * of inches per step and the measuring unit
     * @param unit: the measurement unit - Metric or US standard
     * @param noOfInchesPerStep: User specific attribute to compute the
     *                         distance
     * @param noOfSteps: number of steps walked to compute the distance from
     * @return distance travelled as a double.
     **********/

    public double getDistance(int unit, double noOfInchesPerStep, int noOfSteps) {
        //1 = miles
        if (unit == 1) {
            return (noOfSteps * noOfInchesPerStep) / INCHES_TO_MILES;
        }
        //inches to kilometer
        else {
            return (noOfSteps * noOfInchesPerStep) / INCHES_TO_KILOMETER;
        }
    }

    /**********
     * stream handler, Unused
     **********/

    @Override
    public Stream<StepCounterInstance> stream() {
        return null;
    }

    /**********
     * describeContents handler, Unused
     **********/

    @Override
    public int describeContents() {
        return 0;
    }

    /**********
     * describeContents writeToParcel, Unused
     *
     * @param dest
     * @param flags
     **********/
    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


