package com.utd.mxp165130.steps;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;


    /**
     * Utility class to process the data
     *
     */

    public class DataProcessing implements Serializable {

        private ArrayList<StepCounterInstance> stepCounterData = new ArrayList<>();
        private UserAccount userData = new UserAccount();
        private String STEP_COUNTER_DATA_FILENAME = "StepCounterData.txt";
        private String User_DATA_FILENAME = "UserData.txt";

        /**
         * Method to get contact details from the local data store
         * @return
         */

        public ArrayList<StepCounterInstance> getStepCounterData(){
            return this.stepCounterData;
        }


        public void setStepCounterData(String[] stepCounterDataStrings){

            try {
                if(stepCounterDataStrings.length == 4) {
                    StepCounterInstance object = new StepCounterInstance();
                    object.setStepCounterInstanceDate(stepCounterDataStrings[0]);
                    object.setStartTime(stepCounterDataStrings[1]);
                    object.setEndTime(stepCounterDataStrings[2]);
                    object.setNoOfSteps(stepCounterDataStrings[3]);
                    stepCounterData.add(object);
                }
            }
            catch (ParseException e) {
                //do not add the instance to the list. Corrupt instance.
            }

        }


        /**
         * Method to read data from a file and store it to a local data store
         * @param context
         */
        public void readFromStepCounterDataFile(Context context){

            try {
                InputStream inputStream = context.openFileInput(STEP_COUNTER_DATA_FILENAME);

                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        this.setStepCounterData(line.split("\t"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void writeToStepCounterDataFile(Context context) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(STEP_COUNTER_DATA_FILENAME,Context.MODE_PRIVATE));
                for ( StepCounterInstance instance : stepCounterData) {
                    outputStreamWriter.write(instance.toString());
                }
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        // File processing for the USER data


        public UserAccount getUserData() {
            return userData;
        }

        public void setUserData(String[] userDataStrings) {
            if(userDataStrings.length == 7){
                userData.setFirstName(userDataStrings[0]);
                userData.setLastName(userDataStrings[1]);
                userData.setGender(userDataStrings[2]);
                userData.setAge(userDataStrings[3]);
                userData.setInches_per_step(userDataStrings[4]);
                userData.setMetric(userDataStrings[5]);
                userData.setDateFormat(userDataStrings[6]);
            }
        }

        public void readFromUSerAccountDataFile(Context context){

            try {
                InputStream inputStream = context.openFileInput(User_DATA_FILENAME);

                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        this.setUserData(line.split("\t"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void writeToUserAccountDataFile(Context context) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(User_DATA_FILENAME,Context.MODE_PRIVATE));
                outputStreamWriter.write(userData.toString());
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


