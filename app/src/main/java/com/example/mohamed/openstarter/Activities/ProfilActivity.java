package com.example.mohamed.openstarter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView fullname, email;
    CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        avatar = findViewById(R.id.avatar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email.setText(firebaseAuth.getCurrentUser().getEmail());
        if (firebaseAuth.getCurrentUser().getPhotoUrl() != null)
            Picasso.with(this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(avatar);
        fullname.setText(firebaseAuth.getCurrentUser().getDisplayName());


        //Log.v("myimage",firebaseAuth.getCurrentUser().getPhotoUrl().toString());
    }
}
