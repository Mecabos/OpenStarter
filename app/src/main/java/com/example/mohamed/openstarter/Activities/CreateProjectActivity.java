package com.example.mohamed.openstarter.Activities;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mohamed.openstarter.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class CreateProjectActivity extends AppCompatActivity implements VerticalStepperForm {

    public static final String NEW_PROJECT_CREATED = "new_project_created";

    // Information about the steps/fields of the form
    private static final int NAME_STEP_NUM = 0;
    private static final int SHORT_DESCRIPTION_STEP_NUM = 1;
    private static final int START_DATE_STEP_NUM = 2;
    private static final int BUDGET_STEP_NUM = 3;

    // Name step
    private EditText nameEditText;
    private static final int MIN_CHARACTERS_TITLE = 3;
    private static final int MAX_CHARACTERS_TITLE = 50;
    public static final String STATE_NAME = "nom";

    // Short Description step
    private EditText shortDescriptionEditText;
    private static final int MIN_CHARACTERS_SHORT_DESCRIPTION = 20;
    private static final int MAX_CHARACTERS_SHORT_DESCRIPTION = 150;
    public static final String STATE_DESCRIPTION = "short_description";

    // Start Date step
    private TextView startDateTextView;
    private DatePickerDialog startDatePicker;
    private TextView startDateTimeTextView;
    private TimePickerDialog startDateTimePicker;
    private Pair<Integer, Integer> startDateTime;
    public static final String STATE_START_DATE_TIME_HOUR = "start_date_time_hour";
    public static final String STATE_START_DATE_TIME_MINUTES = "start_date_time_minutes";

    // Budget step
    private EditText budgetEditText;
    public static final String STATE_BUDGET = "budget";

    private boolean confirmBack = true;
    private ProgressDialog progressDialog;
    private VerticalStepperFormLayout verticalStepperForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        initializeActivity();
    }

    private void initializeActivity() {

        // Time step vars
        startDateTime = new Pair<>(8, 30);
        setTimePicker(8, 30);

        // Vertical Stepper form vars
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        String[] stepsTitles = getResources().getStringArray(R.array.create_project_steps_titles);
        //String[] stepsSubtitles = getResources().getStringArray(R.array.steps_subtitles);

        // Here we find and initialize the form
        verticalStepperForm = findViewById(R.id.create_project_form);
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, stepsTitles, this, this)
                //.stepsSubtitles(stepsSubtitles)
                //.materialDesignInDisabledSteps(true) // false by default
                //.showVerticalLineWhenStepsAreCollapsed(true) // false by default
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true)
                .init();
    }

    // METHODS THAT HAVE TO BE IMPLEMENTED TO MAKE THE LIBRARY WORK
    // (Implementation of the interface "VerticalStepperForm")

    @Override
    public View createStepContentView(int stepNumber) {
        return null;
    }

    @Override
    public void onStepOpening(int stepNumber) {

    }

    @Override
    public void sendData() {

    }

    private void setTimePicker(int hour, int minutes) {
        startDateTimePicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTime(hourOfDay, minute);
                    }
                }, hour, minutes, true);
    }

    private void setTime(int hour, int minutes) {
        startDateTime = new Pair<>(hour, minutes);
        String hourString = ((startDateTime.first > 9) ?
                String.valueOf(startDateTime.first) : ("0" + startDateTime.first));
        String minutesString = ((startDateTime.second > 9) ?
                String.valueOf(startDateTime.second) : ("0" + startDateTime.second));
        String time = hourString + ":" + minutesString;

        startDateTimeTextView.setText(time);
    }
}
