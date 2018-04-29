/**************************
 * Step Counter program
 * Class : CS6326.001
 * Spring 2018
 *
 * Coder 1:
 * 	Hitesh Gupta Tumsi Ramesh
 *   netId: hxg170230
 * Coder 2:
 * 	Meghana Pochiraju
 * 	netId: mxp165130
 *
 * This is a Fragment that is designed to handle
 * Account details and User preferences.
 *
 * This Fragment has two main functionality,
 *  1. Populate the previous details and preferences of the user.
 *  2. Update any changes in any details and preferences and update the application.
 *
 * The User Details and Preferences are stored in the DataProcessing object (dataObj).
 **************************/

package com.utd.mxp165130.steps;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class AccountFragment extends Fragment {

    private static String DATA_OBJECT = "dataObject";
    private DataProcessing dataObject;

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * Bundles the data to be passed to the Fragment
     * and returns the fragment back to the invoking function.
     *
     * @param dataObject : DataProcessing object.
     *
     * @return AccountFragment : an instance of AccountFragment
     **************************/
    public static AccountFragment newInstance(DataProcessing dataObject) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATA_OBJECT, dataObject);
        fragment.setArguments(args);
        return fragment;
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * onCreateView Handler,
     *  this function extracts data from the bundle and saves on to the global variables
     *  this function also binds on click handler to the save button.
     *
     * @param inflater : LayoutInflater
     * @param container : View that is the parent of generated view
     * @param savedInstanceState : Bundle object containing arguments.
     *
     * @return View : the fragment's view.
     **************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.account_fragment, container, false);
        dataObject = getArguments().getParcelable(DATA_OBJECT);
        populateFields(view);
        Button BtnSave = view.findViewById(R.id.BtnSave);
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHandle(view);
            }
        });

        return view;
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * function Handles the on click event on save button,
     *  this function validates the editable text fields and raises toasts.
     *  this function handles saving the data to an object and navigates to the SensorFragment.
     *
     * @param view : View of the event generator.
     **************************/
    private void onClickHandle(View view) {
        EditText fname = view.findViewById(R.id.editTextFirstName);
        EditText lname = view.findViewById(R.id.editTextLastName);
        EditText gender = view.findViewById(R.id.editGender);
        EditText age = view.findViewById(R.id.editAge);
        EditText noOfInchesPerStep = view.findViewById(R.id.editTextNoOfSteps);
        RadioGroup radioButtonGroup = view.findViewById(R.id.unitsRadioGroup);

        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int idx = radioButtonGroup.indexOfChild(radioButton);
        RadioButton r = (RadioButton) radioButtonGroup.getChildAt(idx);
        String selectedtext = r.getText().toString();
        Spinner dateFormat = view.findViewById(R.id.DropDownDateFormat);
        String[] UserData = new String[7];

        /* Validating editable fields */
        if (!fname.getText().toString().trim().isEmpty()) {
            UserData[0] = fname.getText().toString();
        } else {
            Toast.makeText(getContext(), "First Name can not be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!lname.getText().toString().trim().isEmpty()) {
            UserData[1] = lname.getText().toString();
        } else {
            Toast.makeText(getContext(), "Last Name can not be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!gender.getText().toString().trim().isEmpty()) {
            UserData[2] = gender.getText().toString();
        } else {
            Toast.makeText(getContext(), "Gender can not be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!age.getText().toString().trim().isEmpty()) {
            UserData[3] = age.getText().toString();
        } else {
            Toast.makeText(getContext(), "Age can not be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!noOfInchesPerStep.getText().toString().trim().isEmpty()) {
            UserData[4] = noOfInchesPerStep.getText().toString();
        } else {
            Toast.makeText(getContext(), "Number of inches per Step can not be Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        UserData[5] = selectedtext;
        UserData[6] = dateFormat.getSelectedItem().toString();
        dataObject.setUserData(UserData);

        /* Update the Recycler view based on new preferences. */
        ((MainActivity) getActivity()).setAdapterUpdate();

        /* Navigate to SensorFragment */
        ((TabLayout) getActivity().findViewById(R.id.tabs)).getTabAt(0).select();
    }

    /**************************
     * Coder: Meghana Pochiraju (mxp165130)
     *
     * This function is responsible to populate all the fields with the data from user object.
     *
     * @param view : the view which contains the editable fields.
     **************************/
    public void populateFields(View view) {

        EditText fname = view.findViewById(R.id.editTextFirstName);
        EditText lname = view.findViewById(R.id.editTextLastName);
        EditText gender = view.findViewById(R.id.editGender);
        EditText age = view.findViewById(R.id.editAge);
        EditText noOfInchesPerStep = view.findViewById(R.id.editTextNoOfSteps);
        RadioGroup unitsGroup = view.findViewById(R.id.unitsRadioGroup);
        Spinner dateFormat = view.findViewById(R.id.DropDownDateFormat);
        UserAccount user = dataObject.getUserData();
        if (user != null) {
            fname.setText(user.getFirstName());
            lname.setText(user.getLastName());
            gender.setText(user.getGender());
            age.setText(String.valueOf(user.getAge()));
            noOfInchesPerStep.setText(String.valueOf(user.getInches_per_step()));
            if (user.getMetric() == 1) {
                unitsGroup.check(R.id.metricSystem);
            } else {
                unitsGroup.check(R.id.USStandardSystem);
            }
            List<String> DateFormats = Arrays.asList(getResources().getStringArray(R.array.DateFormats));
            dateFormat.setSelection(DateFormats.indexOf(user.getDateFormat()));
        }
    }

}