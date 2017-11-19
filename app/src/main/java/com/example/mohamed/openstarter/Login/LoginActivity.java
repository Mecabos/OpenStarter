package com.example.mohamed.openstarter.Login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohamed.openstarter.MainActivity;
import com.example.mohamed.openstarter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText etUsername;
    EditText etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;
    private GradientBackgroundPainter gradientBackgroundPainter;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);

        View backgroundImage = findViewById(R.id.bg_view);

        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();

        btGo.setOnClickListener(new View.OnClickListener() {
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
                                        Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                        Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i2, oc2.toBundle());
                                        finish();
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

    @Override
    public void onStart() {
        super.onStart();
        /*if (firebaseAuth.getCurrentUser() != null) {
            preferences.edit().putString("email", firebaseAuth.getCurrentUser().getEmail()).apply();
            preferences.edit().putString("providerId", firebaseAuth.getCurrentUser().getProviderId()).apply();
            preferences.edit().putString("nom", firebaseAuth.getCurrentUser().getDisplayName()).apply();
            preferences.edit().putString("photo", firebaseAuth.getCurrentUser().getPhotoUrl().toString()).apply();
            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
            Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i2, oc2.toBundle());
            finish();

        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }

}
