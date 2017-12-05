package com.example.mohamed.openstarter.Activities;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mohamed.openstarter.Adapters.CategoriesSpinnerAdapter;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class CreateProjectActivity extends AppCompatActivity implements VerticalStepperForm {

    public static final String NEW_PROJECT_CREATED = "new_project_created";

    // Information about the steps/fields of the form
    private static final int NAME_STEP_NUM = 0;
    private static final int CATEGORY_STEP_NUM = 1;
    private static final int SHORT_DESCRIPTION_STEP_NUM = 2;
    private static final int DESCRIPTION_STEP_NUM = 3;
    private static final int START_DATE_STEP_NUM = 4;
    private static final int FINISH_DATE_STEP_NUM = 5;
    private static final int BUDGET_STEP_NUM = 6;

    // Name step
    private EditText nameEditText;
    private static final int MIN_CHARACTERS_NAME = 3;
    private static final int MAX_CHARACTERS_NAME = 50;
    public static final String STATE_NAME = "name";

    // Category step
    private Spinner categorySpinner;
    public static final String STATE_CATEGORY = "category";

    // Short Description step
    private EditText shortDescriptionEditText;
    private static final int MIN_CHARACTERS_SHORT_DESCRIPTION = 20;
    private static final int MAX_CHARACTERS_SHORT_DESCRIPTION = 150;
    public static final String STATE_SHORT_DESCRIPTION = "short_description";

    // Description step
    private EditText DescriptionEditText;
    private static final int MIN_CHARACTERS_DESCRIPTION = 100;
    private static final int MAX_CHARACTERS_DESCRIPTION = 600;
    public static final String STATE_DESCRIPTION = "description";
    
    // Start Date step
    private TextView startDateDayTextView;
    private DatePickerDialog startDateDayPicker;
    private TextView startDateTimeTextView;
    private TimePickerDialog startDateTimePicker;
    private Pair<Integer, Integer> startDateTime;
    public static final String STATE_START_DATE_TIME_HOUR = "start_date_time_hour";
    public static final String STATE_START_DATE_TIME_MINUTES = "start_date_time_minutes";

    // Finish Date step
    private TextView finishDateDayTextView;
    private DatePickerDialog finishDateDayPicker;
    private TextView finishDateTimeTextView;
    private TimePickerDialog finishDateTimePicker;
    private Pair<Integer, Integer> finishDateTime;
    public static final String STATE_FINISH_DATE_TIME_HOUR = "finish_date_time_hour";
    public static final String STATE_FINISH_DATE_TIME_MINUTES = "finish_date_time_minutes";

    // Budget step
    private EditText budgetEditText;
    public static final String STATE_BUDGET = "budget";

    private boolean confirmBack = true;
    private ProgressDialog progressDialog;
    private VerticalStepperFormLayout verticalStepperForm;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //firebaseAuth.getCurrentUser().getEmail();
        initializeActivity();
    }

    private void initializeActivity() {

        // Start date step vars
        calendar = Calendar.getInstance();
        setStartDateDayPicker(calendar);
        setStartDateTimePicker(calendar);

        // Finish date step vars
        setFinishDateDayPicker(calendar);
        setFinishDateTimePicker(calendar);

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
            case CATEGORY_STEP_NUM:
                view = createProjectCategoryStep();
                break;
            case SHORT_DESCRIPTION_STEP_NUM:
                view = createProjectShortDescriptionStep();
                break;
            case DESCRIPTION_STEP_NUM:
                view = createProjectDescriptionStep();
                break;
            case START_DATE_STEP_NUM:
                view = createProjectStartDateStep();
                break;
            case FINISH_DATE_STEP_NUM:
                view = createProjectFinishDateStep();
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
                checkNameStep(nameEditText.getText().toString());
                break;
            case CATEGORY_STEP_NUM:
                verticalStepperForm.setStepAsCompleted(stepNumber);
                break;
            case SHORT_DESCRIPTION_STEP_NUM:
                checkShortDescriptionStep(shortDescriptionEditText.getText().toString());
                break;
            case DESCRIPTION_STEP_NUM:
                checkDescriptionStep(DescriptionEditText.getText().toString());
                break;
            case START_DATE_STEP_NUM:
                checkStartDateStep(startDateDayTextView.getText().toString());
                break;
            case FINISH_DATE_STEP_NUM:
                checkFinishDateStep(finishDateDayTextView.getText().toString());
                break;
            case BUDGET_STEP_NUM:
                checkBudgetStep(budgetEditText.getText().toString());
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
    //************************  CATEGORY

    private View createProjectCategoryStep() {
        categorySpinner = new Spinner(this);
        final String[] data = {"Category 3amtek", "Okhtek"};
        final ArrayAdapter<String> spinnerAdapter = new CategoriesSpinnerAdapter(
                this, android.R.layout.simple_spinner_item,
                data);
        categorySpinner.setAdapter(spinnerAdapter);



        return categorySpinner;
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
    //************************  DESCRIPTION

    private View createProjectDescriptionStep() {
        DescriptionEditText = new EditText(this);
        DescriptionEditText.setHint(R.string.create_project_form_hint_description);
        DescriptionEditText.setSingleLine(false);
        DescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkDescriptionStep(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        DescriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        return DescriptionEditText;
    }


    private boolean checkDescriptionStep(String Description) {
        boolean DescriptionIsCorrect = false;

        if (Description.length() >= MIN_CHARACTERS_DESCRIPTION && Description.length() <= MAX_CHARACTERS_DESCRIPTION) {
            DescriptionIsCorrect = true;

            verticalStepperForm.setActiveStepAsCompleted();

        } else if (Description.length() < MIN_CHARACTERS_DESCRIPTION) {
            String DescriptionErrorString = getResources().getString(R.string.error_name_min_characters);
            String DescriptionError = String.format(DescriptionErrorString, MIN_CHARACTERS_DESCRIPTION);

            verticalStepperForm.setActiveStepAsUncompleted(DescriptionError);
            // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

        } else {
            String DescriptionErrorString = getResources().getString(R.string.error_name_max_characters);
            String DescriptionError = String.format(DescriptionErrorString, MAX_CHARACTERS_DESCRIPTION);

            verticalStepperForm.setActiveStepAsUncompleted(DescriptionError);
        }

        return DescriptionIsCorrect;
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
        startDateDayTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkStartDateStep(s.toString())) {
                    // verticalStepperForm.goToNextStep();
                }
            }
        });
        return startDateStepContent;
    }

    private boolean checkStartDateStep(String startDate) {
        boolean startDateIsCorrect = false;
        if (startDate.trim().length() > 0) {
            startDateIsCorrect = true;

            verticalStepperForm.setActiveStepAsCompleted();

        } else {
            String startDateError = getResources().getString(R.string.error_start_date);

            verticalStepperForm.setActiveStepAsUncompleted(startDateError);
        }

        return startDateIsCorrect;
    }

    private void setStartDateTimePicker(Calendar calendar) {
        int hourOfDay = calendar.get((Calendar.HOUR_OF_DAY));
        int minute = calendar.get((Calendar.MINUTE));
        startDateTimePicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setStartTime(hourOfDay, minute);
                    }
                }, hourOfDay, minute, true);
    }

    private void setStartTime(int hour, int minutes) {
        startDateTime = new Pair<>(hour, minutes);
        String hourString = ((startDateTime.first > 9) ?
                String.valueOf(startDateTime.first) : ("0" + startDateTime.first));
        String minutesString = ((startDateTime.second > 9) ?
                String.valueOf(startDateTime.second) : ("0" + startDateTime.second));
        String time = hourString + ":" + minutesString;
        startDateTimeTextView.setText(time);
    }

    private void setStartDateDayPicker(Calendar calendar) {
        int year = calendar.get((Calendar.YEAR));
        int monthOfYear = calendar.get((Calendar.MONTH));
        int dayOfMonth = calendar.get((Calendar.DAY_OF_MONTH));
        startDateDayPicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        setStartDay(year, monthOfYear, dayOfMonth);
                    }
                }, year, monthOfYear, dayOfMonth);
        startDateDayPicker.getDatePicker().setMinDate(calendar.getTime().getTime());
    }

    private void setStartDay(int year, int monthOfYear, int dayOfMonth) {
        String day = year + "/" + monthOfYear + "/" + dayOfMonth;
        startDateDayTextView.setText(day);
    }

    //******************************************
    //************************ FINISH DATE

    private View createProjectFinishDateStep() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        LinearLayout finishDateStepContent =
                (LinearLayout) inflater.inflate(R.layout.step_create_project_finish_date, null, false);
        finishDateDayTextView = finishDateStepContent.findViewById(R.id.finish_date_day);
        finishDateTimeTextView = finishDateStepContent.findViewById(R.id.finish_date_time);

        finishDateDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDateDayPicker.show();
            }
        });

        finishDateTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDateTimePicker.show();
            }
        });

        finishDateDayTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkFinishDateStep(s.toString())) {
                    // verticalStepperForm.goToNextStep();
                }
            }
        });
        return finishDateStepContent;
    }

    private boolean checkFinishDateStep(String finishDayString) {
        boolean finishDateIsCorrect = false;
        String startDayString = startDateDayTextView.getText().toString();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date finishDay = dayFormat.parse(finishDayString);
            Date startDay = dayFormat.parse(startDayString);
            if (finishDayString.trim().length() > 0) {
                if (finishDay.after(startDay)) {
                    finishDateIsCorrect = true;
                    verticalStepperForm.setActiveStepAsCompleted();
                } else {
                    String finishDateError = getResources().getString(R.string.error_finish_date);
                    verticalStepperForm.setActiveStepAsUncompleted(finishDateError);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finishDateIsCorrect;
    }

    private void setFinishDateTimePicker(Calendar calendar) {
        int hourOfDay = calendar.get((Calendar.HOUR_OF_DAY));
        int minute = calendar.get((Calendar.MINUTE));
        finishDateTimePicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setFinishTime(hourOfDay, minute);
                    }
                }, hourOfDay, minute, true);
    }

    private void setFinishTime(int hour, int minutes) {
        finishDateTime = new Pair<>(hour, minutes);
        String hourString = ((finishDateTime.first > 9) ?
                String.valueOf(finishDateTime.first) : ("0" + finishDateTime.first));
        String minutesString = ((finishDateTime.second > 9) ?
                String.valueOf(finishDateTime.second) : ("0" + finishDateTime.second));
        String time = hourString + ":" + minutesString;
        finishDateTimeTextView.setText(time);
    }

    private void setFinishDateDayPicker(Calendar calendar) {
        int year = calendar.get((Calendar.YEAR));
        int monthOfYear = calendar.get((Calendar.MONTH));
        int dayOfMonth = calendar.get((Calendar.DAY_OF_MONTH));

        finishDateDayPicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        setFinishDay(year, monthOfYear, dayOfMonth);
                    }
                }, year, monthOfYear, dayOfMonth);
        finishDateDayPicker.getDatePicker().setMinDate(calendar.getTime().getTime());
    }

    private void setFinishDay(int year, int monthOfYear, int dayOfMonth) {
        String day = year + "/" + monthOfYear + "/" + dayOfMonth;
        finishDateDayTextView.setText(day);
    }

    //******************************************
    //************************ BUDGET

    private View createProjectBudget() {
        budgetEditText = new EditText(this);
        budgetEditText.setHint(R.string.create_project_form_hint_budget);
        budgetEditText.setSingleLine(true);
        budgetEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        budgetEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        budgetEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (checkBudgetStep(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        return budgetEditText;
    }

    private boolean checkBudgetStep(String budgetString) {
        boolean budgetIsCorrect = false;
        long budget = Long.valueOf(budgetString);
        if (budget > 0) {
            budgetIsCorrect = true;
            verticalStepperForm.setActiveStepAsCompleted();
        } else {
            String startDateError = getResources().getString(R.string.error_budget);
            verticalStepperForm.setActiveStepAsUncompleted(startDateError);
        }
        return budgetIsCorrect;
    }

    //******************************************

}


