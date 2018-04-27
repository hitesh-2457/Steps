package com.utd.mxp165130.steps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class TabFragment3 extends Fragment {

    private static String DATA_OBJECT = "dataObject";
    private DataProcessing dataObject;

    public static TabFragment3 newInstance(DataProcessing dataObject) {
        TabFragment3 fragment = new TabFragment3();
        Bundle args = new Bundle();
        args.putSerializable(DATA_OBJECT, dataObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);
        populateFields(view);
        dataObject = (DataProcessing) getArguments().getSerializable(DATA_OBJECT);
        Button BtnSave = (Button) view.findViewById(R.id.BtnSave);
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText fname = (EditText) v.findViewById(R.id.editTextFirstName);
                EditText lname = (EditText) v.findViewById(R.id.editTextLastName);
                EditText gender = (EditText) v.findViewById(R.id.editGender);
                EditText age = (EditText) v.findViewById(R.id.editAge);
                EditText noOfInchesPerStep = (EditText) v.findViewById(R.id.editTextNoOfSteps);
                RadioGroup unitsGroup = (RadioGroup) v.findViewById(R.id.unitsRadioGroup);
                int selectedRadioBtnId = unitsGroup.getCheckedRadioButtonId();
                RadioButton selectedUnit = (RadioButton) v.findViewById(selectedRadioBtnId);
                Spinner dateFormat = (Spinner) v.findViewById(R.id.DropDownDateFormat);
                String[] UserData = new String[7];
                UserData[0] = fname.getText().toString();
                UserData[1] = lname.getText().toString();
                UserData[2] = gender.getText().toString();
                UserData[3] = age.getText().toString();
                UserData[4] = noOfInchesPerStep.getText().toString();
                UserData[5] = selectedUnit.getText().toString();
                UserData[6] = dateFormat.getSelectedItem().toString();
                dataObject.setUserData(UserData);
            }
        });

        return view;
    }

    public void populateFields(View v){

        EditText fname = (EditText) v.findViewById(R.id.editTextFirstName);
        EditText lname = (EditText) v.findViewById(R.id.editTextLastName);
        EditText gender = (EditText) v.findViewById(R.id.editGender);
        EditText age = (EditText) v.findViewById(R.id.editAge);
        EditText noOfInchesPerStep = (EditText) v.findViewById(R.id.editTextNoOfSteps);
        RadioGroup unitsGroup = (RadioGroup) v.findViewById(R.id.unitsRadioGroup);
        int selectedRadioBtnId = unitsGroup.getCheckedRadioButtonId();
        RadioButton selectedUnit = (RadioButton) v.findViewById(selectedRadioBtnId);
        Spinner dateFormat = (Spinner) v.findViewById(R.id.DropDownDateFormat);
        UserAccount user = dataObject.getUserData();
        if(user!=null){
            fname.setText(user.getFirstName());
            lname.setText(user.getLastName());
            gender.setText(user.getGender());
            age.setText(user.getAge());
            noOfInchesPerStep.setText(String.valueOf(user.getInches_per_step()));
            if(user.getMetric() == 1) {
                unitsGroup.check(R.id.metricSystem);
            }
            else{
                unitsGroup.check(R.id.USStandardSystem);
            }
            List<String> DateFormats = Arrays.asList(getResources().getStringArray(R.array.DateFormats));
            dateFormat.setSelection(DateFormats.indexOf(user.getDateFormat()));
        }

    }

}