package com.example.mohamed.openstarter.Activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mohamed.openstarter.Data.DataSuppliers.UserDs;
import com.example.mohamed.openstarter.Helpers.GradientBackgroundPainter;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText etUsername;
    EditText etPassword;
    Button btLogin;
    CardView cv;
    FloatingActionButton fab;
    private GradientBackgroundPainter gradientBackgroundPainter;
    public static String PREFERENCE_FIlENAME = "intro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btLogin = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);

        View backgroundImage = findViewById(R.id.bg_view);
        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;
        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                if (etUsername.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "username can't be empty", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "password can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    (firebaseAuth.signInWithEmailAndPassword(etUsername.getText().toString(), etPassword.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        UserDs ds = new UserDs();
                                        ds.getUserByEmail(etUsername.getText().toString(), new UserDs.Callback() {
                                            @Override
                                            public void onSuccess(User createdUser) {

                                                if (createdUser.getEmail() == null) {

                                                    Log.d("userr", createdUser.toString());
                                                    Log.d("userr", "user missing");
                                                    Intent i2 = new Intent(LoginActivity.this, CompleteRegisterActivity.class);
                                                    startActivity(i2);
                                                    finish();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();


                                                    //ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                                    if (getIntroSharedPref().equals("waiting")){
                                                        Intent i2 = new Intent(LoginActivity.this, IntroductionActivity.class);
                                                        startActivity(i2);
                                                        finish();
                                                    }
                                                    else{
                                                        Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                                                        startActivity(i2);
                                                        finish();
                                                    }


                                                }
                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                Toast.makeText(LoginActivity.this, "couldn't reach the server", Toast.LENGTH_LONG).show();
                                            }
                                        });


                                    } else {
                                        Log.e("Registration", task.getException().getMessage());
                                        Toast.makeText(LoginActivity.this, "wrong email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            }
        });

    }



    public String getIntroSharedPref(){
        SharedPreferences tutoPref = getSharedPreferences(PREFERENCE_FIlENAME, Context.MODE_PRIVATE);
        return tutoPref.getString("intro", "waiting");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }

}
