package com.example.mohamed.openstarter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mohamed.openstarter.R;

public class ProfilActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
