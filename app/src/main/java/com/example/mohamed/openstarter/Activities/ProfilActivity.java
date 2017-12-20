package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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
    Button bt_editProfil, bt_groups;
    private Spinner collaborationGroupSpinner;
    private long collaborationGroupSelectedId;
    private long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        tv_bio = findViewById(R.id.bio);
        bt_editProfil = findViewById(R.id.bt_editProfil);
        bt_groups = findViewById(R.id.bt_showGroups);
        avatar = findViewById(R.id.avatar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : refresh
                Intent i2 = new Intent(ProfilActivity.this, EditProfilActivity.class);
                startActivity(i2);
            }
        });

        bt_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(ProfilActivity.this)
                        //.setImageRecourse(R.drawable.ic_cloud_computing)
                        .setTextTitle("GROUPS")
                        //.setTextSubTitle("username")
                        .setBody("List of groups here")
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
                alert.show();*/

                //TODO : list groups
                Intent i2 = new Intent(ProfilActivity.this, GroupActivity.class);
                startActivity(i2);


            }
        });


        /*collaborationGroupSpinner = new Spinner(ProfilActivity.this);
        CollaborationGroupDs collaborationGroupDs = new CollaborationGroupDs();
        collaborationGroupDs.collaborationGroupGetByUser(firebaseAuth.getCurrentUser().getEmail(),new CollaborationGroupDs.Callback() {
            @Override
            public void onSuccessGet(List<CollaborationGroup> groupsList) {
                if (groupsList.size() > 0){
                    CollaborationGroup[] dataArray = new CollaborationGroup[groupsList.size()];
                    collaborationGroupSelectedId=groupsList.get(0).getId();
                    for (int i = 0; i < groupsList.size(); i++  ){

                        dataArray[i]=groupsList.get(i);
                    }
                    final ArrayAdapter<CollaborationGroup> groupSpinnerAdapter = new CollaborationGroupSpinnerAdapter(
                            instance, android.R.layout.simple_spinner_item,dataArray);

                    collaborationGroupSpinner.setAdapter(groupSpinnerAdapter);
                    collaborationGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int pos, long id) {

                            CollaborationGroup selectedCollaborationGroup = (CollaborationGroup) parent.getItemAtPosition(pos);
                            collaborationGroupSelectedId = selectedCollaborationGroup.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else {
                    AlertDialog noAdminGroupAlert = new AlertDialog.Builder(instance).create();
                    noAdminGroupAlert.setTitle("Alert");
                    noAdminGroupAlert.setMessage("You have no groups you're administrating, please create a group or get into a group with admin rights");
                    noAdminGroupAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    getBaseContext().startActivity(myIntent);
                                }
                            });
                    noAdminGroupAlert.show();

                }

            }

            @Override
            public void onSuccessCreate(CollaborationGroup createdGroup) {}

            @Override
            public void onFail() {}
        });*/


        UserDs ds = new UserDs();
        ds.getUserByEmail(firebaseAuth.getCurrentUser().getEmail(), new UserDs.CallbackGet() {
            @Override
            public void onSuccess(User createdUser) {
                user_id = createdUser.getId();
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
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
