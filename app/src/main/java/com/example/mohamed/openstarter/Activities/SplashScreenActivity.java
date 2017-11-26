package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mohamed.openstarter.Helpers.GradientBackgroundPainter;
import com.example.mohamed.openstarter.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private GradientBackgroundPainter gradientBackgroundPainter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        View backgroundImage = findViewById(R.id.bg_view);
        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;
        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gradientBackgroundPainter.stop();
    }
}