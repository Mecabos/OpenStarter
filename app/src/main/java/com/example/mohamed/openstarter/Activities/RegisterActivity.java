package com.example.mohamed.openstarter.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mohamed.openstarter.Data.DataSuppliers.UserDs;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterActivity extends AppCompatActivity {


    FloatingActionButton fab;
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private SharedPreferences preferences;
    private FirebaseAuth.AuthStateListener mAuthListener;

    EditText username, pass, repeatpass;
    CardView cvAdd;
    Button next;
    SignInButton mGoogleBtn;
    CallbackManager mCallbackManager;
    LoginButton fbloginButton;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    public static String PREFERENCE_FIlENAME = "intro";

    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        next = findViewById(R.id.bt_next);
        username = findViewById(R.id.et_username_reg);
        pass = findViewById(R.id.et_password_reg);
        repeatpass = findViewById(R.id.et_repeatpassword_reg);
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        mGoogleBtn = findViewById(R.id.bt_google_signup);

        ShowEnterAnimation();
       /* FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
*/

        mCallbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();


        fbloginButton = findViewById(R.id.button_facebook_login);
        fbloginButton.setReadPermissions("email", "public_profile");
        fbloginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(accessToken);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    preferences.edit().putString("email", firebaseAuth.getCurrentUser().getEmail()).apply();
                    preferences.edit().putString("providerId", firebaseAuth.getCurrentUser().getProviderId()).apply();
                    preferences.edit().putString("nom", firebaseAuth.getCurrentUser().getDisplayName()).apply();
                    preferences.edit().putString("photo", firebaseAuth.getCurrentUser().getPhotoUrl().toString()).apply();
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(RegisterActivity.this);
                    Intent i2 = new Intent(RegisterActivity.this, CompleteRegisterActivity.class);
                    startActivity(i2, oc2.toBundle());
                    finish();

                }
            }
        };


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                showToast("ErrorGoogle");
            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                if (username.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "username can't be empty", Toast.LENGTH_SHORT).show();
                } else if (pass.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "password can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.getText().toString().equals(repeatpass.getText().toString())) {

                        (firebaseAuth.createUserWithEmailAndPassword(username.getText().toString(), pass.getText().toString()))
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {

                                            UserDs ds = new UserDs();
                                            ds.getUserByEmail(username.getText().toString(), new UserDs.Callback() {
                                                @Override
                                                public void onSuccess(User createdUser) {

                                                    if (createdUser.getEmail() == null) {

                                                        Log.d("userr", createdUser.toString());
                                                        Log.d("userr", "user missing");
                                                        Intent i2 = new Intent(RegisterActivity.this, CompleteRegisterActivity.class);
                                                        startActivity(i2);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(RegisterActivity.this, "login successful", Toast.LENGTH_SHORT).show();

                                                        //ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                                        if (getIntroSharedPref().equals("waiting")){
                                                            Intent i2 = new Intent(RegisterActivity.this, IntroductionActivity.class);
                                                            startActivity(i2);
                                                            finish();
                                                        }
                                                        else{
                                                            Intent i2 = new Intent(RegisterActivity.this, MainActivity.class);
                                                            startActivity(i2);
                                                            finish();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onError(VolleyError error) {
                                                    Toast.makeText(RegisterActivity.this, "couldn't reach the server", Toast.LENGTH_LONG).show();
                                                }
                                            });


                                        } else {
                                            Log.e("Registration", task.getException().getMessage());
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "password doesen't match", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }


    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showToast("google failded");                // ...
            }
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Toast toast;

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();

    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //showToast(user.getEmail());


                            UserDs ds = new UserDs();
                            assert user != null;
                            ds.getUserByEmail(user.getEmail(), new UserDs.Callback() {
                                @Override
                                public void onSuccess(User createdUser) {

                                    if (createdUser.getEmail() == null) {

                                        Log.d("userr", createdUser.toString());
                                        Log.d("userr", "user missing");
                                        Intent i2 = new Intent(RegisterActivity.this, CompleteRegisterActivity.class);
                                        startActivity(i2);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                                        //ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);

                                        if (getIntroSharedPref().equals("waiting")){
                                            Intent i2 = new Intent(RegisterActivity.this, IntroductionActivity.class);
                                            startActivity(i2);
                                            finish();
                                        }
                                        else{
                                            Intent i2 = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(i2);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Toast.makeText(RegisterActivity.this, "couldn't reach the server", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            showToast("Failed");
                            showToast(task.getException() + "");
                        }

                        // ...
                    }
                });
    }


    public void SignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        LoginManager.getInstance().logOut();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();


                            UserDs ds = new UserDs();
                            assert user != null;
                            ds.getUserByEmail(user.getEmail(), new UserDs.Callback() {
                                @Override
                                public void onSuccess(User createdUser) {

                                    if (createdUser.getEmail() == null) {

                                        Log.d("userr", createdUser.toString());
                                        Log.d("userr", "user missing");
                                        Intent i2 = new Intent(RegisterActivity.this, CompleteRegisterActivity.class);
                                        startActivity(i2);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                                        //ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                        if (getIntroSharedPref().equals("waiting")){
                                            Intent i2 = new Intent(RegisterActivity.this, IntroductionActivity.class);
                                            startActivity(i2);
                                            finish();
                                        }
                                        else{
                                            Intent i2 = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(i2);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Toast.makeText(RegisterActivity.this, "couldn't reach the server", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "fb Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public String getIntroSharedPref(){
        SharedPreferences tutoPref = getSharedPreferences(PREFERENCE_FIlENAME, Context.MODE_PRIVATE);
        return tutoPref.getString("intro", "waiting");
    }
}
