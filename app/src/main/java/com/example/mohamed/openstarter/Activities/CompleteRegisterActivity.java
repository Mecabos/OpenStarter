package com.example.mohamed.openstarter.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.openstarter.Data.DataSuppliers.UserDs;
import com.example.mohamed.openstarter.Helpers.GradientBackgroundPainter;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.vlstr.blurdialog.BlurDialog;

import java.io.IOException;
import java.util.Calendar;

public class CompleteRegisterActivity extends AppCompatActivity {

    private GradientBackgroundPainter gradientBackgroundPainter;
    private DatePickerDialog birthdatePicker;
    Button go;
    EditText firstName, lastName, bio;
    TextView birthdate;
    ImageButton avatar;
    private FirebaseAuth firebaseAuth;
    private final int IMG_REQUEST = 1;
    Bitmap bitmap;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);

        final BlurDialog blurDialog = findViewById(R.id.blurLoader);
        blurDialog.create(getWindow().getDecorView(), 6);
        blurDialog.setTitle("Please wait");

        calendar = Calendar.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        birthdate = findViewById(R.id.et_birthdate);
        bio = findViewById(R.id.et_bio);
        avatar = findViewById(R.id.avatar);
        go = findViewById(R.id.bt_finish);

        //background set
        View backgroundImage = findViewById(R.id.bg_view);
        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();


        setBirthdatePicker(calendar);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdatePicker.show();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("datee",birthdate.getText().toString());
                if (firstName.getText().toString().equals("") || lastName.getText().toString().equals("")|| birthdate.getText().toString().equals("")) {
                    Toast.makeText(CompleteRegisterActivity.this, "first name and last name are required ", Toast.LENGTH_LONG).show();
                } else {

                    blurDialog.show();
                    UserDs ds = new UserDs();
                    ds.addUser(firebaseAuth.getCurrentUser().getEmail(), firstName.getText().toString(), lastName.getText().toString(), birthdate.getText().toString(), bio.getText().toString(), new UserDs.CallbackAdd() {
                        @Override
                        public void onSuccess() {
                            blurDialog.hide();
                            //ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(CompleteRegisterActivity.this);
                            Intent i2 = new Intent(CompleteRegisterActivity.this, IntroductionActivity.class);
                            startActivity(i2/*, oc2.toBundle()*/);
                            finish();
                        }

                        @Override
                        public void onError() {
                            blurDialog.hide();
                            Toast.makeText(CompleteRegisterActivity.this, "could not reach the server", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        if (firebaseAuth.getCurrentUser().getPhotoUrl() != null)
            Picasso.with(this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(avatar);
        firstName.setText(firebaseAuth.getCurrentUser().getDisplayName());
    }

    private void setBirthdatePicker(Calendar calendar){
        int year = calendar.get((Calendar.YEAR));
        int monthOfYear = calendar.get((Calendar.MONTH));
        int dayOfMonth = calendar.get((Calendar.DAY_OF_MONTH));

        birthdatePicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        setBirthdate(year, monthOfYear, dayOfMonth);
                    }
                }, year, monthOfYear, dayOfMonth);
        birthdatePicker.getDatePicker().setMaxDate(calendar.getTime().getTime());
    }

    private void setBirthdate(int year, int monthOfYear, int dayOfMonth) {
        String day = year + "/" + monthOfYear + "/" + dayOfMonth;
        birthdate.setText(day);
    }

    @Override
    protected void onActivityResult(int IMG_REQUEST, int resultCode, Intent data) {
        super.onActivityResult(IMG_REQUEST, resultCode, data);
        Uri path = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
            avatar.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }

}
