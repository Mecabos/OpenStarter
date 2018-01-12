package com.example.mohamed.openstarter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mohamed.openstarter.Data.DataSuppliers.UserServer;
import com.example.mohamed.openstarter.Helpers.DialogHelper;
import com.example.mohamed.openstarter.Models.User;
import com.example.mohamed.openstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.vlstr.blurdialog.BlurDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView medal;
    TextView fullname, email, tv_bio,tv_birthDate, tv_projectsCount, tv_contributions;
    CircleImageView avatar;
    Button bt_editProfil, bt_groups;
    private long user_id;

    String firstName=" ";
    String lastName=" ";

    private DialogHelper dialogHelper;
    private BlurDialog blurDialog;
    public static ProfilActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        instance = this;
        dialogHelper = new DialogHelper();
        blurDialog = findViewById(R.id.blurLoader);

        email = findViewById(R.id.email);
        tv_birthDate = findViewById(R.id.birthdate);
        fullname = findViewById(R.id.fullname);
        tv_bio = findViewById(R.id.bio);
        bt_editProfil = findViewById(R.id.bt_editProfil);
        tv_projectsCount = findViewById(R.id.projectsCount);
        tv_contributions = findViewById(R.id.contributions);
        bt_groups = findViewById(R.id.bt_showGroups);
        avatar = findViewById(R.id.avatar);
        medal = findViewById(R.id.medal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : refresh
                Intent i1 = new Intent(ProfilActivity.this, EditProfilActivity.class);
                i1.putExtra("firstName", firstName);
                i1.putExtra("lastName", lastName);
                i1.putExtra("bio", tv_bio.getText().toString());
                i1.putExtra("birthDate", tv_birthDate.getText().toString());
                startActivity(i1);
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


                Intent i2 = new Intent(ProfilActivity.this, GroupActivity.class);
                startActivity(i2);


            }
        });



        dialogHelper.blurDialogShow(instance,blurDialog,"Loading profil");
        UserServer ds = new UserServer();
        ds.getUserByEmailWithCount(firebaseAuth.getCurrentUser().getEmail(), new UserServer.CallbackGet() {
            @Override
            public void onSuccess(User createdUser) {
                dialogHelper.blurDialogHide(instance,blurDialog);
                user_id = createdUser.getId();
                firstName = createdUser.getFirstName();
                lastName = createdUser.getLastName();
                String name = firstName+" "+lastName;
                String bio = createdUser.getBio();
                fullname.setText(name);
                tv_bio.setText(bio);
                tv_projectsCount.setText(Integer.toString(createdUser.getProjectsCount()));
                tv_contributions.setText(Integer.toString(createdUser.getContributions()));
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String birthDate = df.format(createdUser.getBirthDate());
                tv_birthDate.setText(birthDate);

                if (Integer.parseInt(tv_contributions.getText().toString())>=2)
                    medal.setImageResource(R.drawable.gold);
                else if (Integer.parseInt(tv_contributions.getText().toString())==1)
                    medal.setImageResource(R.drawable.silver);
            }

            @Override
            public void onError(VolleyError error) {
                dialogHelper.blurDialogHide(instance,blurDialog);
                finish();
                Toast.makeText(ProfilActivity.this, "couldn't load profil", Toast.LENGTH_LONG).show();
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
