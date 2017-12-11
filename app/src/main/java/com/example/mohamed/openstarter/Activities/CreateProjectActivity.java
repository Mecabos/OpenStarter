package com.example.mohamed.openstarter.Activities;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mohamed.openstarter.Adapters.CategoriesSpinnerAdapter;
import com.example.mohamed.openstarter.Adapters.CollaborationGroupSpinnerAdapter;
import com.example.mohamed.openstarter.Data.DataSuppliers.CategoryDs;
import com.example.mohamed.openstarter.Data.DataSuppliers.CollaborationGroupDs;
import com.example.mohamed.openstarter.Data.DataSuppliers.ProjectDs;
import com.example.mohamed.openstarter.Models.Category;
import com.example.mohamed.openstarter.Models.CollaborationGroup;
import com.example.mohamed.openstarter.Models.Project;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private static final int COLLABORATION_GROUP_STEP_NUM = 7;

    // Name step
    private EditText nameEditText;
    private static final int MIN_CHARACTERS_NAME = 2;
    private static final int MAX_CHARACTERS_NAME = 50;
    public static final String STATE_NAME = "name";

    // Category step
    private Spinner categorySpinner;
    private Long categorySelectedId;
    public static final String STATE_CATEGORY = "category";

    // Short Description step
    private EditText shortDescriptionEditText;
    private static final int MIN_CHARACTERS_SHORT_DESCRIPTION = 2;
    private static final int MAX_CHARACTERS_SHORT_DESCRIPTION = 150;
    public static final String STATE_SHORT_DESCRIPTION = "short_description";

    // Description step
    private EditText descriptionEditText;
    private static final int MIN_CHARACTERS_DESCRIPTION = 2;
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

    // Collaboration Group step
    private Spinner collaborationGroupSpinner;
    private Long collaborationGroupSelectedId;
    public static final String STATE_COLLABORATION_GROUP= "collaborationGroup";

    //Activity vars
    private static String PROJECT_TAG = "project" ;
    private static String COLLABORATION_GROUP_TAG = "collaboration group";
    private static String CATEGORY_TAG = "category" ;
    private boolean confirmBack = true;
    private ProgressDialog progressDialog;
    private VerticalStepperFormLayout verticalStepperForm;
    private Calendar calendar;
    public static CreateProjectActivity instance = null;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        initializeActivity();
    }

    private void initializeActivity() {
        firebaseAuth = FirebaseAuth.getInstance();
        instance = this ;
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
                view = createProjectBudgetStep();
                break;
            case COLLABORATION_GROUP_STEP_NUM:
                view = createProjectCollaborationGroupStep();
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
                checkDescriptionStep(descriptionEditText.getText().toString());
                break;
            case START_DATE_STEP_NUM:
                checkStartDateStep(startDateDayTextView.getText().toString());
                break;
            case FINISH_DATE_STEP_NUM:
                checkFinishDateStep(finishDateDayTextView.getText().toString()+ " " + finishDateTimeTextView.getText().toString());
                break;
            case BUDGET_STEP_NUM:
                checkBudgetStep(budgetEditText.getText().toString());
                break;
            case COLLABORATION_GROUP_STEP_NUM:
                verticalStepperForm.setStepAsCompleted(stepNumber);
                break;
        }
    }

    @Override
    public void sendData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.create_project_sending_message));
        executeDataSending();
    }

    private void executeDataSending() {
        //Toast.makeText(this, ,Toast.LENGTH_LONG).show();
        ProjectDs projectDs = new ProjectDs () ;
        projectDs.projectCreate(nameEditText.getText().toString(),
                startDateDayTextView.getText().toString() + " " + startDateTimeTextView.getText().toString() +":00",
                finishDateDayTextView.getText().toString() + " " + finishDateTimeTextView.getText().toString() +":00",
                shortDescriptionEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                budgetEditText.getText().toString(),
                collaborationGroupSelectedId.toString(),
                categorySelectedId.toString(),
                new ProjectDs.Callback() {
                    @Override
                    public void onSuccessGet(List<Project> result) {}

                    @Override
                    public void onSuccessCreate(Project createdProject) {
                        progressDialog.cancel();
                        Intent myIntent = new Intent(getBaseContext(), ProjectActivity.class);
                        myIntent.putExtra(PROJECT_TAG,createdProject);
                        myIntent.putExtra(COLLABORATION_GROUP_TAG,createdProject.getCollaborationGroup());
                        myIntent.putExtra(CATEGORY_TAG,createdProject.getCategory());
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getBaseContext().startActivity(myIntent);
                    }
                    @Override
                    public void onFail() {
                    }
                });

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
        CategoryDs categoryDs = new CategoryDs();
        categoryDs.getAll(new CategoryDs.Callback() {
            @Override
            public void onSuccessGet(List<Category> categoriesList) {

                Category[] dataArray = new Category[categoriesList.size()];
                categorySelectedId=categoriesList.get(0).getId();
               for (int i = 0; i < categoriesList.size(); i++  ){

                   dataArray[i]=categoriesList.get(i);
               }
                final ArrayAdapter<Category> spinnerAdapter = new CategoriesSpinnerAdapter(
                        instance, android.R.layout.simple_spinner_item,dataArray);


                categorySpinner.setAdapter(spinnerAdapter);
                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {

                        Category selectedCategory = (Category) parent.getItemAtPosition(pos);
                        categorySelectedId = selectedCategory.getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            @Override
            public void onFail() {}
        });
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
        descriptionEditText = new EditText(this);
        descriptionEditText.setHint(R.string.create_project_form_hint_description);
        descriptionEditText.setSingleLine(false);
        descriptionEditText.addTextChangedListener(new TextWatcher() {
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
        descriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        return descriptionEditText;
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
                    checkFinishDateStep(finishDateDayTextView.getText().toString() + " " + finishDateTimeTextView.getText().toString());
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
                        checkFinishDateStep(finishDateDayTextView.getText().toString() + " " + finishDateTimeTextView.getText().toString());
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
                if (checkFinishDateStep(s.toString() + " " + finishDateTimeTextView.getText().toString())) {
                    // verticalStepperForm.goToNextStep();
                }
            }
        });
        return finishDateStepContent;
    }

    private boolean checkFinishDateStep(String finishDateString) {
        boolean finishDateIsCorrect = false;
        String startDateString = startDateDayTextView.getText().toString() + " " + startDateTimeTextView.getText().toString() ;
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date finishDate = dayFormat.parse(finishDateString);
            Date startDate = dayFormat.parse(startDateString);
            if (finishDateString.trim().length() > 0) {
                if (finishDate.after(startDate)) {
                    finishDateIsCorrect = true;
                    verticalStepperForm.setStepAsCompleted(FINISH_DATE_STEP_NUM);
                    //verticalStepperForm.setActiveStepAsCompleted();
                } else{
                    String finishDateError = getResources().getString(R.string.error_finish_date);
                    verticalStepperForm.setStepAsUncompleted(FINISH_DATE_STEP_NUM,finishDateError);
                    //verticalStepperForm.setActiveStepAsUncompleted(finishDateError);
                }
            }else {
                String finishDateError = getResources().getString(R.string.error_finish_date);
                verticalStepperForm.setStepAsUncompleted(FINISH_DATE_STEP_NUM,finishDateError);
                //verticalStepperForm.setActiveStepAsUncompleted(finishDateError);
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
                        checkFinishDateStep(finishDateDayTextView.getText().toString() + " " + finishDateTimeTextView.getText().toString());
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

    private View createProjectBudgetStep() {
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
        budgetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkBudgetStep(s.toString())) {
                    //verticalStepperForm.goToNextStep();
                }
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
        if (budgetString != null && !budgetString.equals("") ){
            long budget = Long.valueOf(budgetString);
            if (budget > 0) {
                budgetIsCorrect = true;
                verticalStepperForm.setActiveStepAsCompleted();
            } else {
                String budgetError = getResources().getString(R.string.error_budget);
                verticalStepperForm.setActiveStepAsUncompleted(budgetError);
            }
        }else {
            String budgetError = getResources().getString(R.string.error_budget);
            verticalStepperForm.setActiveStepAsUncompleted(budgetError);
        }
        return budgetIsCorrect;
    }

    //******************************************
    //************************  COLLABORATION GROUP

    private View createProjectCollaborationGroupStep() {
        collaborationGroupSpinner = new Spinner(this);
        CollaborationGroupDs collaborationGroupDs = new CollaborationGroupDs();
        collaborationGroupDs.collaborationGroupGetByAdminUser(firebaseAuth.getCurrentUser().getEmail(),new CollaborationGroupDs.Callback() {
            @Override
            public void onSuccessGet(List<CollaborationGroup> groupsList) {
                if (groupsList.size() > 0){
                    CollaborationGroup[] dataArray = new CollaborationGroup[groupsList.size()];
                    collaborationGroupSelectedId=groupsList.get(0).getId();
                    for (int i = 0; i < groupsList.size(); i++  ){

                        dataArray[i]=groupsList.get(i);
                    }
                    final ArrayAdapter<CollaborationGroup> groupSpinnerAdapter = new CollaborationGroupSpinnerAdapter(
                            instance, android.R.layout.simple_spinner_item,dataArray);

                    collaborationGroupSpinner.setAdapter(groupSpinnerAdapter);
                    collaborationGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int pos, long id) {

                            CollaborationGroup selectedCollaborationGroup = (CollaborationGroup) parent.getItemAtPosition(pos);
                            collaborationGroupSelectedId = selectedCollaborationGroup.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else {
                    AlertDialog noAdminGroupAlert = new AlertDialog.Builder(instance).create();
                    noAdminGroupAlert.setTitle("Alert");
                    noAdminGroupAlert.setMessage("You have no groups you're administrating, please create a group or get into a group with admin rights");
                    noAdminGroupAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getBaseContext().startActivity(myIntent);
                                }
                            });
                    noAdminGroupAlert.show();

                }

            }

            @Override
            public void onSuccessCreate(CollaborationGroup createdGroup) {}

            @Override
            public void onFail() {}
        });
        return collaborationGroupSpinner;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

}


