package com.example.mohamed.openstarter.Activities;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mohamed.openstarter.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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
    private static final int MIN_CHARACTERS_NAME = 3;
    private static final int MAX_CHARACTERS_NAME = 50;
    public static final String STATE_NAME = "nom";

    // Short Description step
    private EditText shortDescriptionEditText;
    private static final int MIN_CHARACTERS_SHORT_DESCRIPTION = 20;
    private static final int MAX_CHARACTERS_SHORT_DESCRIPTION = 150;
    public static final String STATE_DESCRIPTION = "short_description";

    // Start Date step
    private TextView startDateDayTextView;
    private DatePickerDialog startDateDayPicker;
    private TextView startDateTimeTextView;
    private TimePickerDialog startDateTimePicker;
    private Pair<Integer, Integer> startDateTime;
    public static final String STATE_START_DATE_TIME_HOUR = "start_date_time_hour";
    public static final String STATE_START_DATE_TIME_MINUTES = "start_date_time_minutes";
    SimpleDateFormat dayFormat ;
    SimpleDateFormat timeFormat ;
    Calendar calendar;
    String formattedDay ;
    String formattedTime;

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

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        String formattedDay = dayFormat.format(calendar.getTime());
        String formattedTime = timeFormat.format(calendar.getTime());

        startDateDayPicker = new DatePickerDialog(this, datePickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Time step vars
        startDateTime = new Pair<>(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
        setStartDateTimePicker(startDateTime.first, startDateTime.second);

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
        // Here we generate the content view of the correspondent step and we return it so it gets
        // automatically added to the step layout (AKA stepContent)
        View view = null;
        switch (stepNumber) {
            case NAME_STEP_NUM:
                view = createProjectNameStep();
                break;
            case SHORT_DESCRIPTION_STEP_NUM:
                view = createProjectShortDescriptionStep();
                break;
            case START_DATE_STEP_NUM:
                view = createProjectStartDateStep();
                break;
            case BUDGET_STEP_NUM:
                view = createProjectBudget();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case NAME_STEP_NUM:
                // When this step is open, we check that the title is correct
                checkNameStep(nameEditText.getText().toString());
                break;
            case SHORT_DESCRIPTION_STEP_NUM:
            case START_DATE_STEP_NUM:
                // As soon as they are open, these two steps are marked as completed because they
                // have default values
                verticalStepperForm.setStepAsCompleted(stepNumber);
                // In this case, the instruction above is equivalent to:
                // verticalStepperForm.setActiveStepAsCompleted();
                break;
            case BUDGET_STEP_NUM:
                // When this step is open, we check the days to verify that at least one is selected
                //checkDays();
                break;
        }
    }

    @Override
    public void sendData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.vertical_form_stepper_form_sending_data_message));
        executeDataSending();
    }

    private void executeDataSending() {

        /*// TODO Use here the data of the form as you wish

        // Fake data sending effect
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    intent.putExtra(NEW_ALARM_ADDED, true);
                    intent.putExtra(STATE_TITLE, titleEditText.getText().toString());
                    intent.putExtra(STATE_DESCRIPTION, descriptionEditText.getText().toString());
                    intent.putExtra(STATE_TIME_HOUR, time.first);
                    intent.putExtra(STATE_TIME_MINUTES, time.second);
                    intent.putExtra(STATE_WEEK_DAYS, weekDays);
                    // You must set confirmBack to false before calling finish() to avoid the confirmation dialog
                    confirmBack = false;
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // You should delete this code and add yours*/
    }

    private void setStartDateTimePicker(int hour, int minutes) {
        startDateTimePicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTime(hourOfDay, minute);
                    }
                }, hour, minutes, true);
    }

    /*private void setStartDateDayPicker() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
    }*/

    final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

        }
    };

    private void setTime(int hour, int minutes) {
        startDateTime = new Pair<>(hour, minutes);
        String hourString = ((startDateTime.first > 9) ?
                String.valueOf(startDateTime.first) : ("0" + startDateTime.first));
        String minutesString = ((startDateTime.second > 9) ?
                String.valueOf(startDateTime.second) : ("0" + startDateTime.second));
        String time = hourString + ":" + minutesString;
        startDateTimeTextView.setText(time);
    }

    //************************ NAME

    private View createProjectNameStep() {
        // This step view is generated programmatically
        nameEditText = new EditText(this);
        nameEditText.setHint(R.string.create_project_form_hint_name);
        nameEditText.setSingleLine(true);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkNameStep(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkNameStep(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        return nameEditText;
    }

    private boolean checkNameStep(String name) {
        boolean nameIsCorrect = false;

        if (name.length() >= MIN_CHARACTERS_NAME && name.length() <= MAX_CHARACTERS_NAME) {
            nameIsCorrect = true;

            verticalStepperForm.setActiveStepAsCompleted();
            // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);

        } else if (name.length() < MIN_CHARACTERS_NAME) {
            String nameErrorString = getResources().getString(R.string.error_name_min_characters);
            String nameError = String.format(nameErrorString, MIN_CHARACTERS_NAME);

            verticalStepperForm.setActiveStepAsUncompleted(nameError);
            // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

        } else {
            String nameErrorString = getResources().getString(R.string.error_name_max_characters);
            String nameError = String.format(nameErrorString, MAX_CHARACTERS_NAME);

            verticalStepperForm.setActiveStepAsUncompleted(nameError);
        }

        return nameIsCorrect;
    }

    //******************************************
    //************************ SHORT DESCRIPTION

    private View createProjectShortDescriptionStep() {
        shortDescriptionEditText = new EditText(this);
        shortDescriptionEditText.setHint(R.string.create_project_form_hint_short_description);
        shortDescriptionEditText.setSingleLine(false);
        shortDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkShortDescriptionStep(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        shortDescriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        return shortDescriptionEditText;
    }


    private boolean checkShortDescriptionStep(String shortDescription) {
        boolean shortDescriptionIsCorrect = false;

        if (shortDescription.length() >= MIN_CHARACTERS_SHORT_DESCRIPTION && shortDescription.length() <= MAX_CHARACTERS_SHORT_DESCRIPTION) {
            shortDescriptionIsCorrect = true;

            verticalStepperForm.setActiveStepAsCompleted();

        } else if (shortDescription.length() < MIN_CHARACTERS_SHORT_DESCRIPTION) {
            String shortDescriptionErrorString = getResources().getString(R.string.error_name_min_characters);
            String shortDescriptionError = String.format(shortDescriptionErrorString, MIN_CHARACTERS_SHORT_DESCRIPTION);

            verticalStepperForm.setActiveStepAsUncompleted(shortDescriptionError);
            // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

        } else {
            String shortDescriptionErrorString = getResources().getString(R.string.error_name_max_characters);
            String shortDescriptionError = String.format(shortDescriptionErrorString, MAX_CHARACTERS_SHORT_DESCRIPTION);

            verticalStepperForm.setActiveStepAsUncompleted(shortDescriptionError);
        }

        return shortDescriptionIsCorrect;
    }

    //******************************************
    //************************ START DATE

    private View createProjectStartDateStep() {
        // This step view is generated by inflating a layout XML file
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout startDateStepContent =
                (LinearLayout) inflater.inflate(R.layout.step_create_project_start_date, null, false);
        startDateDayTextView = startDateStepContent.findViewById(R.id.start_date_day);
        startDateTimeTextView = startDateStepContent.findViewById(R.id.start_date_time);
        //*********
        Calendar calendar = Calendar.getInstance();
        //startDateDayTextView.setText(formattedDay);
        //startDateTimeTextView.setText(formattedTime);
        //*********jjeh
        startDateDayPicker.getDatePicker().setMinDate(calendar.getTime().getTime());
        //startDateDayPicker =  new DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        startDateDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDayPicker.show();
            }
        });

        startDateTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateTimePicker.show();
            }
        });
        return startDateStepContent;
    }

    //******************************************
    //************************ BUDGET

    private View createProjectBudget() {
        budgetEditText = new EditText(this);
        budgetEditText.setHint(R.string.create_project_form_hint_budget);
        budgetEditText.setSingleLine(true);
        budgetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //checkNameStep(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        budgetEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (true) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        return budgetEditText;
    }

    //******************************************
}


