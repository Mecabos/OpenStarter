package com.example.mohamed.openstarter.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mohamed.openstarter.R;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.firebase.auth.FirebaseAuth;

public class GroupActivity extends AppCompatActivity {

    Button bt_members, bt_editGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        bt_members = findViewById(R.id.bt_members);
        bt_editGroup = findViewById(R.id.bt_editGroup);

        bt_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(GroupActivity.this)
                        //.setImageRecourse(R.drawable.ic_cloud_computing)
                        .setTextTitle("MEMBERS")
                        //.setTextSubTitle("username")
                        .setBody("List of members here")
                        //.setNegativeColor(R.color.colorNegative)
                        .setNegativeButtonText("Later")
                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButtonText("Continue")
                        //.setPositiveColor(R.color.colorPositive)
                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                //Toast.makeText(ProfilActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setBodyGravity(FancyAlertDialog.TextGravity.LEFT)
                        .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                        .setSubtitleGravity(FancyAlertDialog.TextGravity.RIGHT)
                        .setCancelable(false)
                        .build();
                alert.show();
            }
        });

        bt_editGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : refresh
                Intent i2 = new Intent(GroupActivity.this, EditGroupActivity.class);
                startActivity(i2);
            }
        });
    }
}
