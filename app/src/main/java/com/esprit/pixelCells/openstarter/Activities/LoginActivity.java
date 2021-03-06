package com.esprit.pixelCells.openstarter.Activities;

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
import com.esprit.pixelCells.openstarter.Data.DataSuppliers.UserServer;
import com.esprit.pixelCells.openstarter.Helpers.DialogHelper;
import com.esprit.pixelCells.openstarter.Helpers.GradientBackgroundPainter;
import com.esprit.pixelCells.openstarter.Models.User;
import com.esprit.pixelCells.openstarter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vlstr.blurdialog.BlurDialog;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText etUsername;
    EditText etPassword;
    Button btLogin;
    CardView cv;
    FloatingActionButton fab;
    private GradientBackgroundPainter gradientBackgroundPainter;
    public static String PREFERENCE_FIlENAME = "intro";
    //public static String LOGIN_PREFERENCE_FIlENAME = "login";
    private DialogHelper dialogHelper;
    private BlurDialog blurDialog;
    public static LoginActivity instance = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instance = this;
        dialogHelper = new DialogHelper();
        blurDialog = findViewById(R.id.blurLoader);

        firebaseAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btLogin = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);

        /*if (getLoginSharedPref().equals("on")){
            Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i2);
            finish();_
        }*/


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
                    dialogHelper.blurDialogShow(instance,blurDialog,"Connecting");
                    (firebaseAuth.signInWithEmailAndPassword(etUsername.getText().toString(), etPassword.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        UserServer ds = new UserServer();
                                        ds.getUserByEmail(etUsername.getText().toString(), new UserServer.CallbackGet() {
                                            @Override
                                            public void onSuccess(User createdUser) {

                                                if (createdUser.getEmail() == null) {

                                                    Log.d("userr", createdUser.toString());
                                                    Log.d("userr", "user missing");
                                                    dialogHelper.blurDialogHide(instance,blurDialog);
                                                    Intent i2 = new Intent(LoginActivity.this, CompleteRegisterActivity.class);
                                                    startActivity(i2);
                                                    finish();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();


                                                    //ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                                    if (getIntroSharedPref().equals("waiting")){
                                                        UserServer userDs = new UserServer();
                                                        userDs.updateToken(FirebaseAuth.getInstance().getCurrentUser().getEmail(), FirebaseInstanceId.getInstance().getToken(), new UserServer.CallbackUpdate() {
                                                            @Override
                                                            public void onSuccess() {
                                                                Log.d("userr","token updated");
                                                            }

                                                            @Override
                                                            public void onError() {
                                                                Log.d("userr","token failed to update");
                                                            }
                                                        });
                                                        dialogHelper.blurDialogHide(instance,blurDialog);
                                                        Intent i2 = new Intent(LoginActivity.this, IntroductionActivity.class);
                                                        startActivity(i2);
                                                        finish();
                                                    }
                                                    else{
                                                        UserServer userDs = new UserServer();
                                                        Log.d("mytoken",FirebaseInstanceId.getInstance().getToken());
                                                        userDs.updateToken(FirebaseAuth.getInstance().getCurrentUser().getEmail(), FirebaseInstanceId.getInstance().getToken(), new UserServer.CallbackUpdate() {
                                                            @Override
                                                            public void onSuccess() {
                                                                Log.d("userr","token updated");
                                                            }

                                                            @Override
                                                            public void onError() {
                                                                Log.d("userr","token failed to update");
                                                            }
                                                        });
                                                        dialogHelper.blurDialogHide(instance,blurDialog);
                                                        Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                                                        startActivity(i2);
                                                        finish();
                                                    }


                                                }
                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                dialogHelper.blurDialogHide(instance,blurDialog);
                                                Toast.makeText(LoginActivity.this, "couldn't reach the server", Toast.LENGTH_LONG).show();
                                            }
                                        });


                                    } else {
                                        dialogHelper.blurDialogHide(instance,blurDialog);
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

    /*public String getLoginSharedPref(){
        SharedPreferences tutoPref = getSharedPreferences(LOGIN_PREFERENCE_FIlENAME, Context.MODE_PRIVATE);
        return tutoPref.getString("login", "off");
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }

}
