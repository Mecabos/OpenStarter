package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mohamed.openstarter.Data.DataSuppliers.UserDs;
import com.example.mohamed.openstarter.Helpers.GradientBackgroundPainter;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class CompleteRegisterActivity extends AppCompatActivity {

    private GradientBackgroundPainter gradientBackgroundPainter;
    Button go;
    EditText firstName, lastName, phoneNumber;
    ImageButton avatar;
    private FirebaseAuth firebaseAuth;
    private final int IMG_REQUEST=1;
    Bitmap bitmap;
    //Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        phoneNumber = findViewById(R.id.et_phone_number);
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


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDs ds = new UserDs();
                ds.addUser(firebaseAuth.getCurrentUser().getEmail(),firstName.getText().toString(),lastName.getText().toString(),"date","bio");


                //ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(CompleteRegisterActivity.this);
                Intent i2 = new Intent(CompleteRegisterActivity.this, IntroductionActivity.class);
                startActivity(i2/*, oc2.toBundle()*/);
                finish();
            }
        });

        if (firebaseAuth.getCurrentUser().getPhotoUrl() != null)
            Picasso.with(this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(avatar);
        firstName.setText(firebaseAuth.getCurrentUser().getDisplayName());
    }

    @Override
    protected void onActivityResult(int IMG_REQUEST, int resultCode, Intent data) {
        super.onActivityResult(IMG_REQUEST, resultCode, data);
        Uri path=data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
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
