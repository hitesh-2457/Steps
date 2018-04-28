package com.utd.mxp165130.steps;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.stream.Stream;


/**
 * Utility class to process the data
 */

@SuppressLint("ParcelCreator")
public class DataProcessing extends ArrayList<StepCounterInstance> implements Parcelable {

    private static final String TAG = "DataProcessing";
    private final ArrayList<StepCounterInstance> stepCounterData;
    private final UserAccount userData;
    private final String STEP_COUNTER_DATA_FILENAME = Environment.getExternalStorageDirectory() + "/" + "StepCounterData.txt";
    private final String User_DATA_FILENAME = Environment.getExternalStorageDirectory() + "/" + "UserData.txt";

    /**
     * Method to get contact details from the local data store
     *
     * @return
     */

    public DataProcessing(){
        stepCounterData = new ArrayList<>();
        userData = new UserAccount();
    }

    public ArrayList<StepCounterInstance> getStepCounterData() {
        return stepCounterData;
    }


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
        Log.i(TAG, "readFromStepCounterDataFile: "+object.toString());
        return object;
    }

    public void setStepCounterData(StepCounterInstance instance) {
            stepCounterData.add(instance);
        Log.i(TAG, "Size: "+stepCounterData.size());
    }

    public void setStepCounterData(String[] stepCounterDataStrings) {
        setStepCounterData(setStepCounterInstance(stepCounterDataStrings));
    }

    /**
     * Method to read data from a file and store it to a local data store
     */
    public void readFromStepCounterDataFile(Context context) {

        try {
//            File file = new File(STEP_COUNTER_DATA_FILENAME);
//            if (!file.exists()) {
//                file.createNewFile();
//                return;
//            }
//            InputStream inputStream = context.openFileInput(STEP_COUNTER_DATA_FILENAME);
//
//
//            if (inputStream != null) {
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    this.setStepCounterData(line.split("\t"));
//
//                }
            File file = new File(STEP_COUNTER_DATA_FILENAME);
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    this.setStepCounterData(line.split("\t"));
                    Log.i(TAG, "readFromStepCounterDataFile: "+line);
                }
                bufferedReader.close();
                fileReader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeToStepCounterDataFile(Context context) {
        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(STEP_COUNTER_DATA_FILENAME, Context.MODE_PRIVATE));
//            for (StepCounterInstance instance : stepCounterData) {
//                outputStreamWriter.write(instance.toString());
//            }
//            outputStreamWriter.close();
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


    // File processing for the USER data


    public UserAccount getUserData() {
        return userData;
    }

    public void setUserData(String[] userDataStrings) {
        if (userDataStrings.length == 7) {
            userData.setFirstName(userDataStrings[0]);
            userData.setLastName(userDataStrings[1]);
            userData.setGender(userDataStrings[2]);
            userData.setAge(userDataStrings[3]);
            userData.setInches_per_step(userDataStrings[4]);
            if (userDataStrings[5].equals("Metric System")||userDataStrings[5].equals("1")) {
                userData.setMetric("1");
            } else {
                userData.setMetric("2");
            }
            userData.setDateFormat(userDataStrings[6]);
        }
    }

    public void readFromUserAccountDataFile(Context context) {


        try {
            File file = new File(User_DATA_FILENAME);
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();
                this.setUserData(line.split("\t"));
                Log.i(TAG, "readFromStepCounterDataFile: "+line);

                bufferedReader.close();
                fileReader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeToUserAccountDataFile(Context context) {
        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(User_DATA_FILENAME, Context.MODE_PRIVATE));
//            outputStreamWriter.write(userData.toString());
//            outputStreamWriter.close();
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

    @Override
    public Stream<StepCounterInstance> stream() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


