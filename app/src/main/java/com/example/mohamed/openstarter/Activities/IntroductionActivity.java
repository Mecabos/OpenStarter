package com.example.mohamed.openstarter.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.example.mohamed.openstarter.R;

import java.util.ArrayList;
import java.util.List;

public class IntroductionActivity extends AhoyOnboarderActivity {

    public static String PREFERENCE_FIlENAME = "intro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Welcome to OpenStarter", "Open starter is an open project funding app created for anyone who wishes to have a quick way to find suporters for his project", R.drawable.big_logo);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Discover", "Swipe right to get the side menu", R.drawable.swiperight);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Get started", "Look for projects that inspire you and that you want to support and help becoming a reality", R.drawable.getstarted);

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.grey_200);
            //page.setTitleTextSize(dpToPixels(12, this));
            //page.setDescriptionTextSize(dpToPixels(8, this));
            //page.setIconLayoutParams(width, height, marginTop, marginLeft, marginRight, marginBottom);
        }

        setFinishButtonTitle("Finish");
        showNavigationControls(true);
        setGradientBackground();

        //set the button style you created
        setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        setFont(face);

        setOnboardPages(pages);
    }

    public void setIntroSharedPref(){
        SharedPreferences tutoPref  = getSharedPreferences(PREFERENCE_FIlENAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = tutoPref.edit();
        editor.putString("intro","done");
        editor.apply();

    }

    @Override
    public void onFinishButtonPressed() {
        //Toast.makeText(this, "Finish Pressed", Toast.LENGTH_SHORT).show();
        setIntroSharedPref();
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(IntroductionActivity.this);
        Intent i2 = new Intent(IntroductionActivity.this, MainActivity.class);
        startActivity(i2, oc2.toBundle());
        finish();
    }
}