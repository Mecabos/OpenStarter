package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.mohamed.openstarter.Data.DataSuppliers.UserDs;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView fullname, email, tv_bio;
    CircleImageView avatar;
    Button bt_editProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        tv_bio = findViewById(R.id.bio);
        bt_editProfil = findViewById(R.id.bt_editProfil);
        avatar = findViewById(R.id.avatar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(ProfilActivity.this, EditProfilActivity.class);
                startActivity(i2);
            }
        });

        UserDs ds = new UserDs();
        ds.getUserByEmail(firebaseAuth.getCurrentUser().getEmail(), new UserDs.CallbackGet() {
            @Override
            public void onSuccess(User createdUser) {
                String name = createdUser.getFirstName()+" "+createdUser.getLastName();
                String bio = createdUser.getBio();
                fullname.setText(name);
                tv_bio.setText(bio);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        email.setText(firebaseAuth.getCurrentUser().getEmail());
        if (firebaseAuth.getCurrentUser().getPhotoUrl() != null)
            Picasso.with(this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(avatar);

    }
}
